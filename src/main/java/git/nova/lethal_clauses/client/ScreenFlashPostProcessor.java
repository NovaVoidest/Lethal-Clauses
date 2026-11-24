package git.nova.lethal_clauses.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class ScreenFlashPostProcessor extends PostProcessor {
    public static final ScreenFlashPostProcessor INSTANCE = new ScreenFlashPostProcessor();

    private static final ResourceLocation SHADER_LOCATION =
            ResourceLocation.fromNamespaceAndPath("lethal_clauses", "flash");

    private float intensity = 0.0f;
    private float decaySpeed = 2.5f;
    private final Vector3f flashColor = new Vector3f(0.980f, 0.961f, 0.824f);

    static {

        INSTANCE.setActive(false);
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return SHADER_LOCATION;
    }

    public void triggerFlash(float strength, float durationSeconds, float r, float g, float b) {
        this.intensity = strength;
        this.decaySpeed = 1.0f / Math.max(durationSeconds, 0.05f);
        this.flashColor.set(r, g, b);
        this.setActive(true);
    }


    public void triggerFlash(float strength, float durationSeconds, int hexColor) {
        float r = ((hexColor >> 16) & 0xFF) / 255.0f;
        float g = ((hexColor >> 8) & 0xFF) / 255.0f;
        float b = (hexColor & 0xFF) / 255.0f;
        triggerFlash(strength, durationSeconds, r, g, b);
    }

    @Override
    public void beforeProcess(Matrix4f viewModelMatrix) {

        float deltaSeconds = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks() / 20.0f;
        this.intensity -= this.intensity * this.decaySpeed * deltaSeconds * 3.0f;

        if (this.intensity <= 0.005f) {
            this.intensity = 0.0f;
            this.setActive(false);
            return;
        }


        if (this.effects != null) {
            for (EffectInstance effect : this.effects) {
                var uniformIntensity = effect.getUniform("Intensity");
                if (uniformIntensity != null) {
                    uniformIntensity.set(this.intensity);
                }
                var uniformColor = effect.getUniform("FlashColor");
                if (uniformColor != null) {
                    uniformColor.set(this.flashColor.x, this.flashColor.y, this.flashColor.z);
                }
            }
        }
    }

    @Override
    public void afterProcess() {

    }
}
