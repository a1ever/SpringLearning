plugins {
	id("java")
	id("org.springframework.boot")
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.hibernate:hibernate-jcache:6.4.4.Final")
	implementation("com.h2database:h2:1.4.200")
	implementation("org.postgresql:postgresql:42.2.19")
	implementation("org.springframework.kafka:spring-kafka")

	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test:6.2.4")
}

tasks.test {
	useJUnitPlatform()
}