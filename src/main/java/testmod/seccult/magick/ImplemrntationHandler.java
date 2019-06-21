package testmod.seccult.magick;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import testmod.seccult.magick.implementation.Implementation;
import testmod.seccult.magick.implementation.ImplementationFocused;

public class ImplemrntationHandler {

	public static final String FocuseI = "focuse";
	public static final String ProjectileI = "projectile";
	public static final String GroupI = "group";
	public static final String CircleI = "cirlce";
	public static final String SelfI = "self";
	
	public Implementation Focuse;
	

	private Set<Implementation> implementations = new HashSet<>();

	public ImplemrntationHandler()
	{
		Focuse = new ImplementationFocused("implementation_focuse", FocuseI);
		addImplementation(Focuse);
	}

	public void addImplementation(Implementation implementation) {
		
		implementations.add(implementation);
	}

	@Nullable
	public Implementation getImplementationFromName(String name) {
		for (Implementation implementation : implementations) {
			if (implementation.getNbtName().equals(name) || implementation.getShortName().equals(name)) return implementation;
		}
		return null;
	}
}
