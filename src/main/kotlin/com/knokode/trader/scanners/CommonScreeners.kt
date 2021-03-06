package com.knokode.trader.scanners

import com.knokode.trader.TimeSeries
import com.knokode.trader.indicators.PositionInRange
import com.knokode.trader.indicators.lastPrice
import java.math.BigDecimal

fun lastPriceInBracket(low: String, high: String) = lastPriceInBracket(BigDecimal(low), BigDecimal(high))
fun lastPriceInBracket(low: BigDecimal, high: BigDecimal) : (TimeSeries) -> Boolean {

    fun returned(timeSeries: TimeSeries) : Boolean {
        val lastPrice = lastPrice(timeSeries)
        return lastPrice in low..high
    }

    return ::returned
}

fun hasPositionInRange(rangeSize: Int, low: String, high: String) = hasPositionInRange(rangeSize, BigDecimal(low), BigDecimal(high))
fun hasPositionInRange(rangeSize: Int, low: BigDecimal, high: BigDecimal) : (TimeSeries) -> Boolean {

    fun returned(timeSeries: TimeSeries) : Boolean {
        val price = lastPrice(timeSeries)
        val positionInRange = PositionInRange(price, timeSeries, rangeSize)(timeSeries.size - 1)
        return positionInRange in low..high
    }

    return ::returned
}

fun averageVolumeIn(rangeSize: Int, low: String, high: String) = averageVolumeIn(rangeSize, BigDecimal(low), BigDecimal(high))
fun averageVolumeIn(rangeSize: Int, low: BigDecimal, high: BigDecimal) : (TimeSeries) -> Boolean {

    fun returned(timeSeries: TimeSeries) : Boolean {
        val startRange = timeSeries.size - rangeSize
        val totalVolume = (startRange..(timeSeries.size - 1)).map { timeSeries(it).volume }.sum()
        val average = BigDecimal(totalVolume) / BigDecimal(rangeSize)
        return average in low..high
    }

    return ::returned
}