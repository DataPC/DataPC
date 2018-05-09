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
@Path("/addCType")
public class AddComponentType {
	
	/**
     * Default constructor.
     */
    public AddComponentType() {
    }

    /**
     * Prida novy typ komponentu do databazy
     * @param name Meno noveho typu komponentu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("name") String name) {
    	Logger LOG = Logging.getLogger();
    	
    	try {
    		LOG.info("Pridavanie typu komponentu");
    		
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Component_type (name) VALUES (?);";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setString(1, name);
			
			query.executeUpdate();
			
			query.close();
			
			LOG.info("Pridany typ komponentu");
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
