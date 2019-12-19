package com.akurey.bright


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_pin.*


class PinFragment : Fragment() {
    lateinit var parent: MainActivity
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_button?.setOnClickListener {
            buttonClick()
        }
    }

    fun buttonClick() {
    }
}
