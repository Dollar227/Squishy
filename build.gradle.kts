import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    kotlin("jvm") version "2.2.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
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
        description("My plugin description")
        links {
            homepage("https://github.com/mtctx/Squishy")
            source("https://github.com/mtctx/Squishy")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

val javaTarget = 21 // Sponge targets a minimum of Java 21
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
