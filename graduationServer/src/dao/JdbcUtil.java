package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

import entity.AppUsedInfo;
import entity.CallInfo;
import entity.MessageInfo;
import entity.SensorInfo;
import entity.UserInfo;
import utils.StringConstant;

public class JdbcUtil
{
	//Database User
	private static final String USER_NAME = "root";
	//Database password
	private static final String PASSWORD = "011235";
	//Database driver
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//Database URL
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_graduation";
	Connection connection = null;
	public JdbcUtil()
	{
		
		try
		{
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection()
	{
		try
		{
			connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public ResultSet query(String sql, String... params )
	{
		ResultSet rs = null;
		PreparedStatement psmt = null;
		try
		{
			psmt = (PreparedStatement) connection.prepareStatement(sql);
			for(int i = 0; i < params.length; i++)
			{
				psmt.setString(i + 1, params[i]);
			}
			rs = psmt.executeQuery();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public boolean insert(String sql, String className, Object object, String uuid)
	{
		object.getClass().getName();
		int result = -1;
		PreparedStatement preparedStatement = null;
		try
		{
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			switch (className)
			{
				case StringConstant.USER_INFO_CLASS_NAME:
					UserInfo userInfo = (UserInfo) object;
					preparedStatement.setString(1, userInfo.getAccount());
					preparedStatement.setString(2, userInfo.getPassword());
					preparedStatement.setString(3, userInfo.getEmail());
					preparedStatement.setString(4, userInfo.getSchoolName());
					preparedStatement.setString(5, userInfo.getStudentId());
					preparedStatement.setDate(6, stringToDate(userInfo.getBirth()));
					preparedStatement.setString(7, userInfo.getSex());
					break;
				case StringConstant.CALL_INFO_CLASS_NAME:
					CallInfo callInfo = (CallInfo) object;
					preparedStatement.setString(1, uuid);
					preparedStatement.setTimestamp(2, stringToDateTime(callInfo.getRingTime()));
					preparedStatement.setTimestamp(3, stringToDateTime(callInfo.getCallStartTime()));
					preparedStatement.setTimestamp(4, stringToDateTime(callInfo.getCallStopTime()));
					break;
				case StringConstant.MESSAGE_INFO_CLASS_NAME:
					MessageInfo messageInfo = (MessageInfo) object;
					preparedStatement.setString(1, uuid);
					preparedStatement.setTimestamp(2, stringToDateTime(messageInfo.getMessageTime()));
					preparedStatement.setInt(3, messageInfo.getMessageType());
					break;
				case StringConstant.APP_INFO_CLASS_NAME:
					AppUsedInfo appUsedInfo = (AppUsedInfo) object;
					preparedStatement.setString(1, uuid);
					preparedStatement.setString(2, appUsedInfo.getAppName());
					preparedStatement.setTimestamp(3, stringToDateTime(appUsedInfo.getAppStartTime()));
					preparedStatement.setTimestamp(4, stringToDateTime(appUsedInfo.getAppStopTime()));
					break;
				case StringConstant.SENSOR_INFO_CLASS_NAME:
					SensorInfo sensorInfo = (SensorInfo) object;
					preparedStatement.setString(1, uuid);
					preparedStatement.setString(2, sensorInfo.getSensorName());
					preparedStatement.setTimestamp(3, stringToDateTime(sensorInfo.getSensorGetTime()));
					preparedStatement.setFloat(4, sensorInfo.getSensorValue());
					break;
				default:
					break;
			}
			result = preparedStatement.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result > 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public java.sql.Date stringToDate(String dateStr)
	{
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		java.sql.Date sqlDate = null;
		try
		{
			Date date = format.parse(dateStr);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	public Timestamp stringToDateTime(String dateStr)
	{
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		Timestamp timeStamp = null;
		try
		{
			Date date = format.parse(dateStr);
			timeStamp = new Timestamp(date.getTime());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeStamp;
	}
	
	
	public void releaseConnection()
	{
		if (null != connection)
		{
			try
			{
				connection.close();
				connection = null;
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
