/*
 * Squishy (Squishy.paper.main): SquishyPlugin.kt
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

import mtctx.squishy.foundation.PaperConcurrency
import mtctx.squishy.logging.PaperSquishyLogger
import mtctx.squishy.logging.SquishyLogger
import mtctx.squishy.platform.PlatformInfo
import mtctx.squishy.util.PaperTextColorizer
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Paths

abstract class SquishyPlugin(private var customSquishyLogger: SquishyLogger? = null) : Plugin,
    JavaPlugin() {
    private lateinit var javaPlugin: JavaPlugin

    override fun onLoad() = PluginRegistry.register(this).also {
        this.javaPlugin = this

        SquishyGlobals.init(
            PaperTextColorizer(),
            PaperConcurrency(this),
            customSquishyLogger ?: PaperSquishyLogger(logger),
            PlatformInfo(
                PlatformInfo.ServerSoftwareBase.PaperBased,
                PlatformInfo.Version(server.bukkitVersion, server.minecraftVersion)
            ),
            Paths.get(dataFolder.toURI()).toAbsolutePath()
        )

        load()
    }

    override fun onEnable() = enable()
    override fun onDisable() = disable().also { PluginRegistry.unregister(this) }

    fun javaPlugin(): JavaPlugin = javaPlugin
}

fun <T : JavaPlugin> getJavaPlugin(clazz: Class<T>) = JavaPlugin.getPlugin(clazz)
inline fun <reified T : JavaPlugin> T.getJavaPlugin() = JavaPlugin.getPlugin(T::class.java)
fun <T : JavaPlugin> getProvidingJavaPlugin(clazz: Class<T>) = JavaPlugin.getProvidingPlugin(clazz)
inline fun <reified T : JavaPlugin> getProvidingJavaPlugin() = JavaPlugin.getProvidingPlugin(T::class.java)