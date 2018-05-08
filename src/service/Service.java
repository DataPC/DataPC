package service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Stateless session bean-a s jednoduchou sluzbou
 * @author Jaroslav Jakubik
 */
@Stateless
@LocalBean
@Path("/vypis")
public class Service {

    /**
     * Default constructor. 
     */
    public Service() {
    }

    /**
     * Jednoducha sluzba na stateless bean-e, publikovana cez GET REST
     * @param who
     * @return
     */
    @GET
    public String sayHello(@QueryParam("who") String who) {
    	return "hello " + who;   	
    }
    @GET
    public String nieco(@QueryParam("test") String who) {
    	return "hello " + who;   	
    }
    
}
