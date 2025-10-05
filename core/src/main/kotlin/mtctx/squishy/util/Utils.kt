/*
 * Squishy (Squishy.core.main): Utils.kt
 * Copyright (C) 2025 mtctx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the **GNU General Public License** as published
 * by the Free Software Foundation, either **version 3** of the License, or
 * (at your option) any later version.
 *
 * *This program is distributed WITHOUT ANY WARRANTY;** see the
 * GNU General Public License for more details, which you should have
 * received with this program.
 *
 * SPDX-FileCopyrightText: 2025 mtctx
 * SPDX-License-Identifier: GPL-3.0-only
 */

package mtctx.squishy.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

fun Duration.prettyPrint(): String {
    val hours = inWholeHours
    val minutes = (this - hours.hours).inWholeMinutes
    val seconds = (this - hours.hours - minutes.minutes).inWholeSeconds
    val millis = (this - hours.hours - minutes.minutes - seconds.seconds).inWholeMilliseconds

    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        if (seconds > 0) append("${seconds}s ")
        if (millis > 0) append("${millis}ms")
        if (isEmpty()) append("0ms")
    }.trim()
}

private const val TICK_MILLIS = 50L
val Long.ticks: Duration
    get() = (this * TICK_MILLIS).milliseconds

val Duration.inWholeTicks: Long
    get() = inWholeMilliseconds / TICK_MILLIS