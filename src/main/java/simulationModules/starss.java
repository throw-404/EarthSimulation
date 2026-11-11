package simulationModules;

import javafx.scene.Group;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class starss {

    public final List<Twinkle> twinkles = new ArrayList<>();
    private static final double CAMERA_Z = -1000;

    public static class Twinkle {
        final WritableImage pixel = new WritableImage(1, 1);
        final Color base;
        final double speed;
        final double phase;
        final double depth;

        Twinkle(Color base, double speed, double phase, double depth) {
            this.base = base;
            this.speed = speed;
            this.phase = phase;
            this.depth = depth;
            update(0);
        }

        public void update(double time) {
            double wave = 0.5 + 0.5 * Math.sin(time * speed + phase);
            double brightness = 0.72 + (1.0 - depth * 0.25) * wave;

            pixel.getPixelWriter().setColor(0, 0, Color.color(
                    Math.min(1.0, base.getRed() * brightness),
                    Math.min(1.0, base.getGreen() * brightness),
                    Math.min(1.0, base.getBlue() * brightness)
            ));
        }
    }

    public Group createStarfield(int count) {
        Random random = new Random(108);

        Color[] tints = {
                Color.rgb(255, 255, 255),
                Color.rgb(220, 232, 255),
                Color.rgb(170, 202, 255),
                Color.rgb(255, 239, 215),
                Color.rgb(255, 218, 190),
                Color.rgb(205, 220, 255),
                Color.rgb(255, 245, 230)
        };

        // Reuse a set of animated materials instead of creating one per star.
        PhongMaterial[] materials = new PhongMaterial[96];
        for (int i = 0; i < materials.length; i++) {
            Twinkle twinkle = new Twinkle(
                    tints[random.nextInt(tints.length)],
                    0.35 + random.nextDouble() * 2.2,
                    random.nextDouble() * Math.PI * 2,
                    0.12 + random.nextDouble() * 0.55
            );
            twinkles.add(twinkle);

            PhongMaterial material = new PhongMaterial(Color.BLACK);
            material.setSelfIlluminationMap(twinkle.pixel);
            materials[i] = material;
        }

        final double radius = 4000;
        Group stars = new Group();

        for (int i = 0; i < count; i++) {
            double azimuth = Math.toRadians((random.nextDouble() * 2 - 1) * 52);

            // Some stars form a soft diagonal density band, adding depth without
            // making the background look like a uniform point grid.
            double elevation;
            if (random.nextDouble() < 0.38) {
                double bandCentre = Math.toRadians(5.5)
                        * Math.sin(azimuth * 1.45);
                elevation = bandCentre + Math.toRadians(random.nextGaussian() * 8.0);
            } else {
                elevation = Math.toRadians((random.nextDouble() * 2 - 1) * 30);
            }
            elevation = Math.max(Math.toRadians(-34),
                    Math.min(Math.toRadians(34), elevation));

            double size = 0.65 + Math.pow(random.nextDouble(), 4.2) * 3.3;
            boolean brightStar = random.nextDouble() < 0.035;
            if (brightStar) {
                size += 1.8 + random.nextDouble() * 2.0;
            }

            double x = radius * Math.cos(elevation) * Math.sin(azimuth);
            double y = radius * Math.sin(elevation);
            double z = CAMERA_Z + radius * Math.cos(elevation) * Math.cos(azimuth);

            // A faint translucent halo makes only the brighter stars stand out.
            if (brightStar) {
                Color tint = tints[random.nextInt(tints.length)];
                Sphere halo = new Sphere(size * 2.7, 8);
                PhongMaterial haloMaterial = new PhongMaterial(
                        Color.color(tint.getRed(), tint.getGreen(), tint.getBlue(), 0.10)
                );
                haloMaterial.setSpecularColor(Color.TRANSPARENT);
                halo.setMaterial(haloMaterial);
                halo.setTranslateX(x);
                halo.setTranslateY(y);
                halo.setTranslateZ(z);
                stars.getChildren().add(halo);
            }

            Sphere star = new Sphere(size, brightStar ? 8 : 6);
            star.setMaterial(materials[random.nextInt(materials.length)]);
            star.setTranslateX(x);
            star.setTranslateY(y);
            star.setTranslateZ(z);
            stars.getChildren().add(star);
        }

        return stars;
    }
}
