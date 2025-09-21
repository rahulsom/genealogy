plugins {
  `java-library`
  id("com.github.rahulsom.waena.root").version("0.18.1")
  id("com.github.rahulsom.waena.published").version("0.18.1")
  id("io.freefair.lombok").version("8.14.2")
  id("com.diffplug.spotless").version("7.2.1")
}

repositories {
  mavenCentral()
}

group = "com.github.rahulsom"
description = "Genealogy"

spotless {
  java {
    importOrder()
    removeUnusedImports()
    googleJavaFormat()
  }
  kotlinGradle {
    target("*.gradle.kts", "settings.gradle.kts")
    ktlint()
  }
  format("misc") {
    target("*.md", ".gitignore")
    prettier(mapOf("prettier" to "2.8.8"))
  }
  format("yaml") {
    target("**/*.yml")
    prettier(mapOf("prettier" to "2.8.8"))
      .config(mapOf("parser" to "yaml"))
  }
}