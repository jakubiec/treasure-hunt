plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"

    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("io.kotlintest:kotlintest:2.0.7")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}

application {
    mainClassName = "com.github.jakubiec.treasure_hunt.ApplicationKt"
}

tasks {

    withType<Test> {
        useJUnitPlatform()
    }

    withType<Jar> {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }
}