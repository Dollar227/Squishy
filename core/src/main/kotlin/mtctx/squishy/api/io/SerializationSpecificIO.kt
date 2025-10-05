/*
 * Squishy (Squishy.core.main): SerializationSpecificIO.kt
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

import com.charleskorn.kaml.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import mtctx.squishy.foundation.Outcome
import mtctx.squishy.foundation.failure
import mtctx.squishy.foundation.success
import java.nio.charset.Charset
import java.nio.file.OpenOption
import java.nio.file.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter

@OptIn(ExperimentalSerializationApi::class)
val defaultJson = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    allowTrailingComma = true
    useAlternativeNames = false
}

val defaultYaml = Yaml(
    configuration = YamlConfiguration(
        encodeDefaults = true,
        strictMode = false,
        extensionDefinitionPrefix = "x-",
        polymorphismStyle = PolymorphismStyle.Property,
        polymorphismPropertyName = "type",
        encodingIndentationSize = 2,
        breakScalarsAt = 120,
        sequenceStyle = SequenceStyle.Block,
        singleLineStringStyle = SingleLineStringStyle.PlainExceptAmbiguous,
        multiLineStringStyle = MultiLineStringStyle.Folded,
        ambiguousQuoteStyle = AmbiguousQuoteStyle.DoubleQuoted,
        sequenceBlockIndent = 0,
        anchorsAndAliases = AnchorsAndAliases.Permitted(100u),
        yamlNamingStrategy = null,
        codePointLimit = null,
        decodeEnumCaseInsensitive = true,
    )
)

open class SerializationSpecificIO(filePath: Path, private val serializationFormat: StringFormat = defaultYaml) :
    SpecificIO(filePath) {
    open fun <T> write(
        content: T,
        serializer: SerializationStrategy<T>,
        bufferSize: Int = DEFAULT_BUFFER_SIZE,
        charset: Charset = Charsets.UTF_8,
        vararg openOptions: OpenOption
    ) = filePath.bufferedWriter(charset, bufferSize, *openOptions)
        .use { writer ->
            writer.write(serializationFormat.encodeToString(serializer, content))
        }

    open fun <T> read(
        deserializer: DeserializationStrategy<T>,
        bufferSize: Int = DEFAULT_BUFFER_SIZE,
        charset: Charset = Charsets.UTF_8
    ): Outcome<T> = filePath.bufferedReader(charset, bufferSize).use { reader ->
        try {
            success(serializationFormat.decodeFromString(deserializer, reader.readText()))
        } catch (exception: SerializationException) {
            failure(exception.message, exception)
        } catch (exception: IllegalArgumentException) {
            failure(exception.message, exception)
        }
    }
}

class JSONSpecificIO(filePath: Path, json: Json = defaultJson) : SerializationSpecificIO(filePath, json)
class YAMLSpecificIO(filePath: Path, yaml: Yaml = defaultYaml) : SerializationSpecificIO(filePath, yaml)