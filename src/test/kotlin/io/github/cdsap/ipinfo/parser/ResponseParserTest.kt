package io.github.cdsap.ipinfo.parser

import org.junit.Test


class ResponseParserTest{

    @Test
    fun emptyResponseReturnsNull(){
        val responseParser = ResponseParser().process("")

        assert(responseParser == null)
    }

    @Test
    fun invalidResponseReturnsNull(){
        val responseParser = ResponseParser().process("""
            Argentina
            Cordoba
        """.trimIndent())

        assert(responseParser == null)
    }

    @Test
    fun correctFormatReturnIp(){
        val responseParser = ResponseParser().process("""
            United States
            California
            Marina del Rey
            33
            1
            America/Los_Angeles
            Charter Communications
            1.1.1.1
        """.trimIndent())

        assert(responseParser?.city == "Marina del Rey")
        assert(responseParser?.country == "United States")
        assert(responseParser?.lat == "33")
        assert(responseParser?.long == "1")
        assert(responseParser?.timeZone == "America/Los_Angeles")
        assert(responseParser?.regionName == "California")
        assert(responseParser?.isp == "Charter Communications")
        assert(responseParser?.query == "1.1.1.1")
    }
}
