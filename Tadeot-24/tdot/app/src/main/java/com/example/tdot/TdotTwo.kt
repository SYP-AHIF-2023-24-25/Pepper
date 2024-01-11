package com.example.tdot

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class TdotTwo: RobotActivity(), RobotLifecycleCallbacks {
    private var animate: Animate? = null
    // Run the animate action asynchronously.
    //val animateFuture: Future<Void>? = animate?.async()?.run()
    private lateinit var qiContext: QiContext
    //private var mediaPlayer: MediaPlayer? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pepper_dances)

        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.clash_of_clans_raiding)

        val button: Button = findViewById<Button>(R.id.playAnimation)
        button.setOnClickListener {
            mediaPlayer.start()
            QiSDK.register(this, this)
        }
        val backToMainActivity: Button = findViewById<Button>(R.id.backToMain)
        backToMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            mediaPlayer.stop()
            startActivity(intent)
        }
        //val fileContent = TdotTwo::class.java.getResource("/raw")
        //val test = getAllFiles("res/raw")

        // val myUri: Uri = Uri.parse("") // initialize Uri here
        /*val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(applicationContext, "res/raw")
            prepare()
            start()
        }*/

    }

    override fun onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {

        this.qiContext = qiContext
        // Create an animation.
        val animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.dance_b001) // Set the animation resource.
            .build() // Build the animation.

        // Create an animate action.
        val animate = AnimateBuilder.with(qiContext) // Create the builder with the context.
            .withAnimation(animation) // Set the animation.
            .build() // Build the animate action.
            .also { this.animate = it }

        // Add an on started listener to the animate action.
        this.animate = animate

        // Run the animate action asynchronously.
        val animateFuture = animate.async().run()


    }

    override fun onRobotFocusLost() {
        // The robot focus is lost.
        // Remove on started listeners from the animate action.
        animate?.removeAllOnStartedListeners()
    }

    override fun onRobotFocusRefused(reason: String) {
        // The robot focus is refused.
    }


    private fun getAllFiles(path: String) {
        File(path).walk()
            .forEach {
                println(it)
            }
    }

}