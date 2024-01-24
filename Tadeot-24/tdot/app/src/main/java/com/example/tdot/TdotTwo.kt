package com.example.tdot

import android.content.Intent
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.RequiresApi
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import java.io.File

class TdotTwo: RobotActivity(), RobotLifecycleCallbacks {
    private var animate: Animate? = null
    // Run the animate action asynchronously.
    //val animateFuture: Future<Void>? = animate?.async()?.run()
    private lateinit var qiContext: QiContext
    //private var mediaPlayer: MediaPlayer? = null
    private var currentDance: Int = R.raw.pepper_dance_fein
   // private val dances: List<Dance> = getListOfDances()
    var videoView: VideoView? = null
    var mediaController: MediaController? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pepper_dances)

        videoView = findViewById<VideoView>(R.id.videoView) as VideoView?
        if(mediaController == null) {
            mediaController = MediaController(this)

            mediaController!!.setAnchorView(videoView)
        }
        videoView!!.setMediaController(mediaController)
        videoView!!.setVideoURI(Uri.parse("android.resource://"
                + packageName + "/" + R.raw.dance_pepper_video))
        videoView!!.requestFocus()


        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.playboi_carti_stop_breathing_official_audio_k4zvzbrb)
        val button: Button = findViewById<Button>(R.id.playAnimation)
        button.setOnClickListener {
           // val resourceStr = this.resources.getResourceName(R.raw.playboi_carti_stop_breathing_official_audio_k4zvzbrb)
            //val resourceURI: Uri = Uri.parse("android.resource://" + this.packageName + "/" + R.raw.playboi_carti_stop_breathing_official_audio_k4zvzbrb);

         if(mediaPlayer.isPlaying){
             mediaPlayer.pause()
         }
            videoView!!.start()

            /*mediaPlayer.setDataSource(resourceStr)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }*/
            currentDance = R.raw.pepper_dance_fein
            mediaPlayer.start()

            QiSDK.register(this, this)
        }
       /*val secondButton: Button = findViewById(R.id.secondDancePlay)
       secondButton.setOnClickListener {
           //val resourceStr = this.resources.getResourceName(R.raw.die_affen_rasen_durch_den_wald_song)
           // val resourceURI: Uri = Uri.parse("android.resource://" + this.packageName + "/" + R.raw.die_affen_rasen_durch_den_wald_song);
            currentDance = R.raw.die_affen_rasen_durch_den_wald
          if(mediaPlayer.isPlaying){
               mediaPlayer.pause()
          }
           *//*mediaPlayer.release()
           mediaPlayer.setDataSource(resourceStr)
           mediaPlayer.prepareAsync()
           mediaPlayer.setOnPreparedListener {
               mediaPlayer.start()
           }*//*
           mediaPlayer.start()
           QiSDK.register(this, this)
       }*/
        val stopButton: Button = findViewById(R.id.stopVideo)
        stopButton.setOnClickListener {
            mediaPlayer.pause()
            videoView!!.stopPlayback()
            videoView!!.resume()
        }
        val backToMainActivity: Button = findViewById<Button>(R.id.backToMain)
        backToMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            mediaPlayer.stop()
            startActivity(intent)
        }
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
            .withResources(currentDance) // Set the animation resource.
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

    public fun saySomething(sayText: String, pepperFile: Int) {
        // Create a phrase.

        val phrase: Phrase = Phrase(sayText)

        val animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(pepperFile) // Set the animation resource.
            .build() // Build the animation.

        val say: Say = SayBuilder.with(qiContext)
            .withPhrase(phrase)
            .build()

        say.run()
    }

    public fun saySomething(sayText: String) {
        // Create a phrase.
        val phrase: Phrase = Phrase(sayText)

        val say: Say = SayBuilder.with(qiContext)
            .withPhrase(phrase)
            .build()

        say.run()
    }
}