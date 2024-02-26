plugins {
    alias(libs.plugins.kotlin.serialization)
}

val lib = rootProject.libs
dependencies {
    implementation(projects.subprojects.domain)
}
