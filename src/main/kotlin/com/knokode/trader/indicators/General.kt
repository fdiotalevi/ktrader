package com.knokode.trader.indicators

import com.knokode.trader.TimeSeries

class LastPrice(val timeSeries: TimeSeries) : Indicator {

    override fun invoke(index: Int) = timeSeries.candles.last().close

}