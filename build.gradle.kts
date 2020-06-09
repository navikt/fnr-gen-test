plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
    jacoco
    id("info.solidsoft.pitest").version("1.5.1")
    id("com.jfrog.bintray").version("1.8.5")
    id("maven-publish")
    id("net.researchgate.release").version("2.8.1")
    id("com.github.breadmoirai.github-release").version("2.2.12")
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

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Javadoc::class) {

}
val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

artifacts {
    sourcesJar
    javadocJar
}

pitest {
    junit5PluginVersion.set("0.12")
}

val githubUser: String? by project
val githubPassword: String? by project

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/fnr-gen-test")
            credentials {
                username = githubUser
                password = githubPassword
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {

            pom {
                name.set("fnr-gen-test")
                description.set("Fnr generator for test")
                url.set("https://github.com/navikt/fnr-gen-test")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/navikt/fnr-gen-test.git")
                    developerConnection.set("scm:git:https://github.com/navikt/fnr-gen-test.git")
                    url.set("https://github.com/navikt/fnr-gen-test")
                }
            }
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

githubRelease {
    token { githubPassword }
    owner.set("navikt")
    targetCommitish.set("HEAD")
    body { """
        # Release $version
    """.trimIndent()
    }
    tagName.set("${project.version}")
    releaseAssets.from("build/libs")
}
