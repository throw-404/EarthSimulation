package simulationModules;

import creationOfScenario.createSphere;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.net.URL;

public class createEarth {

    private final createSphere earthFactory = new createSphere();

    public Sphere createEarth() {

        Sphere earth = earthFactory.createSphere(150);

        PhongMaterial material = new PhongMaterial();

        material.setDiffuseMap(
                loadTexture("/8k_earth_daymap.jpg")
        );

        material.setSpecularMap(
                loadTexture("/8k_earth_specular_map.png")
        );

        material.setSpecularColor(Color.rgb(150, 150, 150));
        material.setSpecularPower(64);

        earth.setMaterial(material);

        return earth;
    }

    private Image loadTexture(String path) {

        URL resource = getClass().getResource(path);

        if (resource == null) {
            throw new IllegalStateException(
                    "Texture not found: " + path
            );
        }

        Image image = new Image(resource.toExternalForm(), false);

        if (image.isError()) {
            throw new IllegalStateException(
                    "Failed to load texture: " + path,
                    image.getException()
            );
        }

        return image;
    }
}