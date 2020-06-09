![Build and deploy](https://github.com/navikt/fnr-gen-test/workflows/Build%20and%20deploy/badge.svg)
# FnrGen Test Support
- Easy valid fnr generation for testcode

## Usage

* gradle

```
testImplementation "no.nav.test:fnr-gen-test:$version"
```

* maven

```
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

FnrGen.enkeltFnr();
```

To get a stream of fnrs call

```java
import no.nav.fnrgen.FnrGen;

FnrGen.generate() //returns Stream<String>
```