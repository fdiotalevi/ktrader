package com.knokode.trader

import java.io.File

fun list(listName: String) = File("src/main/resources/lists/$listName.csv").readLines()