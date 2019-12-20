package com.akurey.bright

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.akurey.bright.databinding.FragmentPinBinding
import kotlinx.android.synthetic.main.fragment_pin.*


class PinFragment : Fragment() {
    lateinit var parent: MainActivity
    private lateinit var binding: FragmentPinBinding
    private lateinit var viewModel: PinViewModel

    // region New Instance Method
    companion object {
        fun newInstance(): PinFragment {
            val arguments = Bundle()
            val fragment = PinFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    // endregion

    // region Fragment Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pin, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editTextFirstDigit.requestFocus()
        showSoftKeyboard(editTextFirstDigit, activity)
        viewModel = ViewModelProviders.of(this).get(PinViewModel::class.java)
        binding.viewmodel = viewModel
        binding.fragment = this
        binding.setLifecycleOwner(this)
    }

    // endregion

    private fun showSoftKeyboard(view: View, activity: Activity?) {
        if (view.requestFocus()) {
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // region Public Methods
    fun onNextClicked(view: View) {
        val pin =
            viewModel.firstDigit.value + viewModel.secondDigit.value + viewModel.thirdDigit.value + viewModel.fourthDigit.value
        viewModel.setIsLoading(true)
        parent.getEmployee(pin) {
            viewModel.setIsLoading(false)
            it?.let {
                //EmployeeRepository.getInstance().saveEmployee(it)
                parent.goToTimeLog()
            } ?: run {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("Error")
                builder.setMessage("User not found, make sure pin is correct and you are connected to hte internet")
                builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }
    }

    fun onFirstDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.setFirstDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextSecondDigit.requestFocus()
        }
    }

    fun onSecondDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
//        if (viewModel.secondDigit.value.isNullOrEmpty() && s.isEmpty()) {
//            viewModel.setFirstDigit("")
//            //editTextFirstDigit.setText("")
//            editTextFirstDigit.requestFocus()
//            return
//        }

        viewModel.setSecondDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextThirdDigit.requestFocus()
        }
    }

    fun onThirdDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
//        if (viewModel.thirdDigit.value.isNullOrEmpty() && s.isEmpty()) {
//            viewModel.setSecondDigit("")
//            editTextSecondDigit.requestFocus()
//            return
//        }

        viewModel.setThirdDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextFourthDigit.requestFocus()
        }
    }

    fun onFourthDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
//        if (viewModel.fourthDigit.value.isNullOrEmpty() && s.isEmpty()) {
//            viewModel.setThirdDigit("")
//            editTextThirdDigit.requestFocus()
//            return
//        }
        viewModel.setFourthDigit(s.toString())
    }
    // endregion
}