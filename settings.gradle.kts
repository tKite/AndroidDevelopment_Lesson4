pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "rumireaKirillovaLesson4"
include(":app")
include(":app:audio_player")
include(":player")
include(":thread")
include(":data_thread")
include(":looper")
include(":cryptoloader")
include(":serviceapp")
include(":workmanager")
