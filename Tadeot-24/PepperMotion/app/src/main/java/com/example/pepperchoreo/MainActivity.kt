package com.example.pepperchoreo


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import java.util.concurrent.Future


class MainActivity : RobotActivity(), RobotLifecycleCallbacks {
    private var animate: Animate? = null
    // Run the animate action asynchronously.
    //val animateFuture: Future<Void>? = animate?.async()?.run()
    private lateinit var qiContext: QiContext

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById<Button>(R.id.clickButton)
        button.setOnClickListener {
            QiSDK.register(this, this)
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
            .withResources(R.raw.elephant_a001) // Set the animation resource.
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

}


