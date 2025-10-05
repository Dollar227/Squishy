/*
 * Squishy (Squishy.paper.main): PaperConcurrency.kt
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

package mtctx.squishy.foundation

import mtctx.squishy.util.inWholeTicks
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import kotlin.time.Duration

class PaperConcurrency(private val javaPlugin: JavaPlugin) : Concurrency {
    override fun runSync(task: () -> Unit) = BukkitTaskHandle(javaPlugin.server.scheduler.runTask(javaPlugin, task))
    override fun runAsync(task: () -> Unit) =
        BukkitTaskHandle(javaPlugin.server.scheduler.runTaskAsynchronously(javaPlugin, task))

    override fun runLater(delay: Duration, task: () -> Unit) =
        BukkitTaskHandle(javaPlugin.server.scheduler.runTaskLater(javaPlugin, task, delay.inWholeTicks))

    override fun runRepeating(initialDelay: Duration, interval: Duration, task: () -> Unit) =
        BukkitTaskHandle(
            javaPlugin.server.scheduler.runTaskTimer(
                javaPlugin,
                task,
                initialDelay.inWholeTicks,
                interval.inWholeTicks
            )
        )
}

class BukkitTaskHandle(private val bukkitTask: BukkitTask) : TaskHandle {
    override fun cancel() = bukkitTask.cancel()
    override val isActive: Boolean
        get() = !bukkitTask.isCancelled
}