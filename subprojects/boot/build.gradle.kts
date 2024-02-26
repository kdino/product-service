plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
    java
    application
}

application {
    mainClass.set("ki.product.MainKt")
}

val lib = rootProject.libs
dependencies {
    implementation(projects.subprojects.presentation)
    implementation(projects.subprojects.domain)
    implementation(projects.subprojects.infrastructure)

    testImplementation(lib.ktor.server.tests) {
        exclude("ch.qos.logback", "logback-classic")
    }
    testImplementation(lib.ktor.client.serialization)

    testImplementation(projects.subprojects.presentation)
    testImplementation(projects.subprojects.domain)
    testImplementation(projects.subprojects.infrastructure)
    testImplementation(testFixtures(projects.subprojects.presentation))
    testImplementation(testFixtures(projects.subprojects.domain))
    testImplementation(testFixtures(projects.subprojects.infrastructure))
}
