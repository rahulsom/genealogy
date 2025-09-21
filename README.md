Genealogy
====

Java library to generate names for people based on distributions inferred from US Census data for 1990 and 2000.

Sources
----

The first names are gathered from the 1990 census. The last names are gathered from the 2000 census.

Usage
----

This is the old API, low level but (slightly) faster.
It has no support for race.
```java
NameDbUsa instance = NameDbUsa.getInstance();
String firstName = instance.getMaleName();
String lastName = instance.getLastName();

String welcome = "Hello " + firstName + " " + lastName;

System.out.println (welcome);
```

This is the new API, high level but with great support for race.
```java
NameDbUsa instance = NameDbUsa.getInstance();
Person person = instance.getPerson();
String description = person.getFirstName() + " " +
        person.getLastName() + " is of race '" +
        person.getRace() + "' and gender '" +
        person.getGender() + "'.";
System.out.println (description);
```

If you want consistent names, this is an option
```java
long l = 42;
Person person = instance.getPerson(l);
```

If you want to download the library from Sonatype, add this to your dependencies
section:

    com.github.rahulsom:genealogy:1.3

To browse the latest builds, you can see [MavenRepository](http://mvnrepository.com/artifact/com.github.rahulsom/genealogy). It also has instructions for Maven, Gradle, Ivy, sbt, etc.

And add this to your repositories section:

    https://oss.sonatype.org/content/groups/public

You can browse latest snapshots on:

    http://oss.sonatype.org/content/repositories/snapshots/com/github/rahulsom/genealogy/

