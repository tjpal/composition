import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

group = "dev.tjpal"
version = "1.1.0"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    id("maven-publish")
}

kotlin {
    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlin.stdlib)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("composition") {
            from(components["kotlin"])
            groupId = project.group.toString()
            artifactId = "composition"
            version = project.version.toString()
        }
    }
    repositories {
        maven {
            name = "local"
            mavenLocal()
        }
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/jpal/composition")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.register<PublishToMavenRepository>("publishToLocal") {
    publication = publishing.publications["composition"] as MavenPublication
    repository = publishing.repositories["local"] as MavenArtifactRepository
}

tasks.register<PublishToMavenRepository>("publishToGithub") {
    publication = publishing.publications["composition"] as MavenPublication
    repository = publishing.repositories["github"] as MavenArtifactRepository
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

kotlin {
    jvmToolchain(24)
}