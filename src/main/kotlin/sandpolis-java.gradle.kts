//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

plugins {
	id("java")
	id("eclipse")
	id("idea")
}

// Configure Java plugin
java {
	modularity.inferModulePath.set(true)

	withJavadocJar()
	withSourcesJar()

	toolchain {
		languageVersion.set(JavaLanguageVersion.of(16))
	}
}

// Configure unit testing
tasks.test {
	useJUnitPlatform()

	testLogging {
		setExceptionFormat("full")
	}
}

// Configure Eclipse plugin
eclipse {
	project {
		name = project.name
		comment = project.name
	}
}

// Configure module version
tasks.withType<JavaCompile> {
	options.javaModuleVersion.set(provider { project.version as String })
}

// Add repository configuration
repositories {
	mavenLocal()
	mavenCentral()

	for (dep in listOf("core.foundation")) {
		maven("https://maven.pkg.github.com/sandpolis/com.sandpolis.${dep}") {
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}
}
