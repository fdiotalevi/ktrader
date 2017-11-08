package com.knokode.trader

import com.knokode.trader.indicators.SMA
import com.knokode.trader.strategies.Position
import com.knokode.trader.strategies.Strategy
import com.knokode.trader.strategies.Trade
import java.math.BigDecimal
import java.util.*

class BuyOnSma20Strategy(val timeSeries: TimeSeries) : Strategy {

    val sma20 = SMA(span = 20, timeSeries = timeSeries)

    override fun evaluate(index: Int) : Position? {
        val smaVal = sma20(index)
        val price = timeSeries(index)
        val prevPrice = timeSeries(index - 1)

        return when {
            prevPrice.close > smaVal && price.close < smaVal -> Position(initialTrade = Trade("AMD", BigDecimal.TEN, BigDecimal(10), Date()),
                    executionStrategy = {})
            else -> null
        }
    }

}