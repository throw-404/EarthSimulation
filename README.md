# 🌍 EarthSimulation

> Exploring space through JavaFX 3D, one celestial body at a time.

**EarthSimulation** is an interactive 3D space simulation built using Java and JavaFX. It features a textured Earth, an orbiting Moon, a dynamic starfield, and interactive camera controls, bringing a small piece of the solar system to life.

**Status: Completed**

## ✨ Features

- 🌍 **3D Earth:** Textured Earth rendered using high-resolution day and specular maps.
- 🔄 **Earth Rotation:** Continuous axial rotation with interactive 3D manipulation.
- 🌙 **3D Moon:** Textured Moon with detailed surface features.
- 🛰️ **Lunar Orbit:** Moon continuously revolves around Earth with synchronized rotation.
- ⭐ **Dynamic Starfield:** Thousands of stars featuring randomized colours, varying brightness, and twinkling animations.
- 💡 **3D Lighting:** Multi-source lighting with warm key illumination, cool fill lighting, and ambient light.
- 🎥 **Interactive Camera:** Zoom, pan, and rotate controls for exploring the 3D environment.
- 🌌 **Orbital Visualization:** Inclined orbital path for visualizing the Moon's revolution around Earth.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| JavaFX 21 | 3D rendering, graphics, and animation |
| JavaFX 3D API | Spheres, materials, lighting, and transformations |
| Maven | Dependency and build management |

## 🎮 Controls

| Input | Action |
|---|---|
| Left Mouse Drag | Rotate the scene |
| Right / Middle Mouse Drag | Pan the scene |
| Shift + Left Drag | Pan the scene |
| Mouse Scroll | Zoom in / out |
| Double-click | Reset view |

## 📁 Project Structure

```text
EarthSimulation/
│
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── creationOfScenario/
│       │   │   └── createSphere.java
│       │   │
│       │   ├── earthsim/
│       │   │   ├── HelloApplication.java
│       │   │   └── Launcher.java
│       │   │
│       │   ├── simulationModules/
│       │   │   ├── createEarth.java
│       │   │   ├── createMoon.java
│       │   │   └── starss.java
│       │   │
│       │   └── module-info.java
│       │
│       └── resources/
│           ├── 8k_earth_daymap.jpg
│           ├── 8k_earth_specular_map.png
│           ├── 8k_sun_procedural.jpg
│           ├── moon_8k_albedo.jpg
│           └── moon_8k_normal.jpg
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 🚀 How to Run

### Prerequisites

Make sure you have the following installed:

- **JDK 17** or compatible newer JDK.
- **IntelliJ IDEA** (recommended).
- Maven (optional, since the project includes Maven Wrapper).

Verify your Java installation:

```bash
java -version
```

### Option 1: Run using IntelliJ IDEA

1. Clone or download this repository.
2. Open the `EarthSimulation` project in IntelliJ IDEA.
3. Allow IntelliJ to load and synchronize Maven dependencies from `pom.xml`.
4. Navigate to:

   `src/main/java/earthsim/Launcher.java`

5. Right-click `Launcher.java` and select **Run 'Launcher.main()'**.

The JavaFX simulation should launch.

### Option 2: Run using Maven Wrapper

Open a terminal in the project root directory.

**Windows:**

```bash
.\mvnw.cmd clean javafx:run
```

**Linux / macOS:**

```bash
./mvnw clean javafx:run
```

*Note: The Maven commands assume that the JavaFX Maven plugin is configured in `pom.xml`.*

## 🎯 Project Objectives

This project was developed to explore and implement:

- JavaFX 3D graphics and rendering.
- 3D transformations and coordinate systems.
- Orbital mechanics and rotational animation.
- Lighting, materials, and texture mapping.
- Interactive camera manipulation.
- Modular Java application development.

---

**Built with Java & JavaFX.**

*The universe is yours to explore.* 🚀
