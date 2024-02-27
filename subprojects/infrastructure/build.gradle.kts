plugins {
    alias(libs.plugins.kotlin.serialization)
}

val lib = rootProject.libs
dependencies {
    implementation(projects.subprojects.domain)

    api(lib.h2)
    api(lib.exposed.core)
    api(lib.exposed.jdbc)
    api(lib.hikariCp)
    api(lib.flyway)

    // test
    testImplementation(lib.mockk)

    testFixturesImplementation(projects.subprojects.domain)
    testFixturesImplementation(lib.bundles.arrow)
}
