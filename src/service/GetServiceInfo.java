package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Stateless session bean-a s jednoduchou sluzbou
 * @author DataPC
 */
@Stateless
@LocalBean
@Path("/getServiceInfo")
public class GetServiceInfo {

    /**
     * Default constructor. 
     */
    public GetServiceInfo() {
    }

    /**
     * Z databazy ziska vsetky servisy daneho pocitaca
     * @param id ID daneho pocitaca
     * @return JSON s polom servisov daneho pocitaca
     */
    @GET
    public String getServiceInfo(@QueryParam("id") int id) {
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			
			String query = "SELECT array_to_json(array_agg(row_to_json(t))) " +
					"	FROM ( " +
					"		SELECT st.name, s.service_date	" +
					"		FROM Service AS s " +
					"		LEFT JOIN Computer c ON c.id = s.computer_id " +
					"		LEFT JOIN Service_type st ON st.id = s.service_type_id " +
					"		WHERE s.computer_id = ? " +
					"	) t; ";	
			
			PreparedStatement select = con.prepareStatement(query);
			select.setInt(1, id);
			
			ResultSet rs = select.executeQuery();
			rs.next();
			
			String vysledok = rs.getString(1);
			
			rs.close();
			select.close();
			
			return vysledok;
			
    	} catch (NamingException e) {
			// TODO Zalogovat vyminku
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Zalogovat vyminku
			e.printStackTrace();
		}
		
    	return null;   	
    }
}
