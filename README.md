<img src="logo/logo.svg" width="92px"/>

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/portlek/configs/workflows/build/badge.svg)
[![Release](https://jitpack.io/v/portlek/configs.svg)](https://jitpack.io/#portlek/configs)

## How to Use

### Maven

```xml

<builds>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.2.4</version>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <configuration>
            <minimizeJar>true</minimizeJar>
            <createDependencyReducedPom>false</createDependencyReducedPom>
            <!-- Relocations(Optional)
            <relocations>
              <relocation>
                <pattern>io.github.portlek.configs</pattern>
                <shadedPattern>[YOUR_PLUGIN_PACKAGE].shade</shadedPattern>
              </relocation>
            </relocations>
            -->
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</builds>
```

```xml

<repositories>
  <repository>
    <id>jitpack</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>
```

```xml

<dependencies>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-bukkit-transformers</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-bukkit-jackson-json</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-bukkit-jackson-yaml</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-bukkit-snakeyaml</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-jackson-json</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-jackson-yaml</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-hjson-json</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-simple-json</artifactId>
    <version>${version}</version>
  </dependency>
  <dependency>
    <groupId>com.github.portlek.configs</groupId>
    <artifactId>configs-hocon-lightbend</artifactId>
    <version>${version}</version>
  </dependency>
</dependencies>
```

### Gradle

```groovy
plugins {
    id "com.github.johnrengelman.shadow" version "7.0.0"
}
```

```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

```groovy
dependencies {
}
```

## Supporters

[![Jetbrains](logo/jetbrains.svg)](https://www.jetbrains.com/?from=configs)
