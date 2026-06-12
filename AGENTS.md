# AGENTS.md

## Build & Test

```bash
./gradlew build          # compile, test, jacoco coverage
./gradlew test           # tests only
./gradlew publishToMavenLocal   # install to ~/.m2 for local consumers
```

## Language conventions

- Production source: Java 21 (`src/main/java`)
- Tests: Spock/Groovy 4 (`src/test/groovy`) — do not write JUnit tests in Groovy or Spock tests in Java

## Local dependency

`com.stano:java-utils:1.0.0` resolves from `mavenLocal()` (`~/.m2`). If the build fails with a missing artifact, run `./gradlew publishToMavenLocal` in the `java-utils` project first.

## Publishing

Staging artifacts land in `build/staging-deploy/`. Zip them with:

```bash
./gradlew zipStagingDeploy
```
