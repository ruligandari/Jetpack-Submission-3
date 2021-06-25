package com.dicoding.ruligandari.jetpack_submission3.util

import java.util.concurrent.Executor

class AppExecutorTest : Executor{
    override fun execute(command: Runnable) {
        command.run()
    }
}