import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    id("org.jetbrains.kotlin.kapt") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.1.0"

    application
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")

    val micronautVersion = "1.3.2"
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")

    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut.configuration:micronaut-openapi")

    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")

    annotationProcessor("io.micronaut:micronaut-inject-java")
    annotationProcessor("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
}

application {
    mainClassName = "com.github.jakubiec.treasure_hunt.ApplicationKt"
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<ShadowJar> {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}

