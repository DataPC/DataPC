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
@Path("/addModel")
public class AddModel {

    /**
     * Default constructor.
     */
    public AddModel() {
    }

    /**
     * Prida novy model komponentu do databazy
     * @param man ID vyrobcu komponentu
     * @param ct ID typu komponentu 
     * @param name Nazov noveho komponentu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("man") int man, @QueryParam("ct") int ct, @QueryParam("name") String name) {
    	Logger LOG = Logging.getLogger();
    	
    	try {
    		LOG.info("Pridavanie modelu");
    		
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Model (manufacturer_id, component_type_id, name) VALUES (?,?,?);";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, man);
			query.setInt(2, ct);
			query.setString(3, name);
			
			query.executeUpdate();
			
			query.close();
			
			LOG.info("Pridany model");
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
