package org.tasks.sync

import kotlinx.coroutines.delay
import timber.log.Timber

class Debouncer constructor(private val tag: String, private val block: suspend (Boolean) -> Unit) {
    private var count = 0

    suspend fun sync(immediate: Boolean) {
        val thisCount = ++count

        delay(1000)

        if (immediate || thisCount == count) {
            block.invoke(immediate)
        } else {
            Timber.v("debouncing $tag")
        }
    }
}