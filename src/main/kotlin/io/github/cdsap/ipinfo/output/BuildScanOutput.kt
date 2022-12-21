package io.github.cdsap.ipinfo.output

import com.gradle.scan.plugin.BuildScanExtension
import io.github.cdsap.ipinfo.model.Ip

class BuildScanOutput(
    private val buildScanExtension: BuildScanExtension,
    private val ip: Ip,
) {

    fun addGeolocationInfoToBuildScan() {
        buildScanExtension.value(
            "City",
            ip.city
        )
        buildScanExtension.value(
            "Country",
            ip.country
        )
        buildScanExtension.value(
            "State",
            ip.regionName
        )
        buildScanExtension.value(
            "Ip",
            ip.query
        )
        buildScanExtension.value(
            "Isp",
            ip.isp
        )
        buildScanExtension.value(
            "Timezone",
            ip.timeZone
        )
        buildScanExtension.value(
            "Lat",
            ip.lat
        )
        buildScanExtension.value(
            "Long",
            ip.long
        )
    }
}
