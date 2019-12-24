package com.akurey.bright

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.akurey.bright.databinding.FragmentTimeLogBinding
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.akurey.bright.AWSModel.EmployeeDO
import com.akurey.bright.data.Employee
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class TimeLogFragment : Fragment() {
    lateinit var parent: MainActivity
    lateinit var employee: Employee
    private lateinit var binding: FragmentTimeLogBinding
    private var startDateTime = Calendar.getInstance()
    private var endDateTime = Calendar.getInstance()


    // region New Instance Method
    companion object {
        fun newInstance(): TimeLogFragment {
            val arguments = Bundle()
            val fragment = TimeLogFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_log, container, false)

        startDateTime.set(Calendar.HOUR_OF_DAY, 9)
        startDateTime.set(Calendar.MINUTE, 0)

        endDateTime.set(Calendar.HOUR_OF_DAY, 17)
        endDateTime.set(Calendar.MINUTE, 0)

        binding.startDate.setOnClickListener {
            this.showDatePickerDialog(
                it as TextView,
                startDateTime
            )
        }
        binding.startTime.setOnClickListener {
            this.showTimePickerDialog(
                it as TextView,
                startDateTime
            )
        }

        binding.endDate.setOnClickListener {
            this.showDatePickerDialog(
                it as TextView,
                endDateTime
            )
        }
        binding.endTime.setOnClickListener {
            this.showTimePickerDialog(
                it as TextView,
                endDateTime
            )
        }

        binding.sendButton.setOnClickListener {
            parent.createLog(employee, startDateTime.time, endDateTime.time, differenceInHours(endDateTime, startDateTime))
        }

        this.setHour(binding.startTime, this.startDateTime)
        this.setDate(binding.startDate, this.startDateTime)

        this.setHour(binding.endTime, this.endDateTime)
        this.setDate(binding.endDate, this.endDateTime)
        binding.companyTextView.setText(employee.facilityName)
        binding.nameTextView.setText(employee.firstName + " " + employee.lastName)
        this.updateWorkingHoursLabel()

        return binding.root
    }

    private fun differenceInHours(calendar1: Calendar, calendar2: Calendar): Double {
        return ((calendar1.timeInMillis - calendar2.timeInMillis) / 1000.0 / 60.0 / 60.0)
    }

    private fun updateWorkingHoursLabel(): Double {
        val hours = this.differenceInHours(this.endDateTime, this.startDateTime)
        this.binding.hoursTextView.text =
            "${BigDecimal(hours).setScale(2, RoundingMode.HALF_EVEN)} hours worked"
        return hours
    }

    private fun setDate(textView: TextView, calendar: Calendar) {
        textView.text =
            "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(
                Calendar.YEAR
            )}"
    }

    private fun setHour(textView: TextView, calendar: Calendar) {
        textView.text = SimpleDateFormat("hh:mm aa").format(calendar.time)
    }

    fun showDatePickerDialog(textView: TextView, calendar: Calendar) {
        val datePicker: DatePickerDialog
        context?.let {
            datePicker = DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    setDate(textView, calendar)
                    updateWorkingHoursLabel()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            if (calendar == startDateTime) {
                datePicker.datePicker.maxDate = endDateTime.timeInMillis
            } else {
                datePicker.datePicker.minDate = startDateTime.timeInMillis
            }

            datePicker.show()
        }
    }

    fun showTimePickerDialog(textView: TextView, calendar: Calendar) {
        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { view, hourSelected, minuteSelected ->
                val auxiliarCalendar = calendar.clone() as Calendar
                auxiliarCalendar.apply {
                    set(Calendar.HOUR_OF_DAY, hourSelected)
                    set(Calendar.MINUTE, minuteSelected)
                }
                var differenceInHours = 0.0
                if (calendar == startDateTime) {
                    differenceInHours = differenceInHours(endDateTime, auxiliarCalendar)
                } else {
                    differenceInHours = differenceInHours(auxiliarCalendar, startDateTime)
                }
                if (differenceInHours > 0) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourSelected)
                    calendar.set(Calendar.MINUTE, minuteSelected)
                    setHour(textView, calendar)
                    updateWorkingHoursLabel()
                } else {
                    Toast.makeText(
                        context,
                        "End time must be after start time...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

}