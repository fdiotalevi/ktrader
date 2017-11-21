package com.knokode.trader.examples

import com.knokode.trader.Screener
import com.knokode.trader.TimeSeries
import com.knokode.trader.scanners.averageVolumeIn
import com.knokode.trader.scanners.hasPositionInRange
import com.knokode.trader.scanners.lastPriceInBracket

@Screener("Stocks under 3$ with at least 100K average and low in range")
fun penniesLowInRangeWithVolume(timeSeries: TimeSeries) = lastPriceInBracket("0.5", "3")(timeSeries) &&
                                                averageVolumeIn(10, "100000", "100000000")(timeSeries) &&
                                                hasPositionInRange(30, "0", "25")(timeSeries)