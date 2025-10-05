/*
 * Squishy (Squishy.paper): build.gradle.kts
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
    id("com.gradleup.shadow") version "8.3.0"
    signing
}

group = "dev.mtctx.library"
version = "1.0.0+mc1.21.8"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":core"))
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

mavenPublishing {
    coordinates(group.toString(), "squishy-paper", version.toString())

    pom {
        name.set("Squishy-Paper")
        description.set("Squishy API for PaperMC Plugins to give more control with less work!")
        inceptionYear.set("2025")
        url.set("https://github.com/mtctx/Squishy/tree/main/paper")

        licenses {
            license {
                name.set("GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                distribution.set("repo")
            }
        }

        scm {
            url.set("https://github.com/mtctx/Squishy/tree/main/paper")
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

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
