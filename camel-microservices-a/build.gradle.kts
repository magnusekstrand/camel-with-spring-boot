plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "se.callistaenterprise.demoproject"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.9.0")
  implementation("org.apache.camel.springboot:camel-activemq-starter:4.9.0")
  implementation("org.apache.camel.springboot:camel-kafka-starter:4.9.0")
  implementation("org.apache.camel.springboot:camel-http-starter:4.9.0")
  implementation("org.apache.camel.springboot:camel-csv-starter:4.9.0")
  implementation("org.apache.camel.springboot:camel-jackson-starter:4.9.0")
  compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
