package alphamode.core.nebula.dimensions.json;

import alphamode.core.nebula.api.NebulaID;
import alphamode.core.nebula.dimensions.Planet;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class PlanetLoader implements SimpleSynchronousResourceReloadListener {


    @Override
    public Identifier getFabricId() {
        return new NebulaID("planets");
    }

    @Override
    public void reload(ResourceManager manager) {

    }
}
