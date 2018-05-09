package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
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
@Path("/addService")
public class AddService {

    /**
     * Default constructor. 
     */
    public AddService() {
    }

    /**
     * Prida novy servis (opravu, udrzbu...) danemu pocitacu
     * @param pc ID daneho pocitaca
     * @param st Typ daneho servisu
     * @return true - ak zbehne korektne, false - ak nie
     */
    @GET
    public boolean add(@QueryParam("PC") int PC, @QueryParam("st") int st) {
    	Logger LOG = Logging.getLogger();
    	
    	try {
    		LOG.info("Pridavanie servisu");
    		
    		javax.naming.Context ic = new javax.naming.InitialContext();
			javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:");
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PostgresDS");
			java.sql.Connection con = ds.getConnection();
			
			String add = "INSERT INTO Service (computer_id, service_type_id, service_date) VALUES (?,?,?);";
			
			Date datum = new Date(Calendar.getInstance().getTime().getTime());
			
			PreparedStatement query = con.prepareStatement(add);
			query.setInt(1, PC);
			query.setInt(2, st);
			query.setDate(3, datum);
			
			query.executeUpdate();
			
			query.close();
			
			LOG.info("Pridany servis");
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
