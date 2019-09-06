package testmod.seccult.magick.implementation;

import java.util.List;

import net.minecraft.entity.Entity;
import testmod.seccult.entity.EntityIMProjectile;

public class ImplementationProjectile extends ImplementationStoreable{
	
	public ImplementationProjectile(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
		if(this.doEntity && getEntity() != null) 
		{
			List<Entity> eList = getEntity();
			for(int i = 0; i < eList.size(); i++)
			{
				if(eList.get(i) != null) {
					Entity IMentity = eList.get(i);
					EntityIMProjectile projec = new EntityIMProjectile(player.world);
					projec.setEntityIMProjectile(player, IMentity);
					projec.shoot(IMentity.getLookVec().x * 0.1, IMentity.getLookVec().y * 0.1, IMentity.getLookVec().z * 0.1, base, addtion);
					projec.setData(LoadMagick, LoadSelect, this.doEntity, this.doBlock);
					projec.setColor(this.color[0], this.color[1], this.color[2]);
					projec.setScale(scale);
					player.world.spawnEntity(projec);
				}
			}
			setEntity(null);
		}
		else if(this.doBlock && getBlock() != null)
		{
			setBlock(getBlock());
		}
	}
}
