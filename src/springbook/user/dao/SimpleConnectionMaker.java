package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://cookcook.czprhotkwfkj.ap-northeast-2.rds.amazonaws.com:3306/CookCook","admin","qwer1234");
		
		return c;
	}
}
