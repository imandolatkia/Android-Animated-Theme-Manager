package com.dolatkia.example

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dolatkia.example.databinding.ActivityStartBinding
import com.dolatkia.example.multiFragmentSample.MyFragmentActivity
import com.dolatkia.example.reverseSample.ReverseActivity
import com.dolatkia.example.singleActivitySample.SingleActivity


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // full screen app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // create and bind views
        val binder = ActivityStartBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // set click listener for singleActivitySampleButton
        binder.singleActivitySampleButton.setOnClickListener {
            val myIntent = Intent(this, SingleActivity::class.java)
            this.startActivity(myIntent)
        }

        // set click listener for fragmentSampleButton
        binder.fragmentSampleButton.setOnClickListener {
            val myIntent = Intent(this, MyFragmentActivity::class.java)
            this.startActivity(myIntent)
        }

        // set click listener for reverseAnimation
        binder.reverseAnimation.setOnClickListener {
            val myIntent = Intent(this, ReverseActivity::class.java)
            this.startActivity(myIntent)
        }

        // set click listener for github
        binder.github.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/imandolatkia/Android-Animated-Theme-Manager")
                )
            )
        }
    }
}