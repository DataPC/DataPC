package app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import service.Service;

/**
 * Application class-a pre podporu REST controller-a
 * @author Jaroslav Jakubik
 */
@ApplicationPath("")
public class App extends Application {

	/**
	 * Registracia bean-y do REST aplikacie
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> cls = new HashSet<>();
		cls.add(Service.class);
		return cls;
	}

}
