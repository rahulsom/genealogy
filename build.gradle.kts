plugins {
  `java-library`
  id("com.github.rahulsom.waena.root").version("0.18.1")
  id("com.github.rahulsom.waena.published").version("0.18.1")
  id("com.diffplug.spotless").version("7.2.1")
  id("com.adarshr.test-logger").version("4.0.0")
}

repositories {
  mavenCentral()
}

group = "com.github.rahulsom"
description = "Genealogy"

dependencies {
  annotationProcessor("org.projectlombok:lombok:1.18.42")
  compileOnly("org.projectlombok:lombok:1.18.42")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.13.4")
  testImplementation("org.assertj:assertj-core:3.24.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.4")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
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

tasks.withType<Test> {
  useJUnitPlatform()
}