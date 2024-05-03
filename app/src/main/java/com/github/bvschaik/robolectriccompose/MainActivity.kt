package com.github.bvschaik.robolectriccompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goTo(FirstFragment(), isInitial = true)
    }

    fun goTo(fragment: Fragment, isInitial: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val name = fragment::class.java.canonicalName
        if (isInitial) {
            fragmentTransaction.add(R.id.fragmentContainer, fragment, name)
        } else {
            // animate fragment transaction
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            fragmentTransaction.addToBackStack(name)
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, name)
        }
        fragmentTransaction.commit()
    }

    companion object {
        var useCompose = true
    }
}