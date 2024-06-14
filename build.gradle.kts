import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    //id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")

}

kotlin {
    jvmToolchain(17)
}

group = "es.iesra.ctfm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    testImplementation(kotlin("test"))

    implementation("com.h2database:h2:2.2.224")


    implementation ("com.zaxxer:HikariCP:5.0.0")


    implementation("org.slf4j:slf4j-nop:2.0.6")

    implementation("org.apache.commons:commons-dbcp2:2.8.0")

}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "MainKt")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "un9pe"
            packageVersion = "1.0.0"
        }
    }
}