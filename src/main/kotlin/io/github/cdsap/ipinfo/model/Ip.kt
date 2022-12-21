package io.github.cdsap.ipinfo.model

data class Ip(
    val isp: String,
    val query: String,
    val city: String,
    val regionName: String,
    val country: String,
    val timeZone: String,
    val lat: String,
    val long: String
)
