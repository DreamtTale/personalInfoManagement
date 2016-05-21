package personInfo;

import java.sql.*;

public class ConnectDB {
	public static Statement connect() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=PersonInfo";
		Connection connection = DriverManager.getConnection(url, "sa", "123456");
		Statement stat = connection.createStatement();
//		for(int i=10;i<50;i++){
//			String sql="insert into wages values('10000"+i+"','3000','300')";
//			stat.execute(sql);
//		}
		return stat;
	}
}
