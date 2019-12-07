package testmod.seccult.client.textlib;

import java.util.ArrayList;
import java.util.List;

import testmod.seccult.init.ModItems;

public class TextLib {
	public static final String NOTE_BASE = "note_base";
	public static final List<String> AllCaterogy = new ArrayList<String>();
	public static final String CATEGORY_INTRODUCE = "note_category_introduce";
	public static final String CATEGORY_MATERIAL = "note_category_material";
	public static final String CATEGORY_ITEM = "note_category_item";
	public static final String CATEGORY_TOOL = "note_category_tool";
	public static final String CATEGORY_CREATURE = "note_category_creature";
	public static final String CATEGORY_DAEDRA = "note_category_daedra";
	public static final String CATEGORY_BIOME = "note_category_biome";
	public static final String CATEGORY_HOLYEQUIPMENT = "note_category_holy_equipment";
	
	private static final String NOTEBOOK_BASE = "item_notebook_base";
	private static final String NOTEBOOK_BASE_1 = "item_notebook_1";
	private static final String NOTEBOOK_BASE_2 = "item_notebook_2";
	
	private static final String MAGICK_CORE_BASE = "item_magick_core_base";
	private static final String MAGICK_CORE_USE_1 = "item_magick_core_use_1";
	private static final String MAGICK_CORE_USE_2 = "item_magick_core_use_2";
	
	private static final String EFFECT_REFLECT_BASE = "item_effect_refract_base";
	
	private static final String ITEM_MAGICK_CORE = getName(ModItems.MagickCore);
	private static final String ITEM_NOTE_BOOK = getName(ModItems.NOTEBOOK);
	private static final String ITEM_EFFECT_REFLECT = getName(ModItems.EFFECT_REFRACT);

	public static void init()
	{
		AllCaterogy.add(CATEGORY_INTRODUCE);
		AllCaterogy.add(CATEGORY_MATERIAL);
		AllCaterogy.add(CATEGORY_ITEM);
		AllCaterogy.add(CATEGORY_TOOL);
		AllCaterogy.add(CATEGORY_CREATURE);
		AllCaterogy.add(CATEGORY_DAEDRA);
		AllCaterogy.add(CATEGORY_BIOME);
		AllCaterogy.add(CATEGORY_HOLYEQUIPMENT);
	}
	
	public static String getName(net.minecraft.item.Item item)
	{
		return item.getRegistryName().toString();
	}
	
	public enum Category
	{
		Introduce(CATEGORY_INTRODUCE), Material(CATEGORY_MATERIAL), Item(CATEGORY_ITEM), Tool(CATEGORY_TOOL),
		Creature(CATEGORY_CREATURE), Daedra(CATEGORY_DAEDRA), Biome(CATEGORY_BIOME), HolyEquipment(CATEGORY_HOLYEQUIPMENT);
		private String name;
		
		Category(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return this.name;
		}
	}
	
	public enum Item
	{
		MagickCore_base(ITEM_MAGICK_CORE, MAGICK_CORE_BASE), MagickCore_1(ITEM_MAGICK_CORE, MAGICK_CORE_USE_1), MagickCore_2(ITEM_MAGICK_CORE, MAGICK_CORE_USE_2),
		NoteBook_base(ITEM_NOTE_BOOK, NOTEBOOK_BASE), NoteBook_1(ITEM_NOTE_BOOK, NOTEBOOK_BASE_1), NoteBook_2(ITEM_NOTE_BOOK, NOTEBOOK_BASE_2),
		EFFECT_REFLECT_base(ITEM_EFFECT_REFLECT, EFFECT_REFLECT_BASE);
		
		private String string;
		private String ItemName;
		
		Item(String ItemName, String string)
		{
			this.string = string;
			this.ItemName = ItemName;
		}
		
		public String getString()
		{
			return this.string;
		}
		
		public String getItemName()
		{
			return this.ItemName;
		}
	}
}
