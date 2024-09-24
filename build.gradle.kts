plugins {
    id("com.android.application") version "7.1.0" apply false
    id("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath(kotlin("gradle-plugin", version = "1.6.21"))
    }}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}