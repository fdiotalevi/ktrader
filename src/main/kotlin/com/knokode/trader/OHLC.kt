package com.knokode.trader

import java.math.BigDecimal
import java.time.Duration
import java.util.*


data class TimeSeries(val symbol: String, val duration: Duration, val candles: List<OHLC>) {

    val size : Int
        get() = candles.size

    operator fun invoke(index: Int) = candles.get(index)

}

class OHLC(
        val open: BigDecimal,
        val high: BigDecimal,
        val low: BigDecimal,
        val close: BigDecimal,
        val volume: Long,
        val time: Date,
        val duration: Duration)
