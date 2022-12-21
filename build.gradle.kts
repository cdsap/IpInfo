plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
}

group = "io.github.cdsap"
version = "0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation("com.gradle.enterprise:com.gradle.enterprise.gradle.plugin:3.12.1")
    testImplementation("junit:junit:4.13.2")
}

gradlePlugin {
    plugins {
        create("IpInfoPlugin") {
            id = "io.github.cdsap.ipinfo"
            displayName = "Ip Information in your Build Scan"
            description = "Retrieve geolocation information and includes it in the Build Scan"
            implementationClass = "io.github.cdsap.ipinfo.IpInfoPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/cdsap/IpInfo"
    vcsUrl = "https://github.com/cdsap/IpInfo"
    tags = listOf("kotlin", "ipinfo")
}

publishing {
    repositories {
        maven {
            name = "Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            credentials {
                username = System.getenv("USERNAME_SNAPSHOT")
                password = System.getenv("PASSWORD_SNAPSHOT")
            }
        }
        maven {
            name = "Release"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = System.getenv("USERNAME_SNAPSHOT")
                password = System.getenv("PASSWORD_SNAPSHOT")
            }
        }
    }
    publications {
        create<MavenPublication>("ipInfoPublication") {
            from(components["java"])
            artifactId = "ipinfo"
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                scm {
                    connection.set("scm:git:git://github.com/cdsap/IpInfo/")
                    url.set("https://github.com/cdsap/IpInfo/")
                }
                name.set("IpInfo")
                url.set("https://github.com/cdsap/IpInfo/")
                description.set(
                    "Retrieve geolocation information and includes it in the Build Scan"
                )
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("cdsap")
                        name.set("Inaki Villar")
                    }
                }
            }
        }
    }
}
