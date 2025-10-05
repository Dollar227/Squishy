/*
 * Squishy (Squishy.core): build.gradle.kts
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

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.2.0"
    signing
}

group = "dev.mtctx.library"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    api("com.charleskorn.kaml:kaml:0.97.0")
    compileOnly("net.kyori:adventure-api:4.24.0")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk21")
}

mavenPublishing {
    coordinates(group.toString(), "squishy-core", version.toString())

    pom {
        name.set("Squishy-Core")
        description.set("Core of Squishy.")
        inceptionYear.set("2025")
        url.set("https://github.com/mtctx/Squishy/tree/main/core")

        licenses {
            license {
                name.set("GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                distribution.set("repo")
            }
        }

        scm {
            url.set("https://github.com/mtctx/Squishy/tree/main/core")
            connection.set("scm:git:git@github.com:mtctx/Squishy.git")
            developerConnection.set("scm:git:ssh://git@github.com:mtctx/Squishy.git")
        }

        developers {
            developer {
                id.set("mtctx")
                name.set("mtctx")
                email.set("me@mtctx.dev")
            }
        }

    }

    configure(KotlinJvm(JavadocJar.Dokka("dokkaGenerateJavadoc"), sourcesJar = true))

    signAllPublications()
    publishToMavenCentral(automaticRelease = true)
}

signing {
    useGpgCmd()
}

val javaTarget = 21
kotlin {
    jvmToolchain(javaTarget)
}