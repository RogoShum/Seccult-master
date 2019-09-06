package testmod.seccult.magick;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import testmod.seccult.magick.implementation.Implementation;
import testmod.seccult.magick.implementation.ImplementationCircle;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.magick.implementation.ImplementationGroup;
import testmod.seccult.magick.implementation.ImplementationProjectile;
import testmod.seccult.magick.implementation.ImplementationSelectBlock;
import testmod.seccult.magick.implementation.ImplementationSelectEntity;
import testmod.seccult.magick.implementation.ImplementationSelf;

public class ImplementationHandler {

	public static final String FocuseI = "focuse";
	public static final String ProjectileI = "projectile";
	public static final String GroupI = "group";
	public static final String CircleI = "cirlce";
	public static final String SelfI = "self";
	public static final String SelectEntityI = "noentity";
	public static final String SelectBlockI = "noblock";
	
	public static Implementation Focuse;
	public static Implementation Self;
	public static Implementation Group;
	public static Implementation Circle;
	public static Implementation Projectile;
	public static Implementation Entity;
	public static Implementation Block;

	private static Set<Implementation> implementations = new HashSet<>();

	public static void init()
	{
		Self = new ImplementationSelf(SelfI);
		Focuse = new ImplementationFocused(FocuseI);
		Projectile = new ImplementationProjectile(ProjectileI);
		Group = new ImplementationGroup(GroupI);
		Circle = new ImplementationCircle(CircleI);
		
		Entity = new ImplementationSelectEntity(SelectEntityI);
		Block = new ImplementationSelectBlock(SelectBlockI);
	}

	public static void addImplementation(Implementation implementation) {
		
		implementations.add(implementation);
	}

	@Nullable
	public static Implementation getImplementationFromName(String name) {
		for (Implementation implementation : implementations) {
			if (implementation.getNbtName().equals(name)) return implementation.clone();
		}
		return null;
	}
}
