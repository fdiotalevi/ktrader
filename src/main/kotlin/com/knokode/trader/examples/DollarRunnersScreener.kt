package com.knokode.trader.examples

import com.knokode.trader.TimeSeries
import com.knokode.trader.indicators.PositionInRange
import com.knokode.trader.indicators.decimals
import com.knokode.trader.indicators.lastPrice
import com.knokode.trader.loaders.loadDailyOHLC
import com.knokode.trader.scanners.Criteria
import com.knokode.trader.scanners.scanLisFor
import java.math.BigDecimal

class DollarRunner : Criteria {

    override fun defineGoal() = "Find shares under 15$ that had a full range of a dollar at least 5 times in the past 100 days"

    override fun match(timeSeries: TimeSeries): Boolean {

        return (lastPrice(timeSeries) < BigDecimal(15)) &&
                (timeSeries.candles.count { candle -> candle.fullRange > BigDecimal(1) } > 5 )
    }

}


class ReadyToShoot : Criteria {

    override fun match(timeSeries: TimeSeries): Boolean {
        return DollarRunner().match(timeSeries) &&
                (decimals(lastPrice(timeSeries)) < BigDecimal(20)) &&
                PositionInRange(lastPrice(timeSeries), timeSeries, 30)(timeSeries.size - 1) < BigDecimal(20)
    }

    override fun defineGoal() =
        """Find shares under 15$ that had a full range of a dollar at least 5 times in the past 100 days
            - decimal between 0-20
            - they are now low in the 20 days range"""


}

fun main(args: Array<String>) {
    fun loader(symbol: String) = loadDailyOHLC(symbol, 100)

    println(scanLisFor("allStocks", ::loader, ReadyToShoot()))
}
