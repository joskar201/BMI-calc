package com.joskarman.joshuakaranja


import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_bmiresult.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


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
        displayResults(details)
        val adRequest = AdRequest.Builder().build()
        banner.loadAd(adRequest)

        RateButton.setOnClickListener {
            openPlayStore()
        }

        shareButton.setOnClickListener {
            share(saveBitmap(takeViewScreenShot(view,view.height,view.width))!!)
        }

        backIcon.setOnClickListener {
            activity!!.onBackPressed()
        }

    }

    private fun displayResults(details: BMIDetails) {
        if (details.value <= 18.5 || details.value > 25) {
            wholePartTextView.setTextColor(Color.parseColor("#ff0000"))
            fractionallPartTextView.setTextColor(Color.parseColor("#ff0000"))
        }

        when{
            details.value < 15 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE VERY SEVERELY UNDERWEIGHT "
            }
            details.value > 15 && details.value <= 16 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE SEVERELY UNDERWEIGHT"
            }
            details.value > 16 && details.value <= 18.5-> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE UNDERWEIGHT"
            }
            details.value > 18.5 && details.value <= 25 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE NORMAL"
            }
            details.value > 25 && details.value <= 30 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE OVERWEIGHT"
            }
            details.value > 30 && details.value <= 35 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE MODERATELY OBESE "
            }
            details.value > 35 && details.value <= 40 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE SEVERELY OBESE"
            }
            details.value > 40 && details.value <= 45 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE VERY SEVERELY OBESE"
            }
            details.value > 45 && details.value <= 50 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE MORBIDLY OBESE"
            }
            details.value > 50 && details.value <= 60 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE SUPER OBESE"
            }
            details.value > 60 -> {
                msgTitleTv.text = "HELLO ${details.name.toUpperCase()} ,YOU ARE HYPER OBESE"
            }
        }
        val numberParts = details.value.toString().split(delimiters = *charArrayOf('.'))
        wholePartTextView.text = numberParts[0]
        fractionallPartTextView.text = "." + numberParts[1]
    }

    private fun openPlayStore(){
        val uri: Uri = Uri.parse("market://details?id=systems.digisolwarehouse.plutus")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)

        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )

        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            //+ context!!.packageName
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=systems.digisolwarehouse.plutus")
                )
            )
        }
    }

    private fun share(sharePath: String) {
        Log.d("filePath", sharePath)
        val file = File(sharePath)
        val uri = FileProvider.getUriForFile(
            context!!,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)

    }

    private fun takeViewScreenShot(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): String?{
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        val mPath = context!!.getExternalFilesDir(null).toString() + "/" + now + ".jpeg"
        val imageFile = File(mPath)
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return imageFile.path
    }

}
