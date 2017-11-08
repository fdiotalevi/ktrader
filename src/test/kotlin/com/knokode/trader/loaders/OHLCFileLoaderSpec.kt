package com.knokode.trader.loaders

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.Duration

@RunWith(JUnitPlatform::class)
class FileLoaderMapper : Spek({

    describe("the OHLC file loader") {
        val timeseries =  loadOHLCFile(
                symbol = "AMD",
                duration = Duration.ofMinutes(5),
                filePath = "src/main/resources/be365aee619876a51617308e4d372c4e.csv")

        it("can load a timeseries from a file") {
            assertNotNull(timeseries)
            assertEquals(3913, timeseries.size)
        }
    }


})