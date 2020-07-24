plugins {
    java
    idea
    application
}

dependencies {
    implementation(enforcedPlatform("io.netty:netty-bom:${rootProject.extra["nettyVersion"]}"))

    implementation("com.google.guava:guava")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClassName = "io.github.yonsx.server.App"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
