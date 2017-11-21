package com.knokode.trader.examples

import com.knokode.trader.Screener
import com.knokode.trader.loaders.loadDailyOHLC
import com.knokode.trader.scanners.scanLisFor


val screeners = arrayOf(
        ::readyToShoot,
        ::penniesLowInRangeWithVolume
)

fun main(args: Array<String>) {
    fun loader(symbol: String) = loadDailyOHLC(symbol, 100)

    screeners.forEach { screener ->
        screener.annotations.forEach { annotation ->
            if (annotation is Screener) println(annotation.description)
        }
        println(scanLisFor("allStocks", ::loader, screener))
    }

}