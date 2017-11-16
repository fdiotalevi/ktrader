package com.knokode.trader.scanners

import com.knokode.trader.TimeSeries
import com.knokode.trader.list

fun scanLisFor(listName: String, loader: (String) -> TimeSeries, criteria: (TimeSeries) -> Boolean) = list(listName)
        .map { stock -> loader(stock) }
        .filter { ts -> criteria(ts) }
        .map { it -> it.symbol}
