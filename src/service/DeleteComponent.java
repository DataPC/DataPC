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
@Path("/deleteComponent")
public class DeleteComponent {

    /**
     * Default constructor.
     */
    public DeleteComponent() {
    }

    /**
     * Odstrani komponent z PC
     * @param com ID komponentu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("component") int com) {
    	Logger LOG = Logging.getLogger();
    	
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String delete = "DELETE FROM Component WHERE id = ?;";
			
			PreparedStatement query = con.prepareStatement(delete);
			query.setInt(1, com);
			
			query.executeUpdate();
			
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
