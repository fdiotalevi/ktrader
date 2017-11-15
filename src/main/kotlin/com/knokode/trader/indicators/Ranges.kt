package com.knokode.trader.indicators

import com.knokode.trader.TimeSeries
import java.math.BigDecimal

class Range(val timeSeries: TimeSeries, val size: Int) : Indicator {

    override fun invoke(index: Int): BigDecimal {
        val startRange = maxOf(0, index - size + 1)
        val relevantCandles = (startRange..index).map { it -> timeSeries(it) }
        return relevantCandles.maxBy{ it -> it.high }!!.high - relevantCandles.minBy { it -> it.low }!!.low
    }

}

class PositionInRange(val price: BigDecimal, val timeSeries: TimeSeries, val size: Int) : Indicator {

    override fun invoke(index: Int): BigDecimal {
        val startRange = maxOf(0, index - size + 1)
        val relevantCandles = (startRange..index).map { it -> timeSeries(it) }
        val maxHigh = relevantCandles.maxBy { it -> it.high }!!.high
        val minLow = relevantCandles.minBy { it -> it.low }!!.low
        return (price - minLow) / (maxHigh - minLow) * BigDecimal(100)
    }


}