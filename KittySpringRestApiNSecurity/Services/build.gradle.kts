plugins {
    java
    id("org.springframework.boot") version "3.2.5" apply false
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
}

group = "ru.verbitskiy"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

tasks.create("prepareKotlinBuildScriptModel") {

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.3")
    implementation("org.hibernate:hibernate-jcache:6.4.4.Final")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.postgresql:postgresql:42.2.19")
    implementation(project(":KittySpringRestApiNSecurity:DAO"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
