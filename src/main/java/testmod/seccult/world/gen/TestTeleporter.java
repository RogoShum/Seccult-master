package testmod.seccult.world.gen;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern.PatternHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import testmod.seccult.blocks.BlockPortal;
import testmod.seccult.init.ModBlocks;

public class TestTeleporter extends Teleporter {
	
	public TestTeleporter(WorldServer world) {
		super(world);
	}
	
	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		if(this.world.provider.getDimensionType().getId() != 1) {
			if(!this.placeInExistingPortal(entity, rotationYaw)) {
				this.makePortal(entity);
				this.placeInExistingPortal(entity, rotationYaw);
			}
		} else {
			int i = MathHelper.floor(entity.posX),
				j = MathHelper.floor(entity.posY) - 1,
				k = MathHelper.floor(entity.posZ),
				l = 1,
				i1 = 0;
			
			for(int j1 = -2; j1 <= 2; ++j1) {
				for(int k1 = -2; k1 <= 2; ++k1) {
					for(int l1 = -1; l1 <= 3; ++l1) {
						int i2 = i + k1,
							j2 = j + l1,
							k2 = k - j1;
						boolean flag = l1 < 0;
						this.world.setBlockState(new BlockPos(i2, j2, k2), flag ? ModBlocks.ORE_OVERWORLD.getDefaultState() : Blocks.AIR.getDefaultState());
					}
				}
			}
			
			entity.setLocationAndAngles((double)i, (double)j, (double)k, entity.rotationYaw, 0.0F);
			entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		}
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
		int i = 128,
			j = MathHelper.floor(entity.posX),
			k = MathHelper.floor(entity.posZ);
		double d0 = -1.0D;
		boolean flag = true;
		BlockPos pos = BlockPos.ORIGIN;
		long l = ChunkPos.asLong(j, k);
		
		if(this.destinationCoordinateCache.containsKey(l)) {
			Teleporter.PortalPosition position = (Teleporter.PortalPosition)this.destinationCoordinateCache.get(l);
			d0 = 0.0D;
			pos = position;
			position.lastUpdateTime = this.world.getTotalWorldTime();
			flag = false;
		} else {
			BlockPos pos3 = new BlockPos(entity);
			
			for(int i1 = -128; i1 <= 128; ++i1) {
				
				BlockPos pos2;
				
				for(int j1 = -128; j1 <= 128; ++j1) {
					
					for(BlockPos pos1 = pos3.add(i1, this.world.getActualHeight() - 1 - pos3.getY(), j1); pos1.getY() >= 0; pos1 = pos2) {
						pos2 = pos1.down();
						
						if(this.world.getBlockState(pos1).getBlock() == ModBlocks.PORTAL) {
							for(pos2 = pos1.down(); this.world.getBlockState(pos2).getBlock() == ModBlocks.PORTAL; pos2 = pos2.down()) pos1 = pos2;
							
							double d1 = pos1.distanceSq(pos3);
							
							if(d0 < 0.0D || d1 < d0) {
								d0 = d1;
								pos = pos1;
							}
						}
					}
				}
			}
		}
		
