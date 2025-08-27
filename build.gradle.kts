plugins {
  id("java-library")
  id("groovy")
  id("jacoco")
  id("org.sonarqube") version "6.2.0.5505"
  id("maven-publish")
  id("signing")
}

dependencies {
  implementation("com.stano:java-utils:1.0.0")
  implementation("com.h2database:h2:2.3.232")
  implementation("com.microsoft.sqlserver:mssql-jdbc:12.10.1.jre11")
  implementation("commons-cli:commons-cli:1.9.0")
  implementation("commons-io:commons-io:2.19.0")
  implementation("io.opentelemetry.instrumentation:opentelemetry-jdbc:2.16.0-alpha")
  implementation("mysql:mysql-connector-java:8.0.33")
  implementation("org.apache.commons:commons-collections4:4.5.0")
  implementation("org.apache.commons:commons-dbcp2:2.13.0")
  implementation("org.apache.commons:commons-lang3:3.17.0")
  implementation("org.apache.groovy:groovy-all:4.0.27")
  implementation("org.hsqldb:hsqldb:2.7.4")
  implementation("org.jooq:joor-java-8:0.9.15")
  implementation("org.postgresql:postgresql:42.7.7")
  implementation("org.slf4j:slf4j-api:2.0.17")

  testImplementation("net.bytebuddy:byte-buddy:1.17.6")
  testImplementation("org.junit.jupiter:junit-jupiter:5.13.2")
  testImplementation("org.junit.platform:junit-platform-launcher:1.13.2")
  testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
  testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
  testImplementation("ch.qos.logback:logback-classic:1.5.18")
  testImplementation("ch.qos.logback:logback-core:1.5.18")
  testImplementation("org.slf4j:jcl-over-slf4j:2.0.17")
  testImplementation("org.slf4j:jul-to-slf4j:2.0.17")
  testImplementation("org.slf4j:log4j-over-slf4j:2.0.17")
  testImplementation("uk.org.lidalia:sysout-over-slf4j:1.0.2")
  testImplementation("net.logstash.logback:logstash-logback-encoder:8.1")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      pom {
        name.set("jdbc-utils")
        description.set("A set of utility classes to make working with JDBC easier")
        url.set("https://github.com/jstano/jdbc-utils")
        licenses {
          license {
            name.set("MIT License")
            url.set("https://opensource.org/license/mit")
          }
        }
        developers {
          developer {
            id.set("jstano")
            name.set("Jeff Stano")
            email.set("jeff@stano.com")
          }
        }
        scm {
          connection.set("scm:git:https://github.com/jstano/jdbc-utils.git")
          developerConnection.set("scm:git:ssh://git@github.com:jstano/jdbc-utils.git")
          url.set("https://github.com/jstano/jdbc-utils")
        }
      }
    }
  }
  repositories {
    maven {
      url = uri(layout.buildDirectory.dir("staging-deploy").get().toString())
    }
  }
}

signing {
//  useInMemoryPgpKeys(
//    findProperty("signing.keyId") as String?,
//    findProperty("signing.key") as String?,
//    findProperty("signing.password") as String?
//  )
  sign(publishing.publications["mavenJava"])
}

sonar {
  val sonarHost = "http://localhost:9000"
  val sonarToken = "sqa_85bebeecde7b7f43bbbcdf9e9f6d57e1f7c5fabd"

  properties {
    property("sonar.host.url", sonarHost)
    property("sonar.token", sonarToken)
    property("sonar.projectName", "jdbc-tools")
    property("sonar.projectKey", "${project.group}:jdbc-tools")
    property("sonar.projectVersion", project.version)
  }
}

tasks.register<Zip>("zipStagingDeploy") {
  archiveFileName.set("staging-deploy.zip")
  destinationDirectory.set(layout.buildDirectory.dir("tmp"))
  from("build/staging-deploy") {
    include("**/*")
  }
}

configurations {
  all {
    exclude(group = "commons-logging", module = "commons-logging")
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.compilerArgs = compilerOptions()
  sourceCompatibility = "21"
  targetCompatibility = "21"
}
tasks.withType<GroovyCompile>().configureEach {
  options.compilerArgs = compilerOptions()
  sourceCompatibility = "21"
  targetCompatibility = "21"
  groovyOptions.setParameters(true)
}

java {
  withJavadocJar()
  withSourcesJar()
}

tasks.withType<Jar> {
  exclude("**/.gitkeep")
}
tasks.withType<Javadoc>().configureEach {
  (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
}
tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens", "java.base/java.lang=ALL-UNNAMED")
  finalizedBy("jacocoTestReport")
}
tasks.withType<JacocoReport>().configureEach {
  reports {
    html.required.set(true)
    xml.required.set(true)
  }
}

fun compilerOptions(): List<String> = listOf("-Xlint:none", "-Xdoclint:none", "-nowarn", "-parameters")
