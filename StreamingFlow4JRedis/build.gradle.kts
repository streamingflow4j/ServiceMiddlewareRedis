plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.service"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}

}

repositories {
	mavenCentral()
}

extra["springAiVersion"] = "1.0.0-M5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.ai:spring-ai-redis-store-spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// https://mvnrepository.com/artifact/org.antlr/antlr4-runtime
	implementation("org.antlr:antlr4-runtime:4.7")

	// https://mvnrepository.com/artifact/redis.clients/jedis
	implementation("redis.clients:jedis:5.3.0-beta1")
	// https://mvnrepository.com/artifact/com.espertech/esper
	implementation("com.espertech:esper:7.1.0")
	// https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
	implementation("com.googlecode.json-simple:json-simple:1.1.1")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
	implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
	// https://mvnrepository.com/artifact/org.burningwave/core
	implementation("org.burningwave:core:12.65.2")
	//Zipkin observability////
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("io.zipkin.reporter2:zipkin-reporter-brave")



}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}
tasks.withType<JavaExec> {
	jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
tasks.withType<Test> {
	useJUnitPlatform()
}
