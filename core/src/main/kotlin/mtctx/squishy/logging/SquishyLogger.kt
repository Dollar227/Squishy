/*
 * Squishy (Squishy.core.main): SquishyLogger.kt
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

package mtctx.squishy.logging

interface SquishyLogger {
    suspend fun debug(vararg content: String, logToConsole: Boolean = true): Any?
    fun debugSync(vararg content: String, logToConsole: Boolean = true): Any?

    suspend fun info(vararg content: String, logToConsole: Boolean = true): Any?
    fun infoSync(vararg content: String, logToConsole: Boolean = true): Any?

    suspend fun warn(vararg content: String, logToConsole: Boolean = true): Any?
    fun warnSync(vararg content: String, logToConsole: Boolean = true): Any?

    suspend fun error(vararg content: String, logToConsole: Boolean = true): Any?
    fun errorSync(vararg content: String, logToConsole: Boolean = true): Any?

    suspend fun fatal(vararg content: String, logToConsole: Boolean = true): Any?
    fun fatalSync(vararg content: String, logToConsole: Boolean = true): Any?
}