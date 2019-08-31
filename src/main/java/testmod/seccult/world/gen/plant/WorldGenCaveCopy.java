package testmod.seccult.world.gen.plant;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;

public class WorldGenCaveCopy extends WorldGenerator
{
    private final Block block;
    private int essenceBlock;
    
    private int progressValue;

    public WorldGenCaveCopy(Block blockIn)
    {
        this.block = blockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (position = position.add(-30, 0, -30); position.getY() > 5 && worldIn.isAirBlock(position); position = position.down())
        {
            ;
        }

        if (position.getY() <= 4)
        {
            return false;
        }
        else
        {
            position = position.down(16);
            boolean[] aboolean = new boolean[108000];
            //int i = rand.nextInt(4) + 4;

            for (int j = 0; j < 8; ++j)
            {
                double d0 = rand.nextDouble() * 15.0D + 3.0D;
                double d1 = rand.nextDouble() * 9.0D + 2.0D;
                double d2 = rand.nextDouble() * 15.0D + 3.0D;
                double d3 = rand.nextDouble() * (60.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (30.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (60.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 59; ++l)
                {
                    for (int i1 = 1; i1 < 59; ++i1)
                    {
                        for (int j1 = 1; j1 < 29; ++j1)
                        {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 5.0D)
                            {
                                aboolean[(l * 60 + i1) * 30 + j1] = true;
                            }
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < 60; ++l1)
            {
                for (int i3 = 0; i3 < 60; ++i3)
                {
                    for (int i4 = 0; i4 < 30; ++i4)
                    {
                        if (aboolean[(l1 * 60 + i3) * 30 + i4])
                        {
                            worldIn.setBlockState(position.add(l1, i4, i3), i4 >= 4 ? Blocks.AIR.getDefaultState() : this.block.getDefaultState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}
