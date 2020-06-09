# FnrGen Test Support
- Easy valid fnr generation for testcode



![Build and deploy](https://github.com/navikt/fnr-gen-test/workflows/Build%20and%20deploy/badge.svg)

[![Gradle Status](https://gradleupdate.appspot.com/navikt/fnr-gen-test/status.svg)](https://gradleupdate.appspot.com/navikt/fnr-gen-test/status)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

![Latest version: ]

## Usage

* build.gradle

```
repositories {
   maven {
     url "https://maven.pkg.github.com/navikt/fnr-gen-test"
   }
}
testImplementation "no.nav.test:fnr-gen-test:$version"
```

* maven

```
<repositories>
  <repository>
     <url>https://maven.pkg.github.com/navikt/fnr-gen-test</url>
     <name>Nav FNR repo</name>
  </repository>
</repositories>

<dependency>
    <groupId>no.nav.test</groupId>
    <artifactId>fnr-gen-test</artifactId>
    <version>${version}</version>
</dependency>
```

* Javacode

To get one fnr call
```java
import no.nav.fnrgen.FnrGen;

FnrGen.singleFnr();
```

To get a stream of fnrs call

```java
import no.nav.fnrgen.FnrGen;

FnrGen.generate() //returns Stream<String>
```