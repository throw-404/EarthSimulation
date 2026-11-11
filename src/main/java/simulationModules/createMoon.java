package simulationModules;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.net.URL;

public class createMoon {

    private static final String ALBEDO_TEXTURE = "/moon_8k_albedo.jpg";
    private static final String NORMAL_TEXTURE = "/moon_8k_normal.jpg";
    private static final double MOON_RADIUS = 40;

    public Sphere createMoon() {
        Sphere moon = new Sphere(MOON_RADIUS, 128);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(loadTexture(ALBEDO_TEXTURE));
        material.setBumpMap(loadTexture(NORMAL_TEXTURE));
        material.setSpecularColor(Color.rgb(35, 35, 35));
        material.setSpecularPower(12);

        moon.setMaterial(material);
        return moon;
    }

    private Image loadTexture(String path) {
        URL resource = getClass().getResource(path);
        if (resource == null) {
            throw new IllegalStateException("Moon texture not found: " + path);
        }

        Image image = new Image(resource.toExternalForm(), false);
        if (image.isError()) {
            throw new IllegalStateException(
                    "Failed to load Moon texture: " + path,
                    image.getException()
            );
        }
        return image;
    }
}
