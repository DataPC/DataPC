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
@Path("/addCType")
public class AddModel {

    /**
     * Default constructor. 
     */
    public AddModel() {
    }

    /**
     * Jednoducha sluzba na stateless bean-e, publikovana cez GET REST
     * @param id
     * @return
     */
    @GET
    public boolean add(@QueryParam("man") int man, @QueryParam("ct") int ct, @QueryParam("name") String name) {   
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Model(manufacturer_id, component_type_id, name) values(?,?,?); ";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, man);
			query.setInt(2, ct);
			query.setString(3, name);
			
			query.executeUpdate();
			
			query.close();
			
			return true;
    	} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;    	
    }
    
}
