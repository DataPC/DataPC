package service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Stateless
@LocalBean
@Path("/set")
public class SetService {

    /**
     * Default constructor. 
     */
    public SetService() {
    }

    /**
     * Jednoducha sluzba na stateless bean-e, publikovana cez GET REST
     * @param id
     * @return
     */
    @GET
    public String setId(@QueryParam("id") String id) {
    	return "Set ID: " + id;   	
    }
    
}
