package testmod.seccult.entity.livings;

import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;

public interface IBossBase {

	public abstract BossInfo.Color getBarColor();
	
	public abstract BossInfo.Overlay getOverlay();
	
	public abstract boolean DarkenSky();
	
	public abstract SoundEvent getBGM();
}
