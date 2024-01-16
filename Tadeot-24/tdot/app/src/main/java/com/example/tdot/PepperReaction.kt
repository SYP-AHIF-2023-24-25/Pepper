package com.example.tdot

import kotlin.random.Random

enum class PepperReaction {
    GREET {
        fun getHelloAnimation(): Int {
            val listOfGreetings: List<Int> =
                listOf(R.raw.hello_a001,
                        R.raw.hello_a002,
                R.raw.hello_a003)
            val randomIndex: Int = Random.nextInt(listOfGreetings.size)
            return listOfGreetings[randomIndex - 1]
        }
          },
    SAY_GOODBYE {
                fun getWavingAnimation() = R.raw.waving_left_b001
                },
    GOOD_GAME {
              fun getSaluteAnimation() = R.raw.salute_left_b001
              }
}