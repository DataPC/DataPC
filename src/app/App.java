package app;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import service.*;

/**
 * Application class-a pre podporu REST controller-a
 * @author DataPC
 */
@ApplicationPath("")
public class App extends Application {

	/**
	 * Registracia bean-y do REST aplikacie
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> cls = new HashSet<>();
		cls.add(GetPCInfo.class);
		cls.add(GetServiceInfo.class);
		cls.add(AddComponent.class);
		cls.add(AddComponentType.class);
		cls.add(AddComputer.class);
		cls.add(AddManufacturer.class);
		cls.add(AddModel.class);
		cls.add(AddService.class);
		cls.add(AddServiceType.class);
		return cls;
	}
}
