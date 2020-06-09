# FnrGen

- Easy valid fnr generation for testcode

## Usage

* gradle

```
testImplementation "no.nav.test.fnr:fnrgen:$version"
```

* maven

```
<dependency>
    <groupId>no.nav.test</groupId>
    <artifactId>fnrgen</artifactId>
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