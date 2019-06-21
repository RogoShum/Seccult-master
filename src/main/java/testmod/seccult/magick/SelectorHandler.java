package testmod.seccult.magick;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import testmod.seccult.magick.selector.Selector;
import testmod.seccult.magick.selector.SelectorEntity;

public class SelectorHandler {

	public static final Selector Entity = new SelectorEntity("selector_entity", "entity");
	

	private static final Set<Selector> selectors = new HashSet<>();

	static {
		addSelector(Entity);
	}

	public static void addSelector(Selector selector) {
		selectors.add(selector);
	}

	@Nullable
	public static Selector getSelectorFromName(String name) {
		for (Selector selector : selectors) {
			if (selector.getNbtName().equals(name) || selector.getShortName().equals(name)) return selector;
		}
		return null;
	}
}
