package git.nova.lethal_clauses.client;

import git.nova.lethal_clauses.LethalClausesMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import team.lodestar.lodestone.systems.easing.Easing;

@EventBusSubscriber(modid = LethalClausesMod.MODID, value = Dist.CLIENT)
public class ClientScreenFlash {
    private static int maxTicks = 0;
    private static int ticksLeft = 0;
    private static int rgb = 0xFF005E;
    private static float maxAlpha = 0f;

    public static void trigger(int durationTicks, int colorRgb, float alpha) {
        maxTicks = durationTicks;
        ticksLeft = durationTicks;
        rgb = colorRgb & 0xFF005E;
        maxAlpha = alpha;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (ticksLeft > 0) {
            ticksLeft--;
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (ticksLeft <= 0 || maxTicks <= 0) return;

        float progress = 1f - ((float) ticksLeft / maxTicks);
        float alpha = maxAlpha * (1f - Easing.CUBIC_OUT.ease(progress, 0f, 1f, 1f));
        int alphaInt = Math.min(255, Math.max(0, (int) (alpha * 255f)));

        if (alphaInt > 0) {
            int argb = (alphaInt << 24) | rgb;
            int width = event.getGuiGraphics().guiWidth();
            int height = event.getGuiGraphics().guiHeight();
            event.getGuiGraphics().fill(0, 0, width, height, argb);
        }
    }
}