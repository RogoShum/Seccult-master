package testmod.seccult.client.FX;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModFX {
    @SubscribeEvent
    public static void registerSprite(TextureStitchEvent.Pre event){
        LightFX.test2 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/light"));
        StarFX.test2 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/star"));
        PentagonFX.test2 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/star2"));
        ATFX.test3 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/ATfield"));
        RainbowFX.test3 = event.getMap().registerSprite(new ResourceLocation("seccult:particle/Rainbow"));
    }
}
