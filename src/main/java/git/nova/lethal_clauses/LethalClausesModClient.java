package git.nova.lethal_clauses;

import git.nova.lethal_clauses.client.ScreenFlashPostProcessor;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

@Mod(value = LethalClausesMod.MODID, dist = Dist.CLIENT)
public class LethalClausesModClient {

    public LethalClausesModClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            PostProcessHandler.addInstance(ScreenFlashPostProcessor.INSTANCE);
        });
        LethalClausesMod.LOGGER.info("Lethal Clauses Mod Client Loaded and ScreenFlashPostProcessor registered!");
    }
}
