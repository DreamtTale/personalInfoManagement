package personInfo;

import java.sql.*;

public class ConnectDB {
	private static String userName,password;
	public static void init(String user,String pw){
		userName=new String(user);
		password=new String(pw);
	}
	public static Statement connect() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=PersonInfo";
		Connection connection = DriverManager.getConnection(url, userName, password);
		Statement stat = connection.createStatement();
		return stat;
	}
}
