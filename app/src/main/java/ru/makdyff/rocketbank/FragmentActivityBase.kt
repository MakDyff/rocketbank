package ru.makdyff.rocketbank

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import java.util.concurrent.Executors

/**
 * Created by Max on 16.01.19.
 */
open class FragmentActivityBase : FragmentActivity() {
    protected val pool = Executors.newCachedThreadPool()
    protected val schedule = Executors.newScheduledThreadPool(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



}