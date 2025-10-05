/*
 * Squishy (Squishy.core.main): SquishyGlobals.kt
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

package mtctx.squishy.api

import mtctx.squishy.foundation.Concurrency
import mtctx.squishy.logging.SquishyLogger
import mtctx.squishy.platform.PlatformInfo
import mtctx.squishy.util.TextColorizer
import java.nio.file.Path

object SquishyGlobals {
    @Volatile
    private var _COLORIZER: TextColorizer? = null

    @Volatile
    private var _CONCURRENCY: Concurrency? = null

    @Volatile
    private var _LOGGER: SquishyLogger? = null

    @Volatile
    private var _PLATFORM_INFO: PlatformInfo? = null

    @Volatile
    private var _PLUGIN_FOLDER: Path? = null

    val COLORIZER: TextColorizer get() = _COLORIZER ?: error("Text Colorizer not initialized yet!")
    val CONCURRENCY: Concurrency get() = _CONCURRENCY ?: error("Concurrency not initialized yet!")
    val LOGGER: SquishyLogger get() = _LOGGER ?: error("SquishyLogger not initialized yet!")
    val PLATFORM_INFO: PlatformInfo get() = _PLATFORM_INFO ?: error("Platform Info not initialized yet!")
    val PLUGIN_FOLDER: Path get() = _PLUGIN_FOLDER ?: error("Plugin Folder not initialized yet!")

    val isInitialized: Boolean
        get() = _COLORIZER != null && _CONCURRENCY != null && _LOGGER != null && _PLATFORM_INFO != null && _PLUGIN_FOLDER != null

    fun init(
        colorizer: TextColorizer,
        concurrency: Concurrency,
        logger: SquishyLogger,
        platformInfo: PlatformInfo,
        pluginFolder: Path
    ) {
        check(isInitialized) { "SquishyGlobals already initialized!" }

        _COLORIZER = colorizer
        _CONCURRENCY = concurrency
        _LOGGER = logger
        _PLATFORM_INFO = platformInfo
        _PLUGIN_FOLDER = pluginFolder
    }

    fun init(block: SquishyGlobalsInit.DSL.() -> Unit) {
        check(isInitialized) { "SquishyGlobals already initialized!" }

        val data = SquishyGlobalsInit.DSL().apply(block).build()
        _COLORIZER = data.colorizer
        _CONCURRENCY = data.concurrency
        _LOGGER = data.squishyLogger
        _PLATFORM_INFO = data.platformInfo
        _PLUGIN_FOLDER = data.pluginFolder
    }

    val Plugin.colorizer get() = COLORIZER
    val Plugin.squishyLogger get() = LOGGER
    val Plugin.concurrency get() = CONCURRENCY
    val Plugin.platformInfo get() = PLATFORM_INFO
    val Plugin.pluginFolder get() = PLUGIN_FOLDER
}

class SquishyGlobalsInit {
    data class Data(
        val colorizer: TextColorizer,
        val concurrency: Concurrency,
        val squishyLogger: SquishyLogger,
        val platformInfo: PlatformInfo,
        val pluginFolder: Path,
    )

    class DSL {
        var colorizer: TextColorizer? = null
        var concurrency: Concurrency? = null
        var squishyLogger: SquishyLogger? = null
        var platformInfo: PlatformInfo? = null
        var pluginFolder: Path? = null

        fun colorizer(colorizer: TextColorizer) = apply { this.colorizer = colorizer }
        fun concurrency(concurrency: Concurrency) = apply { this.concurrency = concurrency }
        fun squishyLogger(squishyLogger: SquishyLogger) = apply { this.squishyLogger = squishyLogger }
        fun platformInfo(platformInfo: PlatformInfo) = apply { this.platformInfo = platformInfo }
        fun pluginFolder(pluginFolder: Path) = apply { this.pluginFolder = pluginFolder }

        fun build(): Data = Data(
            requireNotNull(colorizer) { "The Squishy Plugins Text Colorizer cannot be null!" },
            requireNotNull(concurrency) { "The Squishy Plugins Concurrency cannot be null!" },
            requireNotNull(squishyLogger) { "The Squishy Plugins Logger cannot be null!" },
            requireNotNull(platformInfo) { "The Squishy Plugins Platform info cannot be null!" },
            requireNotNull(pluginFolder) { "The Squishy Plugins Plugin Folder cannot be null!" },
        )
    }
}