androidApplication {
    namespace = "org.example.app"

    dependencies {
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation(project(":utilities"))
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("io.coil-kt:coil:2.6.0")
        implementation("androidx.activity:activity-ktx:1.9.2")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.annotation:annotation:1.8.2")
        implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
        implementation("com.google.android.flexbox:flexbox:3.0.0") // optional for future expansions
    }
}
