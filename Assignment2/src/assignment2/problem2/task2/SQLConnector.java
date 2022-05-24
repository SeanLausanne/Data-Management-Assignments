package assignment2.problem2.task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {
	public Connection establishConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://34.152.13.133:3306/Brazil_EComm_User_Info", "root", "root");
		conn.setAutoCommit(false);
		return conn;
	}
}
