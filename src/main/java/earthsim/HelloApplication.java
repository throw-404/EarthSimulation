package earthsim;

import simulationModules.starss;
import simulationModules.createEarth;
import simulationModules.createMoon;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final double ROTATION_SPEED = 0.3;
    private static final double EARTH_ROTATION_SPEED = 10.0;

    private static final double CAMERA_Z = -1000;
    private static final double MOON_ORBIT_RADIUS = 310;
    private static final double MOON_ORBIT_PERIOD_SECONDS = 18.0;

    private final Affine rotation = new Affine();
    private double lastX, lastY;

    private final starss starManager = new starss();

    @Override
    public void start(Stage stage) {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(CAMERA_Z);
        camera.setNearClip(1);
        camera.setFarClip(6000);

        // Earth remains at the origin.
        Sphere earth = new createEarth().createEarth();
        earth.getTransforms().add(new Rotate(3, Rotate.Z_AXIS));
        earth.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
        earth.setRotationAxis(Rotate.Y_AXIS);

        // Create the Moon and place it away from Earth's centre.
        Sphere moon = new createMoon().createMoon();
        moon.setTranslateX(MOON_ORBIT_RADIUS);
        moon.setRotationAxis(Rotate.Y_AXIS);

        // The orbit pivot is centred on Earth. Rotating this group moves the
        // Moon around Earth without rotating Earth's own sphere.
        Group moonOrbitGroup = new Group(moon);
        Rotate moonOrbitRotation = new Rotate(0, Rotate.Y_AXIS);
        moonOrbitGroup.getTransforms().add(moonOrbitRotation);

        // Draw a subtle inclined orbital guide. The Moon and guide share the
        // same orbital plane, while the Earth stays at the centre.
        Circle orbitPath = new Circle(MOON_ORBIT_RADIUS);
        orbitPath.setFill(Color.TRANSPARENT);
        orbitPath.setStroke(Color.color(0.55, 0.68, 0.88, 0.24));
        orbitPath.setStrokeWidth(1.2);
        orbitPath.setMouseTransparent(true);
        orbitPath.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        Group orbitalPlane = new Group(orbitPath, moonOrbitGroup);
        orbitalPlane.getTransforms().add(new Rotate(16, Rotate.Z_AXIS));

        // Lighting: the Sun model is removed, but the planets still need
        // illumination. These off-screen lights act as soft studio lighting.
        // Keep them inside earthGroup so they follow the system when it is
        // rotated, panned, or zoomed with the mouse.
        PointLight keyLight = new PointLight(Color.rgb(255, 245, 225));
        keyLight.setTranslateX(-300);
        keyLight.setTranslateY(-220);
        keyLight.setTranslateZ(-430); // camera-facing side of Earth and Moon

        PointLight coolFillLight = new PointLight(Color.rgb(115, 155, 225));
        coolFillLight.setTranslateX(360);
        coolFillLight.setTranslateY(120);
        coolFillLight.setTranslateZ(-260);

        AmbientLight ambient = new AmbientLight(Color.rgb(72, 76, 88));

        // Keep Earth, Moon, orbit and lights together for mouse controls.
        Group earthGroup = new Group(
                earth,
                orbitalPlane,
                keyLight,
                coolFillLight,
                ambient
        );
        earthGroup.getTransforms().add(rotation);

        Group starfield = starManager.createStarfield(4800);

        Group root = new Group(
                starfield,
                earthGroup
        );

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);

        initMouseControl(earthGroup, scene);

        stage.setTitle("Earth Simulation");
        stage.setScene(scene);
        stage.show();

        startAnimation(earth, moon, moonOrbitRotation);
    }

    private void startAnimation(
            Sphere earth,
            Sphere moon,
            Rotate moonOrbitRotation
    ) {
        new AnimationTimer() {

            private long previousTime = 0;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;
                    return;
                }

                double dt = (now - previousTime) / 1_000_000_000.0;
                previousTime = now;
                dt = Math.min(dt, 0.05);

                double time = now / 1_000_000_000.0;

                // Earth's axial rotation.
                earth.setRotate(
                        earth.getRotate() + EARTH_ROTATION_SPEED * dt
                );

                double orbitAngle = moonOrbitRotation.getAngle()
                        + 360.0 * dt / MOON_ORBIT_PERIOD_SECONDS;
                moonOrbitRotation.setAngle(orbitAngle);

                moon.setRotate(orbitAngle);

                for (starss.Twinkle twinkle : starManager.twinkles) {
                    twinkle.update(time);
                }
            }
        }.start();
    }

    private void initMouseControl(Group earthGroup, Scene scene) {

        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            lastX = e.getSceneX();
            lastY = e.getSceneY();

            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                rotation.setToIdentity();
                earthGroup.setTranslateX(0);
                earthGroup.setTranslateY(0);
                earthGroup.setTranslateZ(0);
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            double dx = e.getSceneX() - lastX;
            double dy = e.getSceneY() - lastY;

            lastX = e.getSceneX();
            lastY = e.getSceneY();

            boolean panning = e.getButton() == MouseButton.SECONDARY
                    || e.getButton() == MouseButton.MIDDLE
                    || (e.getButton() == MouseButton.PRIMARY && e.isShiftDown());

            if (panning) {
                double distance = Math.abs(CAMERA_Z) + earthGroup.getTranslateZ();
                double unitsPerPixel = distance * 2
                        * Math.tan(Math.toRadians(15)) / HEIGHT;

                earthGroup.setTranslateX(clamp(
                        earthGroup.getTranslateX() + dx * unitsPerPixel,
                        -1200, 1200
                ));

                earthGroup.setTranslateY(clamp(
                        earthGroup.getTranslateY() + dy * unitsPerPixel,
                        -700, 700
                ));
            } else if (e.getButton() == MouseButton.PRIMARY) {
                rotation.prependRotation(
                        -dx * ROTATION_SPEED,
                        Point3D.ZERO,
                        Rotate.Y_AXIS
                );
                rotation.prependRotation(
                        dy * ROTATION_SPEED,
                        Point3D.ZERO,
                        Rotate.X_AXIS
                );
            }
        });

        scene.addEventHandler(ScrollEvent.SCROLL, e -> {
            double newZ = earthGroup.getTranslateZ() + e.getDeltaY();
            earthGroup.setTranslateZ(clamp(newZ, -650, 500));
            e.consume();
        });
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
