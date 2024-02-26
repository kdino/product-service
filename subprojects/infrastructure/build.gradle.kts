plugins {
    alias(libs.plugins.kotlin.serialization)
}

val lib = rootProject.libs
dependencies {

    implementation(projects.subprojects.domain)

    api(lib.jasync.postgresql)

    // test
    testImplementation(lib.mockk)

    testFixturesImplementation(projects.subprojects.domain)
    testFixturesImplementation(lib.bundles.arrow)
    testFixturesImplementation(lib.bundles.testcontainers)
}
