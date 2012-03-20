package WeatherToPachube;

import org.apache.commons.dbutils.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;


public class PersistFeedToDatabase {

	public Boolean PersistReading(WeatherClass weather)
	{
		
		Connection conn = null;
		String jdbcConnStr = "jdbc:sqlserver://HAL;instanceName=MSSQL2008;databaseName=WeatherLamp;user=WeatherLampUser;password=WeatherLampUser; ";
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		Date today = new Date();
		
		try
		{
			DbUtils.loadDriver(driverName);
			conn = DriverManager.getConnection(jdbcConnStr);
			QueryRunner runner = new QueryRunner();
			
			for (int i = 0; i < weather.getDays().size(); i++)
			{
				String sql = "INSERT INTO [BBCReadings] VALUES (111,'2012-01-01','2012-01-01','me title','me desciption')";
				int ii = runner.update(conn, sql);
			}			
			
			return true;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
	
	
	
}
