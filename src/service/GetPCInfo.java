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
@Path("/getPCInfo")
public class GetPCInfo {

    /**
     * Default constructor. 
     */
    public GetPCInfo() {
    }

    /**
     * Z databazy ziska informacie o pocitaci
     * @param id ID daneho pocitaca
     * @return JSON s informaciami o pocitaci
     */
    @GET
    public String getPCInfo(@QueryParam("id") int id) {
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			
			String query ="SELECT row_to_json(t) " +
					"FROM ( "+
					"SELECT PC.location, " +
					"( "+
					"	SELECT array_to_json(array_agg(row_to_json(d))) " +
					"	FROM ( " +
					"	SELECT ct.name AS ct_name, ma.name AS ma_name, m.name AS m_name	" +
					"	FROM Component c " +
					"		LEFT JOIN Model m ON m.id = c.model_id " + 
					"		LEFT JOIN Manufacturer ma ON ma.id = m.manufacturer_id " +
					"		LEFT JOIN Component_type ct ON ct.id = m.component_type_id " +
					"	WHERE c.computer_id = ? " +
					"	) d" +
					"	) AS pomoc " +
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
