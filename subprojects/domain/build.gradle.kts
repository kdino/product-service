plugins {
    alias(libs.plugins.kotlin.serialization)
}

val lib = rootProject.libs
dependencies {
    testImplementation(lib.mockk)
}
