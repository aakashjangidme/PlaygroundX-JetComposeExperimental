package com.example.playgroundx.domain.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
