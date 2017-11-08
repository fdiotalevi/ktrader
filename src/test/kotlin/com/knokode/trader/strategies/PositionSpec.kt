package com.knokode.trader.strategies

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.*
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.*

@RunWith(JUnitPlatform::class)
class PositionSpec: Spek({

    describe("a position") {

        val trade = Trade("AMD", BigDecimal(100), BigDecimal("1.54"), Date())
        val position = Position(trade)

        it("it is initialised correctly by the initial trade") {
            assertEquals(trade.symbol, position.symbol)
            assertEquals(trade.quantity, position.quantity())
            assertEquals(trade.price, position.averagePrice())
            assertEquals(1, position.trades.size)
            assertTrue(position.isOpen())
        }

        it("it is double position with a new trade") {
            position.submitTrade(trade)
            assertEquals(2, position.trades.size)
            assertEquals(trade.quantity.multiply(BigDecimal(2)), position.quantity())
            assertEquals(trade.price, position.averagePrice())
        }

        it("it updates the average price correctly") {
            position.submitTrade(Trade("AMD", 200, "1.64", Date()))
            assertEquals(3, position.trades.size)
            assertEquals(BigDecimal(400), position.quantity())
            assertEquals("1.59", position.averagePrice().toString())
        }

        it("fails submitting a trade for a different symbol") {
            try {
                position.submitTrade(Trade("AAPL", 200, "1.64", Date()))
                fail("Illegal Argument Exception has not been raised")
            }
            catch (e: IllegalArgumentException) { }
        }

        it("can close a position") {
            position.close("1.7", Date())
            assertEquals(4, position.trades.size)
            assertEquals(BigDecimal.ZERO, position.quantity())
            assertFalse(position.isOpen())
        }

    }

})
