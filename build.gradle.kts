plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.study"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(rootProject.libs.bundles.basicStarter)
	implementation(rootProject.libs.lombok)
	implementation(rootProject.libs.jakarta.validation.api)

	// test 관련 의존성
	testImplementation(rootProject.libs.spring.boot.starter.test)
	testRuntimeOnly(rootProject.libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
