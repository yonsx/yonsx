plugins {
    val checkVersion = "1.4.1"
    idea
    `java-library`
}


apply("versions.gradle.kts")

allprojects {

    group = "io.github.yonsx"
    version = "1.0.0-SNAPSHOT"

}

val commandLineProjectProp: String by project
val gradlePropertiesProp: String by project
val systemProjectProp: String by project
tasks.register("printProps") {
    doLast {
        println(commandLineProjectProp)
        println(gradlePropertiesProp)
        println(systemProjectProp)
        println(System.getProperty("system"))
    }
}

subprojects {

    apply(plugin = "java")
    apply(plugin = "maven-publish")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    repositories {
        mavenLocal()
        maven {
            url = uri("https://maven.aliyun.com/nexus/content/repositories/central")
        }
        maven {
            url = uri("https://repo.maven.apache.org/maven2")
        }
        maven {
            url = uri("https://maven.springframework.org/release")
        }
        mavenCentral()
        gradlePluginPortal()
        google()
    }


    tasks.withType<JavaCompile>()
        .configureEach {
            options.compilerArgs = listOf(
                "-Xdoclint:none",
                "-Xlint:none",
                "-nowarn"
            )

//        options.compilerArgs.clear() // remove `--release 8` set in root gradle build
//        options.compilerArgs.addAll(listOf(
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
//                "--add-exports", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED"
//        ))
        }

    dependencies {
        implementation(enforcedPlatform("com.google.guava:guava:${rootProject.extra["guaveVersion"]}"))
        implementation(enforcedPlatform("io.netty:netty-bom:${rootProject.extra["nettyVersion"]}"))

        implementation(enforcedPlatform("org.junit:junit-bom:${rootProject.extra["junitVersion"]}"))
        implementation(enforcedPlatform("org.apache.logging.log4j:log4j-bom:${rootProject.extra["log4jVersion"]}"))
        testImplementation(enforcedPlatform("com.google.truth:truth:${rootProject.extra["truthjVersion"]}"))
    }

//    publishing {
//        publications {
//            create<MavenPublication>("maven") {
//                {
//                    from(components.java)
//                    artifactId = tasks.jar.get().archiveBaseName.get()
//                }
//            }
//        }
//    }


    configurations.all {
        // 每隔24小时检查远程依赖是否存在更新，针对xxx:xxx:1.1之类的静态版本
        resolutionStrategy {
            cacheChangingModulesFor(24, TimeUnit.HOURS)
            // 每隔10分钟..
            //resolutionStrategy.cacheChangingModulesFor 10, 'minutes'
            // 采用动态版本声明的依赖缓存10分钟，针对xxx:xxx:1.1.+之类的动态版本
            cacheDynamicVersionsFor(10 * 60, TimeUnit.SECONDS)
        }
    }
}

//不使用缓存，使用仓库中最新的包
//configurations.all {
//    resolutionStrategy.cacheDynamicVersionsFor 0, 'seconds' // 动态版本 x.x.+
//    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'//  变化版本 x.x.x
//}
