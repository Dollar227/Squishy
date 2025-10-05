/*
 * Squishy (Squishy.paper.main): PaperSquishyLogger.kt
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

import java.util.logging.Logger

class PaperSquishyLogger(val paperLogger: Logger) : SquishyLogger {
    override suspend fun debug(vararg content: String, logToConsole: Boolean) =
        debugSync(*content, logToConsole = logToConsole)

    override fun debugSync(vararg content: String, logToConsole: Boolean) =
        content.forEach { line ->
            paperLogger.fine(line)
        }

    override suspend fun info(vararg content: String, logToConsole: Boolean) =
        infoSync(*content, logToConsole = logToConsole)

    override fun infoSync(vararg content: String, logToConsole: Boolean) =
        content.forEach { line ->
            paperLogger.info(line)
        }

    override suspend fun warn(vararg content: String, logToConsole: Boolean) =
        warnSync(*content, logToConsole = logToConsole)

    override fun warnSync(vararg content: String, logToConsole: Boolean) =
        content.forEach { line -> paperLogger.warning(line) }


    override suspend fun error(vararg content: String, logToConsole: Boolean) =
        errorSync(*content, logToConsole = logToConsole)

    override fun errorSync(vararg content: String, logToConsole: Boolean) =
        content.forEach { line ->
            paperLogger.severe(line)
        }

    override suspend fun fatal(vararg content: String, logToConsole: Boolean) =
        errorSync(*content, logToConsole = logToConsole)

    override fun fatalSync(vararg content: String, logToConsole: Boolean) =
        errorSync(*content, logToConsole = logToConsole)
}