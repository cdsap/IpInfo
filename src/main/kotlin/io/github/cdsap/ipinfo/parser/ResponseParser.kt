package io.github.cdsap.ipinfo.parser

import io.github.cdsap.ipinfo.model.Ip

class ResponseParser {
    // Response from ip-api keeps following format:
    //
    // United States --> country
    // California --> nameRegion
    // Marina del Rey -> city
    // 33 -> lat
    // -1 -> lon
    // America/Los_Angeles -> timeZone
    // FastNet -> isp
    // 121.x.x.x -> ip
    // We need to honor the order in the parsing.
    // If we don't get the number of fields required we return null
    fun process(result: String): Ip? {
        val lines = result.trim().split("\n")
        return if (lines.isNotEmpty() && lines.size == 8) {
            Ip(
                country = lines[0],
                regionName = lines[1],
                city = lines[2],
                lat = lines[3],
                long = lines[4],
                timeZone = lines[5],
                isp = lines[6],
                query = lines[7]
            )
        } else {
            null
        }
    }
}
