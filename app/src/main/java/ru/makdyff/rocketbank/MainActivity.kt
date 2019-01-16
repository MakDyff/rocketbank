package ru.makdyff.rocketbank

import android.os.Bundle
import navigations.MainNavigation

class MainActivity : FragmentActivityBase() {

    private val navigation = MainNavigation(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        navigation.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navigation.backClick()
    }
}
