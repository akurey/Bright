package com.akurey.bright

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.akurey.bright.databinding.FragmentPinBinding
import com.amazonaws.services.s3.model.S3DataSource
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
        //S3DataSource.Utils().showSoftKeyboard(editTextFirstDigit, activity)
        viewModel = ViewModelProviders.of(this).get(PinViewModel::class.java)
        binding.viewmodel = viewModel
        binding.fragment = this
        binding.setLifecycleOwner(this)
    }

    // region Public Methods
    fun onNextClicked(view: View) {
    }

    fun onFirstDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.setFirstDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextSecondDigit.requestFocus()
        }
    }

    fun onSecondDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.setSecondDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextThirdDigit.requestFocus()
        }
    }

    fun onThirdDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.setThirdDigit(s.toString())
        if (s.isNotEmpty()) {
            editTextFourthDigit.requestFocus()
        }
    }

    fun onFourthDigitChange(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.setFourthDigit(s.toString())
    }

    // endregion
}