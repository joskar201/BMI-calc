package com.joskarman.joshuakaranja


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_bmiresult.*


class BmiResultFragment : Fragment() {

    val args: BmiResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmiresult, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val details = args.details
        msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE NORMAL "
        val numberParts = details.value.toString().split(delimiters = *charArrayOf('.'))
        wholePartTextView.text = numberParts[0]
        fractionallPartTextView.text = "." + numberParts[1]
    }

}
