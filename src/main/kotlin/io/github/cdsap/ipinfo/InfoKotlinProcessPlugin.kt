package io.github.cdsap.ipinfo

import com.gradle.scan.plugin.BuildScanExtension
import io.github.cdsap.ipinfo.output.BuildScanOutput
import io.github.cdsap.ipinfo.parser.ResponseParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider

class IpInfoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.gradle.rootProject {
            val buildScanExtension = extensions.findByType(com.gradle.scan.plugin.BuildScanExtension::class.java)
            if (buildScanExtension != null) {
                buildScanReporting(project, buildScanExtension)
            }
        }
    }

    private fun buildScanReporting(
        project: Project,
        buildScanExtension: BuildScanExtension
    ) {
        val geolocation = ResponseParser().process(project.ip().get())

        buildScanExtension.buildFinished {
            if (geolocation != null) {
                BuildScanOutput(buildScanExtension, geolocation).addGeolocationInfoToBuildScan()
            }
        }
    }
}

fun Project.ip(): Provider<String> {
    return execute("curl -s --max-time 2 http://ip-api.com/line/?fields=country,regionName,city,lat,lon,timezone,isp,query")
}

fun Project.execute(command: String): Provider<String> {
    return providers.of(CommandLineWithOutputValue::class.java) {
        parameters.commands.set(command)
    }
}
