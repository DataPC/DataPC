package service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

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
     * @param ID komponentu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("component") int com) {   
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
			// TODO Zalogovat vyminku
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Zalogovat vyminku
			e.printStackTrace();
		}
    	
    	return false;    	
    }
}
