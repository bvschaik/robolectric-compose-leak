package com.github.bvschaik.robolectriccompose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

abstract class ButtonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (MainActivity.useCompose) {
            createComposeView()
        } else {
            createLayoutView(container)
        }
    }

    protected abstract fun getNextFragment(): Fragment

    private fun goToNext() {
        (requireActivity() as MainActivity).goTo(getNextFragment())
    }

    private fun createLayoutView(container: ViewGroup?): View {
        return layoutInflater.inflate(R.layout.fragment_button, container, false).apply {
            findViewById<Button>(R.id.click_me_button).setOnClickListener { goToNext() }
        }
    }

    private fun createComposeView(): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Button(onClick = { goToNext() }) {
                    Text("Click me")
                }
            }
        }
    }
}

class FirstFragment : ButtonFragment() {
    override fun getNextFragment() = SecondFragment()
}

class SecondFragment : ButtonFragment() {
    override fun getNextFragment() = ThirdFragment()
}

class ThirdFragment : ButtonFragment() {
    override fun getNextFragment() = FourthFragment()
}

class FourthFragment : Fragment()
