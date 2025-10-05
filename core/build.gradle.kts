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

plugins {
    kotlin("jvm")
    signing
}

group = "dev.mtctx.library"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

mavenPublishing {
    coordinates(group.toString(), "Squishy", version.toString())

    pom {
        name.set("Squishy-Sponge")
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