plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
    jacoco
    id("info.solidsoft.pitest").version("1.5.1")
    id("com.jfrog.bintray").version("1.8.5")
}

repositories {
    jcenter()
}

group = "no.nav.test"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.platform:junit-platform-runner:1.6.1")
    testImplementation("org.assertj:assertj-core:3.16.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

pitest {
    //adds dependency to org.pitest:pitest-junit5-plugin and sets "testPlugin" to "junit5"
    junit5PluginVersion.set("0.12")
    // ...
}