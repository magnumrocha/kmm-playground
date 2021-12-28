package com.magnumrocha.kmm.playground

import kotlinx.coroutines.CoroutineScope

expect fun runTest(skip: Boolean = false, block: suspend CoroutineScope.() -> Unit)
