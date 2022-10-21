import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.10"
  `maven-publish`
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

java {
  withSourcesJar()
  withJavadocJar()
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      versionMapping {
        usage("java-api") {
          fromResolutionOf("runtimeClasspath")
        }
        usage("java-runtime") {
          fromResolutionResult()
        }
      }
      pom {
        name.set("Math")
        artifactId = "math"
        description.set("A concise description of my library")
        url.set("http://www.example.com/library")
        properties.set(
          mapOf(
            "myProp" to "value",
            "prop.with.dots" to "anotherValue"
          )
        )
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("luuthehien1992")
            name.set("Luu The Hien")
            email.set("luuthehien1992@gmail.com")
          }
        }
      }
    }
  }
  repositories {
    maven {
      // change URLs to point to your repos, e.g. http://my.org/repo
      val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
      val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
      url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
    }
  }
}

tasks.javadoc {
  if (JavaVersion.current().isJava9Compatible) {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
  }
}