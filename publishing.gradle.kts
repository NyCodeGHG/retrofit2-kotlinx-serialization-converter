apply(plugin = "org.jetbrains.dokka")
apply(plugin = "org.gradle.maven-publish")
apply(plugin = "org.gradle.signing")

val sonatypeUsername = System.getenv("SONATYPE_USER") ?: findProperty("sonatypeUser").toString()
val sonatypePassword = System.getenv("SONATYPE_PASSWORD") ?: findProperty("sonatypePassword").toString()

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(project.extensions.getByName<SourceSetContainer>("sourceSets").named("main").get().allSource)
}

val dokkaJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(tasks.getByName("dokkaHtml"))
}

val isSnapshot = version.toString().endsWith("SNAPSHOT")

val configurePublishing: PublishingExtension.() -> Unit = {
    repositories {
        maven {
            name = "oss"
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (isSnapshot) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(sourcesJar)
            artifact(dokkaJar)
            pom {
                name.set("Retrofit 2 Kotlin Serialization Converter")
                description.set("A Converter.Factory for Kotlin's serialization support.")
                url.set("https://github.com/NyCodeGHG/retrofit2-kotlinx-serialization-converter")

                licenses {
                    license {
                        name.set("Apache-2.0 License")
                        url.set("https://github.com/NyCodeGHG/retrofit2-kotlinx-serialization-converter/blob/main/LICENSE.txt")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/NyCodeGHG/retrofit2-kotlinx-serialization-converter/issues")
                }

                scm {
                    connection.set("https://github.com/NyCodeGHG/retrofit2-kotlinx-serialization-converter.git")
                    url.set("https://github.com/NyCodeGHG/retrofit2-kotlinx-serialization-converter")
                }

                developers {
                    developer {
                        id.set("jakewharton")
                        name.set("Jake Wharton")
                    }
                    developer {
                        id.set("nycode")
                        name.set("NyCode")
                        email.set("nico@nycode.de")
                        url.set("https://nycode.de")
                        timezone.set("Europe/Berlin")
                    }
                }
            }
        }
    }
}

val configureSigning: SigningExtension.() -> Unit = {
    val signingKey = System.getenv("SIGNING_KEY") ?: findProperty("signingKey")?.toString()
    val signingPassword = System.getenv("SIGNING_PASSWORD") ?: findProperty("signingPassword")?.toString()
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(
            String(java.util.Base64.getDecoder().decode(signingKey.toByteArray())),
            signingPassword
        )
    }

    publishing.publications.withType<MavenPublication> {
        sign(this)
    }
}

extensions.configure("signing", configureSigning)
extensions.configure("publishing", configurePublishing)

val Project.publishing: PublishingExtension
    get() =
        (this as ExtensionAware).extensions.getByName("publishing") as PublishingExtension
