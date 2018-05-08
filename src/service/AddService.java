package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Stateless
@LocalBean
@Path("/addService")
public class AddService {

    /**
     * Default constructor. 
     */
    public AddService() {
    }

    /**
     * Jednoducha sluzba na stateless bean-e, publikovana cez GET REST
     * @param id
     * @return
     */
    @GET
    public boolean add(@QueryParam("PC") int PC, @QueryParam("st") int st) {   
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Service(computer_id, service_type_id, service_date) values(?,?,?);";
			
			Date datum = new Date(Calendar.getInstance().getTime().getTime());
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, PC);
			query.setInt(2, st);
			query.setDate(3, datum);
			
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
