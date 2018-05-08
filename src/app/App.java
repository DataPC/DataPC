package app;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import service.GetService;
import service.SetService;

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
		cls.add(GetService.class);
		cls.add(SetService.class);
		return cls;
	}

}
