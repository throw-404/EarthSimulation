# 🌍 EarthSimulation

> Exploring space through JavaFX 3D, one celestial body at a time.

**EarthSimulation** is a Java-based 3D space simulation built using JavaFX. The project focuses on rendering celestial objects, simulating motion, and experimenting with 3D graphics, lighting, and animation.

🚧 **Status: Work in Proghow toress**

## ✨ Current Features

- 🌍 **3D Earth:** Textured Earth using high-resolution day and specular maps.
- 🔄 **Earth Rotation:** Continuous rotation with interactive mouse controls.
- ⭐ **Dynamic Starfield:** Thousands of stars with randomized colours and twinkling animations.
- ☄️ **Random Comets:** Comets spawn at random intervals of 5–12 seconds.
- 🌌 **Comet Flyby:** Comets travel behind Earth, with their glow and lighting gradually fading after emerging.
- 🎥 **3D Camera:** Zoom, pan, and rotate controls for exploring the scene.

## 🛠️ Tech Stack

| Technology | Purpose                         |
| ---------- | ------------------------------- |
| Java 21    | Core programming language       |
| JavaFX 3D  | 3D rendering and visualization  |

## 🎮 Controls

| Input                     | Action        |
| ------------------------- | ------------- |
| Left Mouse Drag           | Rotate Earth  |
| Right / Middle Mouse Drag | Pan the scene |
| Shift + Left Drag         | Pan the scene |
| Mouse Scroll              | Zoom in / out |
| Double-click              | Reset view    |

## 🗺️ Roadmap

The simulation is actively evolving. More celestial objects and visual improvements are planned.

- [ ] ☀️ **Sun:** Add a visible 3D Sun with realistic lighting and glow.
- [ ] 🌙 **Moon:** Introduce a textured Moon with orbital movement.
- [ ] 🌗 **Lunar Phases:** Simulate the Moon's changing phases.
- [ ] 🌌 **Visual Enhancements:** Improve lighting, glow, textures, and atmospheric effects.

## 📁 Project Structure

```text
EarthSim/
├── src/
│   └── main/
│       ├── java/
│       │   ├── creationOfScenario/
│       │   ├── org/earth/sim/earthsim/
│       │   └── simulationModules/
│       └── resources/
│           ├── 8k_earth_daymap.jpg
│           └── 8k_earth_specular_map.png
├── pom.xml
└── README.md
```

## 🎯 Project Goal

The goal is to build an interactive 3D space environment while learning and experimenting with:

- JavaFX 3D graphics
- 3D transformations and camera manipulation
- Animation and motion simulation
- Lighting and material properties
- Modular Java application design

---

**Built with Java & JavaFX.**

*The universe is still under construction.* 🚀
