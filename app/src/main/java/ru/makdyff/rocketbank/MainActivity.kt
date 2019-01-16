package ru.makdyff.rocketbank

import android.os.Bundle
import navigations.MainNavigation

class MainActivity : FragmentActivityBase() {

    private val navigation = MainNavigation(this)

    var hSize = 512
    var wSize = 512

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        navigation.start()
    }

    override fun onBackPressed() {
        navigation.backClick()
    }


}