		if(d0 >= 0.0D) {
			if(flag) this.destinationCoordinateCache.put(l, new Teleporter.PortalPosition(pos, this.world.getTotalWorldTime()));
			
			double d5 = (double) pos.getX() + 0.5D,
				   d7 = (double) pos.getZ() + 0.5D;
			PatternHelper helper = ModBlocks.PORTAL.createPatternHelper(world, pos);
			boolean flag1 = helper.getForwards().rotateY().getAxisDirection() == AxisDirection.NEGATIVE;
			double d2 = helper.getForwards().getAxis() == Axis.X ? (double) helper.getFrontTopLeft().getZ() : (double) helper.getFrontTopLeft().getX(),
				   d6 = (double) (helper.getFrontTopLeft().getY() + 1) - entity.getLastPortalVec().y * (double) helper.getHeight();
			
			if(flag1) ++d2;
			
			if(helper.getForwards().getAxis() == Axis.X) d7 = d2 + (1.0D - entity.getLastPortalVec().x) * (double) helper.getWidth() * (double) helper.getForwards().rotateY().getAxisDirection().getOffset();
			else d5 = d2 + (1.0D - entity.getLastPortalVec().x) * (double) helper.getWidth() * (double) helper.getForwards().rotateY().getAxisDirection().getOffset();
			
			float f = 0.0F, f1 = 0.0F, f2 = 0.0F, f3 = 0.0F;
			
			if(helper.getForwards().getOpposite() == entity.getTeleportDirection()) f = f1 = 1.0F;
			else if(helper.getForwards().getOpposite() == entity.getTeleportDirection().getOpposite()) f = f1 = -1.0F;
			else if(helper.getForwards().getOpposite() == entity.getTeleportDirection().rotateY()) {f2 = 1.0F; f3 = -1.0F;}
			else {f2 = -1.0F; f3 = 1.0F;}
			
			double d3 = entity.motionX,
				   d4 = entity.motionZ;
			entity.motionX = d3 *(double)f + d4 * (double)f3;
			entity.motionX = d3 *(double)f2 + d4 * (double)f1;
			entity.rotationYaw = rotationYaw - (float)(entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float)(helper.getForwards().getHorizontalIndex() * 90);
			
			if(entity instanceof EntityPlayerMP) ((EntityPlayerMP) entity).connection.setPlayerLocation(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
			else entity.setLocationAndAngles(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entity) {
		int i = 16;
		double d0 = -1.0D;
		int j = MathHelper.floor(entity.posX),
			k = MathHelper.floor(entity.posY),
			l = MathHelper.floor(entity.posZ),
			i1 = j,
			j1 = k,
			k1 = l,
			l1 = 0,
			i2 = this.random.nextInt(4);
		MutableBlockPos pos = new MutableBlockPos();
		
		for(int j2 = j - 16; j2 <= j + 16; ++j2) {
			double d1 = (double) j2 +0.5D - entity.posX;
			
			for(int l2 = l - 16; l2 <= l + 16; ++l2) {
				double d2 = (double) l2 + 0.5D - entity.posZ;
				label293:
				
				for(int j3 = this.world.getActualHeight() - 1; j3 >= 0; --j3) {
					if(this.world.isAirBlock(pos.setPos(j2, j3, l2))) {
						while(j3 > 0 && this.world.isAirBlock(pos.setPos(j2, j3 - 1, l2))) --j3;
						
						for(int k3 = i2; k3 < i2 + 4; ++k3) {
							int l3 = k3 % 2;
							int i4 = 1 - l3;
							
							if(k3 % 4 >= 2) {
								l3 = -l3;
								i4 = -i4;
							}
							
							for(int j4 = 0; j4 < 3; ++j4) {
								for(int k4 = 0; k4 < 3; ++k4) {
									for(int l4 = -1; l4 < 4; ++l4) {
										int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
										int j5 = j3 + l4;
										int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
										pos.setPos(i5, j5, k5);
										
										if(l4 < 0 && !this.world.getBlockState(pos).getMaterial().isSolid() || l4 >= 0 && !this.world.isAirBlock(pos)) continue label293;
									}
								}
							}
							
							double d5 = (double)j3 + 0.5D - entity.posY;
							double d7 = d1 * d1 + d5 * d5 + d2 * d2;
							
							if(d0 < 0.0D || d7 < d0) {
								d0 = d7;
								i1 = j2;
								j1 = j3;
								k1 = l2;
								l1 = k3 % 4;
							}
						}
					}
				}
			}
		}
		
		if(d0 < 0.0D) {
			for(int l5 = j - 16; l5 <= j + 16; ++l5) {
				double d3 = (double) l5 + 0.5D - entity.posX;

				for(int j6 = l - 16; j6 <= l + 16; ++j6) {
					double d4 = (double)j6 + 0.5D - entity.posZ;
					label231:

						for(int i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7) {
							if(this.world.isAirBlock(pos.setPos(l5, i7, j6))) {
								while(i7 > 0 && this.world.isAirBlock(pos.setPos(l5, i7 - 1, j6))) --i7;

								for(int k7 = i2; k7 < i2 + 2; ++k7) {
									int j8 = k7 % 2;
									int j9 = 1 - j8;

									for(int j10 = 0; j10 < 4; ++j10) {
										for(int j11 = -1; j11 < 4; ++j11) {
											int j12 = l5 + (j10 - 1) * j8;
											int i13 = i7 + j11;
											int j13 = j6 + (j10 - 1) * j9;
											pos.setPos(j12, i13, j13);

											if(j11 < 0 && !this.world.getBlockState(pos).getMaterial().isSolid() || j11 >= 0 && !this.world.isAirBlock(pos)) continue label231;
										}
									}

									double d6 = (double) i7 + 0.5D - entity.posY;
									double d8 = d3 * d3 + d6 * d6 + d4 * d4;

									if(d0 < 0.0D || d8 < d0) {
										d0 = d8;
										i1 = l5;
										j1 = i7;
										k1 = j6;
										l1 = k7 % 2;
									}
								}
							}
						}
				}
			}
		}

		int i6 = i1,
				k2 = j1,
				k6 = k1,
				l6 = l1 % 2,
				i3 = 1 - l6;

		if(l1 % 4 >= 2) {
			l6 = -l6;
			i3 = -i3;
		}

		if(d0 < 0.0D) {
			j1 = MathHelper.clamp(j1, 70, this.world.getActualHeight() - 10);
			k2 = j1;

			for(int j7 = -1; j7 <= 1; ++j7) {
				for(int l7 = 1; l7 < 3; ++l7) {
					for(int k8 = -1; k8 < 3; ++k8) {
						int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
						int k10 = k2 + k8;
						int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
						boolean flag = k8 < 0;
						this.world.setBlockState(new BlockPos(k9, k10, k11), flag ? ModBlocks.ORE_OVERWORLD.getDefaultState() : Blocks.AIR.getDefaultState());
					}
				}
			}
		}

		IBlockState state = ModBlocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, l6 == 0 ? Axis.Z : Axis.X);

		for (int i8 = 0; i8 < 4; ++i8) {
			for (int l8 = 0; l8 < 4; ++l8) {
				for (int l9 = -1; l9 < 4; ++l9) {
					int l10 = i6 + (l8 - 1) * l6;
					int l11 = k2 + l9;
					int k12 = k6 + (l8 - 1) * i3;
					boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
					this.world.setBlockState(new BlockPos(l10, l11, k12), flag1 ? ModBlocks.ORE_OVERWORLD.getDefaultState() : state, 2);
				}
			}

			for (int i9 = 0; i9 < 4; ++i9) {
				for (int i10 = -1; i10 < 4; ++i10) {
					int i11 = i6 + (i9 - 1) * l6;
					int i12 = k2 + i10;
					int l12 = k6 + (i9 - 1) * i3;
					BlockPos blockpos = new BlockPos(i11, i12, l12);
					this.world.notifyNeighborsOfStateChange(blockpos, this.world.getBlockState(blockpos).getBlock(), false);
				}
			}
		}
		return true;
	}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
		if(worldTime % 100L == 0L) {
			long i = worldTime - 300L;
			ObjectIterator<Teleporter.PortalPosition> iterator = this.destinationCoordinateCache.values().iterator();
			
			while(iterator.hasNext()) {
				Teleporter.PortalPosition position = (Teleporter.PortalPosition)iterator.next();
				
				if(position == null || position.lastUpdateTime < i) iterator.remove();
			}
		}
	}
}
