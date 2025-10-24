import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("maven-publish")
}

group = "dev.tjpal"
version = "1.0.0"

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composition"
        browser {
            commonWebpackConfig {
                outputFileName = "composition.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
        binaries.library()
    }

    js(IR) {
        browser()
        binaries.library()
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = project.group.toString()
            packageVersion = project.version.toString()
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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