plugins {
    java
    idea
    application
}

dependencies {
    implementation(enforcedPlatform("io.netty:netty-bom:${rootProject.extra["nettyVersion"]}"))
    implementation(enforcedPlatform("com.fasterxml.jackson:jackson-bom:${rootProject.extra["jacksonVersion"]}"))
    implementation(enforcedPlatform("com.google.protobuf:protobuf-bom:${rootProject.extra["protobufVersion"]}"))

    implementation("com.google.guava:guava")

    implementation("io.netty:netty-common")
    implementation("io.netty:netty-buffer")
    implementation("io.netty:netty-resolver")
    implementation("io.netty:netty-resolver-dns")
    implementation("io.netty:netty-transport")
    implementation("io.netty:netty-codec")
    implementation("io.netty:netty-handler")
    implementation("io.netty:netty-transport-sctp")
    implementation("io.netty:netty-handler-proxy")
    implementation("io.netty:netty-codec-http")
    implementation("io.netty:netty-codec-http2")
    implementation("io.netty:netty-codec-socks")
    implementation("io.netty:netty-codec-haproxy")
    implementation("io.netty:netty-codec-dns")
    implementation("io.netty:netty-transport-udt")
    implementation("io.netty:netty-transport-rxtx")
    implementation("io.netty:netty-dev-tools")

    val osName = System.getProperties().getProperty("os.name")
    println("Build OS Name: $osName")
    if ("Mac OS X" === osName) {
        implementation(group = "io.netty", name = "netty-resolver-dns-native-macos", classifier = "osx-x86_64")
        implementation(group = "io.netty", name = "netty-transport-native-kqueue", classifier = "osx-x86_64")
        implementation(group = "io.netty", name = "netty-transport-native-unix-common", classifier = "osx-x86_64")
    } else if ("Linux" === osName) {
        implementation(group = "io.netty", name = "netty-transport-native-epoll", classifier = "linux-x86_64")
        implementation(group = "io.netty", name = "netty-transport-native-unix-common", classifier = "linux-x86_64")
    }

    implementation("com.fasterxml.jackson.core:jackson-databind")

    implementation("com.google.protobuf:protobuf-java")
    implementation("com.google.protobuf:protobuf-java-util")

    implementation("org.apache.logging.log4j:log4j-core")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl")


    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.google.truth:truth")
}

application {
    applicationDefaultJvmArgs = listOf(
            "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
            "-Dio.netty.tryReflectionSetAccessible=true",
            "--illegal-access=warn"
    )
    mainClassName = "io.github.yonsx.App"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
