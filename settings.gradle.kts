rootProject.name = "product-service"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":subprojects:presentation", ":subprojects:domain", ":subprojects:infrastructure")
include("subprojects:boot")
