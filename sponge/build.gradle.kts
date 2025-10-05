/*
 *     Squishy: build.gradle.kts
 *     Copyright (C) 2025 mtctx
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import org.spongepowered.gradle.plugin.config.PluginLoaders

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
    signing
}

group = "dev.mtctx.library"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/") {
        name = "spongepowered-repo"
    }
}

dependencies {
    implementation(project(":core"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

sponge {
    apiVersion("13.0.0")
    license("GPL-3.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("squishy") {
        displayName("Squishy")
        entrypoint("mtctx.squishy.Squishy")
        description("Simple API for SpongeMC Plugins to give more control with less work!")
        links {
            homepage("https://github.com/mtctx/Squishy")
            source("https://github.com/mtctx/Squishy")
        }
        dependency("spongeapi") {
            loadOrder(org.spongepowered.plugin.metadata.model.PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

mavenPublishing {
    coordinates(group.toString(), "squishy-sponge", version.toString())

    pom {
        name.set("Squishy-Sponge")
        description.set("Squishy API for SpongeMC Plugins to give more control with less work!")
        inceptionYear.set("2025")
        url.set("https://github.com/mtctx/Squishy/tree/main/sponge")

        licenses {
            license {
                name.set("GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                distribution.set("repo")
            }
        }

        scm {
            url.set("https://github.com/mtctx/Squishy/tree/main/sponge")
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

tasks.build {
    dependsOn("shadowJar")
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType<AbstractArchiveTask>().configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
