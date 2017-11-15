package com.knokode.trader.indicators

import com.knokode.trader.TimeSeries
import java.math.BigDecimal

class CloseAt(val timeSeries: TimeSeries) : Indicator {

    override fun invoke(index: Int) = if (timeSeries.candles.isNotEmpty()) timeSeries(index).close else BigDecimal(-1)
}


fun lastPrice(timeSeries: TimeSeries) = CloseAt(timeSeries)(timeSeries.size - 1)

fun decimals(price: BigDecimal) = (price * BigDecimal(100)) % BigDecimal(100)