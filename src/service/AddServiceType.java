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
@Path("/addServiceType")
public class AddServiceType {

    /**
     * Default constructor. 
     */
    public AddServiceType() {
    }

    /**
     * Prida novy typ servisu do databazy
     * @param name Nazov noveho typu servisu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("name") String name) {   
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Service_type (name) VALUES (?);";

			PreparedStatement query = con.prepareStatement(add);
			query.setString(1, name);
			
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
