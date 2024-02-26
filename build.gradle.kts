plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
    java
    `java-test-fixtures`
}

val lib = rootProject.libs
allprojects {
    apply{
        plugin(lib.plugins.kotlin.jvm.get().pluginId)
        plugin(lib.plugins.kotlinter.get().pluginId)
        plugin(lib.plugins.java.text.fixtures.get().pluginId)
    }

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
    }

    tasks {
        test {
            useJUnitPlatform()
        }

        compileKotlin{
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xjsr305=strict",
                    "-Xjvm-default=all"
                )
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }

        formatKotlinMain {
            exclude { it.file.path.contains("generated") }
        }
        lintKotlinMain {
            exclude { it.file.path.contains("generated") }
        }
    }

    dependencies {
        implementation(lib.bundles.ktor)
        implementation(lib.kotlinx.coroutines.core)
        implementation(lib.kotlinx.datetime)
        implementation(lib.bundles.arrow)

        // test
        testImplementation(lib.ktor.serialization)
        testImplementation(lib.kotlin.test)
        testImplementation(lib.kotlinx.coroutines.test)

        testImplementation(lib.bundles.kotest)
        testImplementation(lib.kotest.assertions.arrow)

        testFixturesImplementation(lib.ktor.serialization)
    }

}
