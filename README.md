# FnrGen Test Support
- Easy valid fnr generation for testcode



![Build and deploy](https://github.com/navikt/fnr-gen-test/workflows/Build%20and%20deploy/badge.svg)

[![Gradle Status](https://gradleupdate.appspot.com/navikt/fnr-gen-test/status.svg)](https://gradleupdate.appspot.com/navikt/fnr-gen-test/status)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

[![](https://jitpack.io/v/navikt/fnr-gen-test.svg)](https://jitpack.io/#navikt/fnr-gen-test)

## Usage

* build.gradle

```
repositories {
     maven { url 'https://jitpack.io' }
}
testImplementation "com.github.navikt:fnr-gen-test:$version"
```

* maven

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
  	<url>https://jitpack.io</url>
  </repository>
</repositories>
...
<dependencies>
...
<dependency>
    <groupId>com.github.navikt</groupId>
    <artifactId>fnr-gen-test</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
...
</dependencies>
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