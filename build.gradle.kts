plugins {
    `java-library`
    id("com.github.rahulsom.waena.root").version("0.18.1")
    id("com.github.rahulsom.waena.published").version("0.18.1")
    id("io.freefair.lombok") version "9.0.0-rc2"
}

repositories {
    mavenCentral()
}

group = "com.github.rahulsom"
description = "Genealogy"
