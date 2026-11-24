package git.nova.lethal_clauses.common.item;

import git.nova.lethal_clauses.client.ScreenFlashPostProcessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ScreenFlashDebugItem extends Item {
    public ScreenFlashDebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            // Parameters: strength, durationSeconds, hexColor
            ScreenFlashPostProcessor.INSTANCE.triggerFlash(1.2f, 0.8f, 0xFAF5D2);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
