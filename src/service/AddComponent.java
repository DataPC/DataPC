package service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import logger.Logging;

@Stateless
@LocalBean
@Path("/addComponent")
public class AddComponent {

    /**
     * Default constructor. 
     */
	
	Logger LOG = null;
	
    public AddComponent() {
    	LOG = Logging.getLogger();
    }

    /**
     * Prida novy komponent do databazy
     * @param pc ID pocitaca, ktoremu dany komponent prislucha
     * @param model ID modelu daneho komponentu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("pc") int pc, @QueryParam("model") int model) {   
    	try {
    		LOG.info("Pridavanie komponentu");
    		
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Component (computer_id, model_id) VALUES (?,?);";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, pc);
			query.setInt(2, model);
			
			LOG.finest("Query pripravena");
			query.executeUpdate();
			LOG.finest("Query vykonana");
			query.close();
			
			return true;
    	} catch (NamingException e) {
			LOG.severe(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			LOG.severe(e.toString());
			e.printStackTrace();
		}
    	
    	return false;    	
    }
}
