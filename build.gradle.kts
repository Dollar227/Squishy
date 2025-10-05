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

plugins {
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
    `dokka-convention`
}


allprojects {
    plugins.apply("dokka-convention")

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/dokka/maven")
    }
}

subprojects {
    plugins.apply("com.vanniktech.maven.publish")
}

dependencies {
    dokka(project(":core"))
    dokka(project(":paper"))
    dokka(project(":sponge"))
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.projectDirectory.dir("docs/html").asFile)
    }
    dokkaPublications.javadoc {
        outputDirectory.set(layout.projectDirectory.dir("docs/javadoc").asFile)
    }
}