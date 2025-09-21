plugins {
    `java-library`
    id("com.github.rahulsom.waena.root").version("0.18.1")
    id("com.github.rahulsom.waena.published").version("0.18.1")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

group = "com.github.rahulsom"
description = "Genealogy"
