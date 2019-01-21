package ru.makdyff.rocketbank

import android.os.Bundle
import navigations.MainNavigation

class MainActivity : FragmentActivityBase() {

    private val navigation = MainNavigation(this)

    var mass = Array(0){Array(0) { i -> i} }

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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }

}
