package com.joskarman.joshuakaranja


import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_details_entry.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class DetailsEntryFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        calculateButton.setOnClickListener {
            navController.navigate(R.id.action_detailsEntryFragment_to_BMIResultFragment)
        }

        weight.apply {
            maxValue = 120
            minValue = 5
            value = 50
            setOnValueChangedListener { picker, oldVal, newVal ->
               Toast.makeText(context,"$newVal",Toast.LENGTH_SHORT).show()
            }
        }

        height.apply {
            maxValue = 120
            minValue = 5
            value = 30
            showDividers
        }

        gender.apply {
            minValue = 0
            maxValue = 1
            value = Random.nextInt(from = 0, until = 2)
            displayedValues = arrayOf("Male", "Female")

            setOnValueChangedListener { picker, oldVal, newVal ->
                Toast.makeText(context,"$newVal",Toast.LENGTH_SHORT).show()
            }
        }

    }


}
