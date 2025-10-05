/*
 * Squishy (Squishy.core.main): Plugin.kt
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

import mtctx.squishy.foundation.Outcome
import mtctx.squishy.foundation.failure
import mtctx.squishy.foundation.success
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

interface Plugin {
    fun load()
    fun enable()
    fun disable()
}

object PluginRegistry {
    private val plugins: MutableMap<KClass<out Plugin>, Plugin> = ConcurrentHashMap()

    fun <T : Plugin> register(plugin: T) {
        plugins[plugin::class] = plugin
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Plugin> get(pluginClass: KClass<T>): Outcome<T> =
        (plugins[pluginClass] as? T)
            ?.let(::success)
            ?: failure("No Squishy plugin registered for ${pluginClass.simpleName}")

    fun unregister(plugin: Plugin) {
        plugins.remove(plugin::class)
    }
}

inline fun <reified T : Plugin> plugin(): Outcome<T> = PluginRegistry.get(T::class)
inline fun <reified T : Plugin> T.plugin(): T =
    when (val result = PluginRegistry.get(T::class)) {
        is Outcome.Success -> result.value
        is Outcome.Failure -> error("Squishy Plugin ${T::class.simpleName} not registered yet.")
    }

inline fun <reified T : Plugin> T.pluginOrNull(): T? =
    when (val result = PluginRegistry.get(T::class)) {
        is Outcome.Success -> result.value
        is Outcome.Failure -> null
    }