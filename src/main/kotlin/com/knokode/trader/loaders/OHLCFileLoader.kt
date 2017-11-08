package com.knokode.trader.loaders

import com.knokode.trader.OHLC
import com.knokode.trader.TimeSeries
import java.io.FileReader
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.Duration

fun loadOHLCFile(symbol: String, duration: Duration, filePath: String): TimeSeries {

    val ohlcs = FileReader(filePath)
            .readLines()
            .filter { line ->
                !line.startsWith("timestamp")
            }
            .map { line ->
                fromLine(line, duration)
            }
            .reversed()

    return TimeSeries(
            symbol = symbol,
            duration = duration,
            candles = ohlcs)

}

fun fromLine(line: String, duration: Duration) : OHLC {
    val elements = line.split(",").take(6)
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    return OHLC(
            time = sdf.parse(elements[0]),
            open = BigDecimal(elements[1]),
            high = BigDecimal(elements[2]),
            low = BigDecimal(elements[3]),
            close = BigDecimal(elements[4]),
            volume = elements[5].toLong(),
            duration = duration
    )
}