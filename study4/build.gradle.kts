plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
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
	// 2주차: JPA 기초와 H2 데이터베이스에 필요한 의존성
	implementation(rootProject.libs.bundles.basicStarter)
	implementation(rootProject.libs.bundles.springBootUtil)
	compileOnly(rootProject.libs.lombok)
	annotationProcessor(rootProject.libs.lombok)  // 롬복 어노테이션 프로세서 추가

	// H2 데이터베이스
	runtimeOnly(rootProject.libs.h2database)

	// 테스트 관련 의존성
	testImplementation(rootProject.libs.spring.boot.starter.test)
	testCompileOnly(rootProject.libs.lombok)  // 테스트에서 롬복 사용을 위한 의존성 추가
	testAnnotationProcessor(rootProject.libs.lombok)  // 테스트에서 롬복 어노테이션 프로세서 추가
	testRuntimeOnly(rootProject.libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
