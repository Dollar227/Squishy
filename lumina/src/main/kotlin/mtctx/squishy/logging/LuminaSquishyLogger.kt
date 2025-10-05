/*
 * Squishy (Squishy.lumina.main): LuminaSquishyLogger.kt
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

import mtctx.lumina.v4.Lumina
import mtctx.lumina.v4.LuminaConfig
import mtctx.lumina.v4.MessageDSL
import mtctx.lumina.v4.UseSynchronousFunctionsWithCaution
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class LuminaSquishyLogger(val lumina: Lumina) : SquishyLogger {
    constructor(config: LuminaConfig) : this(Lumina(config))
    constructor(pluginName: String) : this(Lumina(LuminaConfig(name = "Plugin $pluginName")))

    fun waitForCoroutinesToFinish(timeout: Duration = 5.seconds) =
        lumina.waitForCoroutinesToFinish(timeout)

    override suspend fun debug(vararg content: String, logToConsole: Boolean) =
        lumina.debug(*content, logToConsole = logToConsole)

    suspend fun debug(block: MessageDSL.() -> Unit) =
        lumina.debug(block)

    @UseSynchronousFunctionsWithCaution
    override fun debugSync(vararg content: String, logToConsole: Boolean) =
        lumina.debugSync(*content, logToConsole = logToConsole)

    @UseSynchronousFunctionsWithCaution
    fun debugSync(block: MessageDSL.() -> Unit) =
        lumina.debugSync(block)

    override suspend fun info(vararg content: String, logToConsole: Boolean) =
        lumina.info(*content, logToConsole = logToConsole)

    suspend fun info(block: MessageDSL.() -> Unit) =
        lumina.info(block)

    @UseSynchronousFunctionsWithCaution
    override fun infoSync(vararg content: String, logToConsole: Boolean) =
        lumina.infoSync(*content, logToConsole = logToConsole)

    @UseSynchronousFunctionsWithCaution
    fun infoSync(block: MessageDSL.() -> Unit) =
        lumina.infoSync(block)

    override suspend fun warn(vararg content: String, logToConsole: Boolean) =
        lumina.warn(*content, logToConsole = logToConsole)

    suspend fun warn(block: MessageDSL.() -> Unit) =
        lumina.warn(block)

    @UseSynchronousFunctionsWithCaution
    override fun warnSync(vararg content: String, logToConsole: Boolean) =
        lumina.warnSync(*content, logToConsole = logToConsole)

    @UseSynchronousFunctionsWithCaution
    fun warnSync(block: MessageDSL.() -> Unit) =
        lumina.warnSync(block)

    override suspend fun error(vararg content: String, logToConsole: Boolean) =
        lumina.error(*content, logToConsole = logToConsole)

    suspend fun error(block: MessageDSL.() -> Unit) =
        lumina.error(block)

    @UseSynchronousFunctionsWithCaution
    override fun errorSync(vararg content: String, logToConsole: Boolean) =
        lumina.errorSync(*content, logToConsole = logToConsole)

    @UseSynchronousFunctionsWithCaution
    fun errorSync(block: MessageDSL.() -> Unit) =
        lumina.errorSync(block)

    override suspend fun fatal(vararg content: String, logToConsole: Boolean) =
        lumina.fatal(*content, logToConsole = logToConsole)

    suspend fun fatal(block: MessageDSL.() -> Unit) =
        lumina.fatal(block)

    @UseSynchronousFunctionsWithCaution
    override fun fatalSync(vararg content: String, logToConsole: Boolean) =
        lumina.fatalSync(*content, logToConsole = logToConsole)

    @UseSynchronousFunctionsWithCaution
    fun fatalSync(block: MessageDSL.() -> Unit) =
        lumina.fatalSync(block)
}
