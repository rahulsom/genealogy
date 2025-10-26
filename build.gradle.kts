import nebula.plugin.contacts.Contact
import nebula.plugin.contacts.ContactsExtension
import org.gradle.kotlin.dsl.delegateClosureOf

plugins {
  id("java-library")
  id("com.github.rahulsom.waena.root").version("0.20.0")
  id("com.github.rahulsom.waena.published").version("0.20.0")
  id("com.diffplug.spotless").version("8.0.0")
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

  testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0")
  testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.0")
  testImplementation("org.assertj:assertj-core:3.27.6")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0")

  testImplementation("org.mockito:mockito-core:5.20.0")
  testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")
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

testlogger {
  theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
}

extensions.findByType<ContactsExtension>()?.apply {
  addPerson(
    "rahulsom@noreply.github.com",
    delegateClosureOf<Contact> {
      moniker("Rahul Somasunderam")
      roles("owner")
      github("https://github.com/rahulsom")
    },
  )
}