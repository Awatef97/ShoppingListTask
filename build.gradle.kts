plugins {
    id("com.android.application") version "7.1.0" apply false
    id("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath(kotlin("gradle-plugin", version = "1.8.0"))
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}