package examples.simple;

import naitsirc98.beryl.core.*;
import naitsirc98.beryl.graphics.rendering.ShadingModel;
import naitsirc98.beryl.lights.DirectionalLight;
import naitsirc98.beryl.materials.Material;
import naitsirc98.beryl.materials.PhongMaterial;
import naitsirc98.beryl.meshes.StaticMesh;
import naitsirc98.beryl.meshes.views.StaticMeshView;
import naitsirc98.beryl.scenes.Entity;
import naitsirc98.beryl.scenes.Scene;
import naitsirc98.beryl.scenes.components.behaviours.UpdateMutableBehaviour;
import naitsirc98.beryl.scenes.components.math.Transform;
import naitsirc98.beryl.scenes.components.meshes.StaticMeshInstance;
import naitsirc98.beryl.scenes.environment.skybox.SkyboxFactory;
import naitsirc98.beryl.util.Color;

public class SimpleApp extends BerylApplication {

    public SimpleApp() {
        // Customize configuration before Beryl launches:
        BerylConfiguration.MSAA_SAMPLES.set(8);
        BerylConfiguration.SCENE_SHADING_MODEL.set(ShadingModel.PHONG);
        BerylConfiguration.FIRST_SCENE_NAME.set("My awesome Beryl game");
        // BerylConfigurationHelper contains useful methods to set predefined sets of configurations.
        BerylConfigurationHelper.debugConfiguration();
    }

    @Override
    protected void onStart(Scene scene) {

        // First of all, position the camera so we can see the cube.
        scene.camera().position(0, 0, 20);

        // Create the cube entity. An entity may have a name, and it must be unique.
        Entity cube = scene.newEntity("The Cube");

        // Add a Transform. It defines the position, scale and rotation of an object in the scene.
        // A Transform is necessary in visible objects, but not in invisible objects, like in game controllers for example.
        // Lets set this cube at position (0, 0, 0) and scale it by 2.
        cube.add(Transform.class).position(0, 0, 0).scale(2.0f);

        // Now attach a mesh view to it to actually see it on the scene.
        // Common meshes, like cube, sphere or quad, are already loaded and ready to use.
        StaticMesh cubeMesh = StaticMesh.cube();

        // Now we need a material. Lets create a material with a green color.
        // Materials have a unique name, and are created through a factory. This is to cached already created materials.
        Material material = PhongMaterial.getFactory().getMaterial("My Material", mat -> {
            // If the material named "My Material" has not been created, then this piece of code will be executed.
            // Here you can initialize the material. The materials are mutable, so you can change their properties at runtime.
            mat.color(Color.colorGreen()).shininess(32.0f);
        });

        // Create a StaticMeshInstance component and attach a new StaticMeshView to it.
        // A mesh view simply binds a mesh with a material. A mesh instance is a component that can have multiple mesh views.
        cube.add(StaticMeshInstance.class).meshView(new StaticMeshView(cubeMesh, material));

        // Now, lets add a small behaviour script that rotates the cube each frame.
        // UpdateMutableBehaviour is a special type of behaviour component that can change its onUpdate method at runtime.
        cube.add(UpdateMutableBehaviour.class).onUpdate(self -> {
            // This will be executed each frame. All onUpdate methods are invoked at the same time, in parallel.
            // If you are going to perform tasks that should be executed in a single thread, use onLateUpdate instead.
            // "Self" represents this behaviour component. You can get the components of its entity with the "get" method:
            Transform transform = self.get(Transform.class);
            transform.rotateY(Time.seconds());
        });

        // Now, lets add the directional light
        scene.environment().lighting().directionalLight(new DirectionalLight().direction(0, 0, 1));

        // And finally, set the skybox
        scene.environment().skybox(SkyboxFactory.newSkybox(BerylFiles.getPath("textures/skybox/day")));

        scene.renderInfo().vsync(false);
    }
}
