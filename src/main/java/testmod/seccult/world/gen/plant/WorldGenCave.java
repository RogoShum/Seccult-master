package testmod.seccult.world.gen.plant;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;

public class WorldGenCave extends WorldGenerator
{
    private final Block block;
    private int essenceBlock;

    public WorldGenCave(Block blockIn)
    {
        this.block = blockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	if(worldIn.isRemote)
    		return false;
    	
    	int distance = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16;
        int scale = (int)(distance / 1.5);
        if(rand.nextInt(5) == 0)
        	scale = 80;
        else
        	scale = 40;
    	Block essence = ModBlocks.OreSpawn;
    	this.essenceBlock = rand.nextInt(3) + 1;
    	
    	int height = scale / 4;
    	
        for (position = position.add(-(scale/2), 0, -(scale/2)); position.getY() > scale / 8 && worldIn.isAirBlock(position); position = position.down())
        {
            ;
        }
        if (position.getY() <= scale / 8)
        {
            return false;
        }
        else
        {
            position = position.down(scale / 8); 
            
            boolean[] aboolean = new boolean[scale * scale * height];
            int i = rand.nextInt(scale / 8) + scale / 8;
            
            int s16 = scale / 16;
            int s32 = scale / 32;
            int s8 = scale / 8;
            
            for (int j = 0; j < i; ++j)
            {
                double d0 = rand.nextDouble() * (scale / 2.28) + (height);
                double d1 = rand.nextDouble() * (scale / 3.2) + (scale / 6.4);
                double d2 = rand.nextDouble() * (scale / 2.28) + (height);
                double d3 = rand.nextDouble() * (scale - d0 - s16 + s32 + d0 / s32);
                double d4 = rand.nextDouble() * ((height) - d1 - s8) + s16 + d1 / s32;
                double d5 = rand.nextDouble() * (scale - d2 - s16) + s32 + d2 / s32;

                for (int l = 1; l < scale-1; ++l)
                {
                    for (int i1 = 1; i1 < scale-1; ++i1)
                    {
                        for (int j1 = 1; j1 < height -1; ++j1)
                        {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 0.8D)
                            {
                                aboolean[(l * scale + i1) * height + j1] = true;
                            }
                        }
                    }
                }
            }
            
            for (int k1 = 0; k1 < scale; ++k1)
            {
                for (int l2 = 0; l2 < scale; ++l2)
                {
                    for (int k = 0; k < height; ++k)
                    {
                            Block block = worldIn.getBlockState(position.add(k1, k, l2)).getBlock();
                            if (block == essence)
                                return false;
                            block = worldIn.getBlockState(position.add(k1, -k, l2)).getBlock();
                            if (block == essence)
                                return false;
                            block = worldIn.getBlockState(position.add(k1+scale, -k, l2+scale)).getBlock();
                            if (block == essence)
                                return false;
                            block = worldIn.getBlockState(position.add(k1+scale, -k, l2-scale)).getBlock();
                            if (block == essence)
                                return false;
                            block = worldIn.getBlockState(position.add(k1-scale, -k, l2+scale)).getBlock();
                            if (block == essence)
                                return false;
                            block = worldIn.getBlockState(position.add(k1-scale, -k, l2-scale)).getBlock();
                            if (block == essence)
                                return false;
                    }
                }
            }
            
            int height4 = (height / 4);
            
            for (int l1 = 0; l1 < scale; ++l1)
            {
                for (int i3 = 0; i3 < scale; ++i3)
                {
                    for (int i4 = 0; i4 < height4; ++i4)
                    {
                        if (aboolean[(l1 * scale + i3) * height + i4])
                        {
                        	worldIn.setBlockState(position.add(l1, i4-1, i3), ModBlocks.Hypha.getDefaultState(), 2);
                        	worldIn.setBlockState(position.add(l1, i4-2, i3), ModBlocks.Hypha.getDefaultState(), 2);
                        }
                    }
                }
            }
            
            int naheight = height - height4;
            
            for (int l1 = 0; l1 < scale; ++l1)
            {
                for (int i3 = 0; i3 < scale; ++i3)
                {
                    for (int i4 = 0; i4 < naheight; ++i4)
                    {
                        if (aboolean[(l1 * scale + i3) * height + i4])
                        {
                            worldIn.setBlockState(position.add(l1, i4, i3), this.block.getDefaultState(), 2);
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < scale; ++l1)
            {
                for (int i3 = 0; i3 < scale; ++i3)
                {
                    for (int i4 = 0; i4 < height; ++i4)
                    {
                        if (aboolean[(l1 * scale + i3) * height + i4])
                        {
                        	if(worldIn.getBlockState(position.add(l1, i4-1, i3)).getBlock() == ModBlocks.Hypha)
                        	{
                            	if(essenceBlock > 0 && rand.nextInt(scale) == 0)
                            	{
                            		worldIn.setBlockState(position.add(l1, i4-(rand.nextInt(2)+1), i3), essence.getDefaultState());
                            		System.out.println("I'm on " + position.add(l1, i4-(rand.nextInt(2)+1), i3) + " Now");
                            		--essenceBlock;
                            	}
                            	else if(rand.nextInt(20) == 0)
                        		(new WorldGenSeccultMushroom()).generate(worldIn, rand, position.add(l1, i4, i3));
                        	}
                        	
                        	if(rand.nextInt(20) == 0)
                        	{
                        		if(!worldIn.isAirBlock(position.add(l1, i4+1, i3)))
                        		{
                        			for(int h = 0; h < 5; ++h)
                        			{
                        				if(worldIn.isAirBlock(position.add(l1, i4+h, i3)))
                        				{
                        					if(rand.nextInt(2) == 0)
                        						worldIn.setBlockState(position.add(l1, i4+h, i3), ModBlocks.HYPHA_LIGHT.getDefaultState());
                        					else
                        						worldIn.setBlockState(position.add(l1, i4+h, i3), ModBlocks.HYPHA_LIGHT_BLUE.getDefaultState());
                        				}
                        				else
                        					break;
                        			}
                        		}
                        	}
                        }
                    }
                }
            }

            return true;
        }
    }
}
