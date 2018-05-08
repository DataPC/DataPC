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

import org.json.simple.JSONObject;

/**
 * Stateless session bean-a s jednoduchou sluzbou
 * @author DataPC
 */
@Stateless
@LocalBean
@Path("/get")
public class GetService {

    /**
     * Default constructor. 
     */
    public GetService() {
    }

    /**
     * Jednoducha sluzba na stateless bean-e, publikovana cez GET REST
     * @param id
     * @return
     */
    @GET
    public String findId(@QueryParam("id") int id) {
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			
			String query ="select row_to_json(t) " +
					"from ( "+
					"SELECT PC.location, " +
					"( "+
					"	SELECT array_to_json(array_agg(row_to_json(d))) " +
					"	FROM ( " +
					"	SELECT ct.name AS ct_name, ma.name AS ma_name, m.name AS m_name	" +
					"	FROM Component c " +
					"		LEFT JOIN Model m on m.id = c.model_id " + 
					"		LEFT JOIN Manufacturer ma on ma.id = m.manufacturer_id " +
					"		LEFT JOIN Component_type ct on ct.id = m.component_type_id " +
					"	WHERE c.computer_id = ? " +
					"	) d" +
					"	) as pomoc " +
					" FROM Computer PC " +
					" WHERE PC.id = ? " +
					") t; ";	
			
			PreparedStatement select = con.prepareStatement(query);
			select.setInt(1, id);
			select.setInt(2, id);
			
			ResultSet rs = select.executeQuery();
			rs.next();
			
			String vysledok = rs.getString(1);
			
			rs.close();
			select.close();
			
			return vysledok;
			
			
    	} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return "chyba";   	
    }
    
}
