plugins {
	kotlin("jvm") version "2.2.0-RC3"
	id("com.gradleup.shadow") version "9.0.0-beta16"
	id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.sailuna"
version = property("version") as String

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
	mavenCentral()
	maven {
		name = "papermc"
		url = uri("https://repo.papermc.io/repository/maven-public/")
	}
}

dependencies {
	compileOnly("io.papermc.paper", "paper-api", "1.21.5-R0.1-SNAPSHOT")
}

tasks {
	processResources {
		expand("version" to project.version as String)
	}

	shadowJar {
		archiveClassifier.set("")
		minimize()
	}

	build {
		dependsOn(shadowJar)
	}

	runServer {
		minecraftVersion("1.21.5")
	}
}
