package com.knokode.trader.strategies

import com.knokode.trader.TimeSeries

class StrategyRunner(val strategies: Array<Strategy>) {

    val positions = mutableMapOf<String, Position>()

    fun run(timeSeries: TimeSeries) {
        (0..timeSeries.size).forEach { index ->

            positions.forEach {
                (symbol, position) -> if (position.isOpen()) position.executionStrategy(index)
            }

            strategies.forEach { strategy ->
                val position = strategy.evaluate(index)
                addOrReject(position)
            }
        }
    }

    private fun addOrReject(position: Position?) {
        when {
            position == null -> null
            positions.containsKey(position.symbol) -> null
            else -> positions[position.symbol] = position
        }
    }


}