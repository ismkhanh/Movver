package com.ik.movverexample.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class TimeUtilsTest {

    @Test
    fun `test now time`() {
        val currentTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse("2022-01-01T10:00:00Z")!!
        val relativeTime = TimeUtilsImpl().getRelativeTime("2022-01-01T10:00:00Z", currentTime)
        assertEquals("Now", relativeTime)
    }

    @Test
    fun `test minutes ago`() {
        val currentTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse("2022-01-01T10:10:00Z")!!
        val relativeTime = TimeUtilsImpl().getRelativeTime("2022-01-01T10:00:00Z", currentTime)
        assertEquals("10 minutes ago", relativeTime)
    }

    @Test
    fun `test hours ago`() {
        val currentTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse("2022-01-01T15:00:00Z")!!
        val relativeTime = TimeUtilsImpl().getRelativeTime("2022-01-01T10:00:00Z", currentTime)
        assertEquals("5 hours ago", relativeTime)
    }

    @Test
    fun `test days ago`() {
        val currentTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse("2022-01-03T10:00:00Z")!!
        val relativeTime = TimeUtilsImpl().getRelativeTime("2022-01-01T10:00:00Z", currentTime)
        assertEquals("2 days ago", relativeTime)
    }

    @Test
    fun `test invalid time string`() {
        val relativeTime = TimeUtilsImpl().getRelativeTime("invalid-time")
        assertEquals("Unknown time", relativeTime)
    }
}