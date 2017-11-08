package com.knokode.trader.indicators

import com.knokode.trader.loaders.loadOHLCFile
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.time.Duration

@RunWith(JUnitPlatform::class)
class MovingAveragesSpec : Spek({

    describe("simple moving average 2") {
        val timeseries =  loadOHLCFile(
                symbol = "AMD",
                duration = Duration.ofMinutes(5),
                filePath = "src/main/resources/smaExample.csv")
        val sma2 = SMA(2, timeseries)

        it("is zero for the first element") {
            assertEquals(BigDecimal.ZERO, sma2(0))
            assertTrue(sma2(1) > BigDecimal.ZERO)
        }

        it("calculates correct values") {
            assertEquals(0, sma2(1).compareTo(BigDecimal("14.1")))
            assertEquals(0, sma2(2).compareTo(BigDecimal("14.4")))
            assertEquals(0, sma2(3).compareTo(BigDecimal("14.8")))
            assertEquals(0, sma2(4).compareTo(BigDecimal("14.6")))
        }

    }

})