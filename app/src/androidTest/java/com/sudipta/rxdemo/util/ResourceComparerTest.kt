package com.sudipta.rxdemo.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.sudipta.rxdemo.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {

    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setup() {
        resourceComparer = ResourceComparer()
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Rxdemo")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
}