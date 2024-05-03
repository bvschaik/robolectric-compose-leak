package com.github.bvschaik.robolectriccompose

import android.widget.Button
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(ParameterizedRobolectricTestRunner::class)
class ExampleRobolectricTest(val name: String) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        MainActivity.useCompose = true
        ShadowLog.stream = System.out
    }

    @Test
    fun `User can click through the app`() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.assertActiveFragmentIsOfType<FirstFragment>()
            scenario.clickButton()
            scenario.assertActiveFragmentIsOfType<SecondFragment>()
            scenario.clickButton()
            scenario.assertActiveFragmentIsOfType<ThirdFragment>()
            scenario.clickButton()
            scenario.assertActiveFragmentIsOfType<FourthFragment>()
        }
    }

    private fun ActivityScenario<MainActivity>.clickButton() {
        if (MainActivity.useCompose) {
            composeTestRule.onNodeWithText("Click me").performClick()
        } else {
            onActivity { activity ->
                activity.findViewById<Button>(R.id.click_me_button).performClick()
            }
        }
    }

    private inline fun <reified T : Fragment> ActivityScenario<MainActivity>.assertActiveFragmentIsOfType() {
        assertActiveFragmentIsOfType(this, T::class.java)
    }

    private fun assertActiveFragmentIsOfType(
        activityScenario: ActivityScenario<MainActivity>,
        fragmentClass: Class<out Fragment>
    ) {
        activityScenario.onActivity { activity ->
            val fragments = activity.supportFragmentManager.fragments
            assertNotNull(fragments)
            assertEquals(1, fragments.size)
            assertEquals(fragmentClass, fragments.first().javaClass)
        }
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun parameters(): List<String> {
            return List(100) { it.toString() }
        }
    }
}