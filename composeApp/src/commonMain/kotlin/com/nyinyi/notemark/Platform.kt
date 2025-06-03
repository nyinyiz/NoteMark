package com.nyinyi.notemark

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
