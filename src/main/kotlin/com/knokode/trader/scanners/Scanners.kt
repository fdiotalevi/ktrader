package com.knokode.trader.scanners

import com.knokode.trader.TimeSeries
import com.knokode.trader.list

interface Criteria {
    fun defineGoal() : String
    fun match(timeSeries: TimeSeries) : Boolean
}

fun scanLisFor(listName: String, loader: (String) -> TimeSeries, criteria: Criteria) = list(listName)
        .map { stock -> loader(stock) }
        .filter { ts -> criteria.match(ts) }
        .map { it -> it.symbol}
