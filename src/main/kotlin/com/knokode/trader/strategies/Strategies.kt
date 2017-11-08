package com.knokode.trader.strategies

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.*


enum class Direction { SHORT, LONG }

interface Strategy {
    fun evaluate(index: Int) : Position?
}

data class Trade(val symbol: String, val quantity: BigDecimal, val price: BigDecimal, val time: Date) {

    constructor(symbol: String, quantity: Int, price: BigDecimal, time: Date)
            : this(symbol, BigDecimal(quantity), price, time)

    constructor(symbol: String, quantity: Int, price: String, time: Date)
            : this(symbol, BigDecimal(quantity), BigDecimal(price), time)

    constructor(symbol: String, quantity: BigDecimal, price: String, time: Date)
            : this(symbol, quantity, BigDecimal(price), time)

}

class Position(val initialTrade: Trade, val executionStrategy: (Int) -> Unit) {

    constructor(initialTrade: Trade) : this(initialTrade, {} )

    val symbol = initialTrade.symbol
    val startTime = initialTrade.time

    val trades = mutableListOf(initialTrade)

    fun quantity() : BigDecimal = when {
        trades.isEmpty() -> BigDecimal.ZERO
        else -> trades.sumBy { it.quantity }
    }

    fun averagePrice() : BigDecimal = when(quantity()) {
        BigDecimal.ZERO -> BigDecimal.ZERO
        else -> trades.sumBy { it.quantity * it.price }.divide(quantity(), 2, HALF_UP)
    }

    fun submitTrade(trade: Trade) {
        require(trade.symbol == symbol)
        trades.add(0, trade)
    }

    fun close(price: BigDecimal, date: Date) : Trade {
        val trade = Trade(symbol, quantity().negate(), price, date)
        this.submitTrade(trade)
        return trade
    }

    fun close(price: String, date: Date) = close(BigDecimal(price), date)

    fun isOpen() = quantity() > BigDecimal.ZERO

    private fun <T> MutableList<T>.sumBy(extractor: (T) -> BigDecimal) : BigDecimal {
        return this.map(extractor).reduce { acc, close -> acc + close }
    }

}


fun BigDecimal.isBetween(low: BigDecimal, high: BigDecimal) = this >= low && this <= high
fun BigDecimal.isBetween(low: String, high: String) = this > BigDecimal(low) && this < BigDecimal(high)
fun BigDecimal.isBetween(low: Int, high: Int) = this > BigDecimal(low) && this < BigDecimal(high)

fun Long.isBetween(low: Long, high: Long) = this >= low && this <= high