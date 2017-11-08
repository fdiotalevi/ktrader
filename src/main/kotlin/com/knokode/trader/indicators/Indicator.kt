package com.knokode.trader.indicators

import java.math.BigDecimal

interface Indicator {

    operator fun invoke(index: Int) : BigDecimal
}

