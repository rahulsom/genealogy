Genealogy
====

Java library to generate names for people based on distributions inferred from US Census data for 1990 and 2000.

Sources
----

The first names are gathered from the 1990 census. The last names are gathered from the 2000 census.

Usage
----

```java
NameDbUsa instance = NameDbUsa();
String firstName = instance.getMaleName();
String lastName = instance.getLastName();

String welcome = "Hello " + firstName + " " + lastName;

System.out.println (welcome);
```

If you want to download the library from Sonatype, add this to your dependencies
section:

    com.github.rahulsom:genealogy:1.0-SNAPSHOT

To browse the latest builds, you can see [MavenRepository](http://mvnrepository.com/artifact/com.github.rahulsom/genealogy). It also has instructions for Maven, Gradle, Ivy, sbt, etc.

And add this to your repositories section:

    https://oss.sonatype.org/content/groups/public
    
You can browse latest snapshots on:

    http://oss.sonatype.org/content/repositories/snapshots/com/github/rahulsom/genealogy/

Builds
------

This project is being built on travis. See
[https://travis-ci.org/rahulsom/genealogy](https://travis-ci.org/rahulsom/genealogy)
It gets automatically deployed to [Sonatype OSS](https://oss.sonatype.org/).

[![Build Status](https://travis-ci.org/rahulsom/genealogy.png)](https://travis-ci.org/rahulsom/genealogy)
