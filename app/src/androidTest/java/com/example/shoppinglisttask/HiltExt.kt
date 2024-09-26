package com.example.shoppinglisttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    val scenario = FragmentScenario.launchInContainer(T::class.java, fragmentArgs)

    scenario.onFragment { fragment ->
        fragmentFactory?.let {
            fragment.fragmentManager?.fragmentFactory = it
        }

        (fragment as T).action()
    }
}
