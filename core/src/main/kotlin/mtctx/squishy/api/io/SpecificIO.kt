/*
 * Squishy (Squishy.core.main): SpecificIO.kt
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

package mtctx.squishy.api.io

import mtctx.squishy.foundation.Outcome
import mtctx.squishy.foundation.failure
import mtctx.squishy.foundation.success
import java.nio.charset.Charset
import java.nio.file.OpenOption
import java.nio.file.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter

open class SpecificIO(val filePath: Path) {
    open fun write(content: Any, bufferSize: Int, charset: Charset, vararg openOptions: OpenOption) =
        filePath.bufferedWriter(charset, bufferSize, *openOptions).use { writer -> writer.write(content.toString()) }

    open fun write(content: List<Any>, bufferSize: Int, charset: Charset, vararg openOptions: OpenOption) =
        filePath.bufferedWriter(charset, bufferSize, *openOptions)
            .use { writer ->
                content.forEach { line -> writer.appendLine(line.toString()) }
            }

    open fun readLine(line: Int, bufferSize: Int, charset: Charset): Outcome<String> =
        filePath.bufferedReader(charset, bufferSize).useLines { lines ->
            val result = lines.drop(line).firstOrNull()
            if (result == null) failure("Line $line not found")
            else success(result)
        }

    open fun readAllLines(bufferSize: Int, charset: Charset): List<String> =
        filePath.bufferedReader(charset, bufferSize).useLines { it.toList() }

    open fun read(bufferSize: Int, charset: Charset): String =
        filePath.bufferedReader(charset, bufferSize).use { it.readText() }
}