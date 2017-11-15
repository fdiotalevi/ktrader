package com.knokode.trader.loaders

import com.knokode.trader.OHLC
import com.knokode.trader.TimeSeries
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.sort
import org.litote.kmongo.toList
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.Duration

val client = KMongo.createClient()
val database = client.getDatabase("MARKET")

data class OHLCRecord(val date: String, val open: BigDecimal, val high: BigDecimal, val low: BigDecimal, val close: BigDecimal, val volume:  Long)

fun loadDailyOHLC(symbol: String, maxDays: Int? = 65) : TimeSeries {

    val collection = database.getCollection<OHLCRecord>(symbol)
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    fun String.toDate() = sdf.parse(this)

    return TimeSeries(
            symbol = symbol,
            duration = Duration.ofDays(1),
            candles = collection.find().sort("{date: -1}").limit(maxDays!!).toList().reversed()
                    .map { record ->
                        OHLC(record.open, record.high, record.low, record.close, record.volume, record.date.toDate(), Duration.ofDays(1))
                    }
    )
}
