package testmod.seccult.util.handlers;

import net.minecraft.util.IStringSerializable;

public class PlantsHandler 
{
	public static enum EnumType implements IStringSerializable
	{
		Little(0, "little_flower"),
		Catch_Soul(1, "catch_the_soul"),
		C(2, "c"),
		M(3, "m"),
		Y(4, "y"),
		Grass(5, "grass");
		
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name, unlocializedName;
		
		private EnumType(int meta, String name) 
		{
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unlocializedName) 
		{
			this.meta = meta;
			this.name = name;
			this.unlocializedName = unlocializedName;
		}
		
		@Override
		public String getName() 
		{
			return this.name;
		}
		
		public int getMeta() 
		{
			return this.meta;
		}
		
		public String getUnlocializedName() 
		{
			return this.unlocializedName;
		}
		
		@Override
		public String toString() 
		{
			return this.name;
		}
		
        public static EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }
		
		static
		{
			for(EnumType enumtype : values())
			{
				META_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}
	}
}
