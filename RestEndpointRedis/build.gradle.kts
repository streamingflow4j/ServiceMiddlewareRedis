plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.redis"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(19)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools
	implementation("org.springframework.boot:spring-boot-devtools")
	// https://mvnrepository.com/artifact/org.springframework/spring-beans
	implementation("org.springframework:spring-beans:7.0.0-M1")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("redis.clients:jedis")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
