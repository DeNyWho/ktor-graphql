val ktorVersion: String by project
val kgraphqlVersion: String by project
val kmongoVersion: String by project
val koinVersion: String by project
val bcryptVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<Jar>{
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { url = uri("https://repo1.maven.org/maven2/") }
    maven { url = uri("https://mvnrepository.com/artifact/io.insert-koin")}
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main")
sourceSets["main"].java.srcDirs("src/main/kotlin")

kotlin.sourceSets["test"].kotlin.srcDirs("src/test")
sourceSets["test"].java.srcDirs("src/test")

sourceSets["main"].resources.srcDirs("src/main")

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INHERIT
}

dependencies {
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("com.apurebase:kgraphql:$kgraphqlVersion")
    implementation("com.apurebase:kgraphql-ktor:$kgraphqlVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("at.favre.lib:bcrypt:$bcryptVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
