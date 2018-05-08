package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.*;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Stateless
@LocalBean
@Path("/addComputer")
@TransactionManagement(TransactionManagementType.BEAN)
public class AddComputer {

    /**
     * Default constructor. 
     */
    public AddComputer() {
    }

    /**
     * Prida novy pocitac do databazy
     * @param location Pozicia noveho pocitaca
     * @return JSON s ID a location novo vytvoreneho pocitaca
     */
    @GET
    public String add(@QueryParam("location") String location) {   
    	try {
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			con.setAutoCommit(false);
			
			String add = "INSERT INTO Computer (location) VALUES (?);";
			
			String find = "SELECT row_to_json(t) " +
					" FROM ( " +
					" 	SELECT * " +
					"	FROM Computer " +
					" ) t; ";
			
			PreparedStatement query = con.prepareStatement(add);
			query.setString(1, location);
			
			query.executeUpdate();
			
			PreparedStatement query2 = con.prepareStatement(find);
		
			ResultSet rs = query2.executeQuery();
			rs.next();
			
			String vysledok = rs.getString(1);
			
			rs.close();
			query.close();
			query2.close();
			
			con.commit();
			con.setAutoCommit(true);
			
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
