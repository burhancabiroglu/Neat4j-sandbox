# Neat4j Sandbox

`neat4j-sandbox` is an experimental LibGDX environment for testing the `Neat4j` neuroevolution library in a game-like setting. The project was inspired by the spirit of OpenAI Gym: keep the learning algorithm separate from the environment, then use the environment to observe how agents behave under a clear fitness signal.

The first environment is a simple Mario-style side-scroller. A population of neural networks is evaluated in the same map, and each network controls when the character should jump.

## Relationship to Neat4j

The sandbox consumes the published `Neat4j` package from GitHub Packages:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/burhancabiroglu/Neat4j")
        credentials {
            username = findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = findProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

implementation "com.cabir:neat4j:1.1.0"
```

This keeps the simulation code independent from the library and makes the sandbox behave like a real downstream consumer of the package.

## Environment

The current environment uses:

- LibGDX for rendering and application lifecycle;
- Box2D for physics and collisions;
- a tiled map for level geometry;
- `Neat4j` for neural-network population search.

The agent receives a simple distance signal from a raycast in front of the player. The network produces two output values, and the sandbox interprets their relative values as a jump decision.

The fitness function rewards forward progress and successful obstacle interaction:

```text
fitness = horizontal_progress / 10 + jump_score
```

This is deliberately simple. It is useful for observing early neuroevolution behavior, but it is not intended as a formal benchmark.

## Current Learning Model

The sandbox uses `Neat4j` to evolve weights and biases for a fixed neural-network skeleton:

```java
NetworkSkeleton skeleton = new NetworkSkeleton();
skeleton.add(new LayerSkeleton(1, 3));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(3, 8));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(8, 4));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(4, 3));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(3, 2));

NEAT neatPlayer = new NEAT(skeleton, 10, 0.1, 100);
```

The current library does not yet implement structural NEAT features such as innovation numbers, speciation, or topology-changing mutation.

## Demo

<div align="center">
  <img src="assetmd/img.gif" width="80%" alt="Neat4j sandbox gameplay preview">
</div>

## Running Locally

The desktop app uses the LibGDX LWJGL3 backend. On macOS, LWJGL3 must start on the first JVM thread; the Gradle `run` task handles this automatically.

GitHub Packages requires credentials when Gradle resolves the `Neat4j` dependency. Add them to `~/.gradle/gradle.properties`:

```properties
gpr.user=your-github-username
gpr.token=your-github-token
```

```bash
JAVA_HOME=/opt/homebrew/Cellar/openjdk@21/21.0.11/libexec/openjdk.jdk/Contents/Home ./gradlew run
```

You can also run the Gradle task from IntelliJ IDEA:

```text
Tasks > application > run
```

If you create a manual IntelliJ Application configuration, use:

```text
Main class: com.cabir.neat4j.sandbox.Main
VM options: -XstartOnFirstThread
```

The Gradle task is usually safer because it sets up the classpath and native dependencies for LibGDX.

## Build and Test

```bash
./gradlew test
```

The test task compiles the sandbox against the published `Neat4j` package.

## Project Structure

```text
src/main/java/com/cabir/neat4j/sandbox
└── Main.java                 # Desktop entry point

game/src/main/java/com/cabir/neat4j/sandbox
├── core                      # Game screen, player, physics objects
└── desktop                   # Desktop launcher

game/src/main/resources       # Maps, tiles, textures, texture atlas
```

## Roadmap

Useful next steps:

- define a small environment interface similar to `reset`, `step`, `observation`, and `reward`;
- separate rendering from simulation so experiments can run headlessly;
- add reproducible seeds for fair comparisons;
- expose training metrics per generation;
- add more environments beyond the Mario-style map;
- connect the sandbox to a future structural NEAT implementation.

## License

This project is licensed under the terms included in `LICENSE`.
