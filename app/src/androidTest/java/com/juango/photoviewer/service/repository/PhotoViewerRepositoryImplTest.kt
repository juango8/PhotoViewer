package com.juango.photoviewer.service.repository

import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PhotoViewerRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun hiltTest() {
        ViewMatchers.assertThat("This is a TEST string", CoreMatchers.containsString("TEST string"))
    }
}