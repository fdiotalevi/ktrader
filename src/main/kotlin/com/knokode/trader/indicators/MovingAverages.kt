package com.knokode.trader.indicators

import com.knokode.trader.OHLC
import com.knokode.trader.TimeSeries
import java.math.BigDecimal
import java.math.RoundingMode


class SMA(val span: Int, val timeSeries: TimeSeries, val extractor : (OHLC) -> BigDecimal = OHLC::close) : Indicator {

    override fun invoke(index: Int) : BigDecimal {
        return when {
            index < span - 1 -> BigDecimal.ZERO
            else -> ((index - span + 1)..index)
                    .map { extractor(timeSeries(it)) }
                    .reduce { acc, close -> acc + close }
                    .divide(BigDecimal(span), 2, RoundingMode.HALF_UP)
        }
    }
}

