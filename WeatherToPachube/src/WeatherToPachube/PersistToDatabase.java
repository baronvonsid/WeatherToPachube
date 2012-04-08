package WeatherToPachube;

import org.apache.commons.dbutils.*;

import java.util.Arrays;
import java.sql.*;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.util.Date;


public class PersistToDatabase {

	public Integer PersistRegionForecast(Integer regionId, WeatherClass weather) throws SQLException
	{
		
		Connection conn = null;
		PreparedStatement statement = null;
		String sql = "INSERT INTO [BeebDayForecasts] ([Region],[ForecastDate],[PublishDate],[Title],[Description]) VALUES (?,?,?,?,?)";
		String jdbcConnStr = "jdbc:sqlserver://HAL;instanceName=MSSQL2008;databaseName=WeatherLamp;user=WeatherLampUser;password=WeatherLampUser; ";
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Integer returnCount = 0;
		Date today = new Date();
		
		try
		{
			DbUtils.loadDriver(driverName);
			conn = DriverManager.getConnection(jdbcConnStr);
			statement = conn.prepareStatement(sql);
			
			if (weather.getDays().size() != 3)
			{
				System.out.println("It appears that the beeb feed didn't return three days");
				return 0;
			}
			
			for (int i = 0; i < weather.getDays().size(); i++)
			{
				DayForecast day = weather.getDays().get(i);

			    Date forecastDate = weather.getDateOfForecast();
			    forecastDate.setTime(forecastDate.getTime() + (i * 86400000));
				
				statement.setInt(1, regionId);
				statement.setTimestamp(2, new java.sql.Timestamp(weather.getDateOfForecast().getTime()));
				statement.setTimestamp(3, new java.sql.Timestamp(today.getTime()));
				statement.setString(4, day.getTitle());
				statement.setString(5, day.getDescription());
				returnCount = returnCount + statement.executeUpdate();
			}
			
		} catch (SQLException ex) {
			this.rethrow(ex, sql, null);
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(conn);
		}
		return returnCount;
	}
	
	public void CheckUpdateRegion(Integer regionId, WeatherClass weather) throws SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = null;
		String jdbcConnStr = "jdbc:sqlserver://HAL;instanceName=MSSQL2008;databaseName=WeatherLamp;user=WeatherLampUser;password=WeatherLampUser; ";
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Date today = new Date();
		Integer returnCount = 0;
		
		try
		{
			DbUtils.loadDriver(driverName);
			conn = DriverManager.getConnection(jdbcConnStr);

			//Retrieve region from DB
			sql = "SELECT [Name] FROM [Regions] WHERE [Region] = ? AND DateExpired IS NULL";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, regionId);
			
            resultSet = statement.executeQuery();
            if(!resultSet.next()) {
            	
                //Region does not exist, add it
            	sql = "INSERT [Regions] ( [Region], [Name] ) VALUES (?,?)";
            	statement = conn.prepareStatement(sql);
            	statement.setInt(1, regionId);
            	statement.setString(2, weather.getRegionTitle());
            	returnCount = statement.executeUpdate();
            	if (returnCount != 1) {
            		throw new SQLException("Error executing SQL: " + sql);
            	}
            	
            	System.out.println("Added new region:" + regionId.toString());
            }
            else {
            	//If exists, check fields match
            	
            	String test = resultSet.getString("Name");
            	String test1 = weather.getRegionTitle();
            	
            	if (resultSet.getString("Name").equals(weather.getRegionTitle()) == false) {
            		
            		//If fields do not match, expire existing record and insert a new one
            		sql = "UPDATE [Regions] SET [DateExpired] = ? WHERE [Region] = ?";
                	statement = conn.prepareStatement(sql);
                	statement.setTimestamp(1, new java.sql.Timestamp(today.getTime()));
                	statement.setInt(2, regionId);
                	returnCount = statement.executeUpdate();
                	if (returnCount != 1) {
                		throw new SQLException("Error executing SQL: " + sql);
                	}
                	
                	System.out.println("Expired region:" + regionId.toString());
                	
                    //Region does not exist, add it
                	sql = "INSERT [Regions] ( [Region], [Name] ) VALUES (?,?)";
                	statement = conn.prepareStatement(sql);
                	statement.setInt(1, regionId);
                	statement.setString(2, weather.getRegionTitle());
                	returnCount = statement.executeUpdate();
                	if (returnCount != 1) {
                		throw new SQLException("Error executing SQL: " + sql);
                	}
                	
                	System.out.println("Added new version of region:" + regionId.toString());
            	}
            }
			
		} catch (SQLException ex) {
			this.rethrow(ex, sql, null);
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(conn);
		}
	}
	
	
    protected void rethrow(SQLException cause, String sql, Object[] params)
            throws SQLException {

            StringBuffer msg = new StringBuffer(cause.getMessage());

            msg.append(" Query: ");
            msg.append(sql);
            msg.append(" Parameters: ");

            if (params == null) {
                msg.append("[]");
            } else {
                msg.append(Arrays.asList(params));
            }

            SQLException e = new SQLException(msg.toString());
            e.setNextException(cause);

            throw e;
        }
	
}
