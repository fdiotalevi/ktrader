package com.knokode.trader.examples

import com.knokode.trader.TimeSeries
import com.knokode.trader.indicators.decimals
import com.knokode.trader.indicators.lastPrice
import com.knokode.trader.loaders.loadDailyOHLC
import com.knokode.trader.scanners.hasPositionInRange
import com.knokode.trader.scanners.scanLisFor
import java.math.BigDecimal


fun dollarRunner(timeSeries: TimeSeries) = (lastPrice(timeSeries) < BigDecimal(15)) &&
        (timeSeries.candles.count { candle -> candle.fullRange > BigDecimal(1) } > 5 )


fun readyToShoot(timeSeries: TimeSeries) = dollarRunner(timeSeries) &&
        (decimals(lastPrice(timeSeries)) < BigDecimal(20)) &&
        hasPositionInRange(30, "0", "20")(timeSeries)


fun main(args: Array<String>) {
    fun loader(symbol: String) = loadDailyOHLC(symbol, 100)

    println(scanLisFor("allStocks", ::loader, ::readyToShoot))
}
