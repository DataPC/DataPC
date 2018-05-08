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
@Path("/addComponent")
public class AddComponent {

    /**
     * Default constructor. 
     */
    public AddComponent() {
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
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Component (computer_id, model_id) VALUES (?,?);";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, pc);
			query.setInt(2, model);
			
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
