package com.joskarman.joshuakaranja


import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_details_entry.*
import java.text.DecimalFormat
import kotlin.random.Random

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

        val weightNPicker = weight.apply {
            maxValue = 120
            minValue = 5
            value = 50
        }

        val heightNPicker = height.apply {
            maxValue = 250
            minValue = 30
            value = 150
        }

        val genderNPicker = gender.apply {
            minValue = 0
            maxValue = 1
            value = Random.nextInt(from = 0, until = 2)
            displayedValues = arrayOf("Male", "Female")
        }

        backIcon.setOnClickListener {
            activity!!.onBackPressed()
        }

        calculateButton.setOnClickListener {
            val details = BMIDetails(
                value = calCulateBMI(
                    weightNPicker.value,
                    heightNPicker.value
                ),
                name = nameET.text.toString(),
                gender = genderNPicker.value
            )

            if (details.name.isEmpty()){
                Toast.makeText(context,"Please enter your name",Toast.LENGTH_SHORT).show()
            }else{
                val action = DetailsEntryFragmentDirections.actionDetailsEntryFragmentToBMIResultFragment(details)
                (activity as MainActivity).showAdd()
                navController.navigate(action)
            }
        }
    }

    fun calCulateBMI(weight :Int,height: Int): Double{
        val cmHeight = height.toDouble() / 100.toDouble()
        return DecimalFormat("##.##").format((weight / (cmHeight * cmHeight))).toDouble()
    }
}

@Parcelize
data class BMIDetails(val value: Double, val name: String, val gender: Int): Parcelable

