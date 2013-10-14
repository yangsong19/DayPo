package thread.local.practice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/test";
	private static final String username = "root";
	private static final String password = "tiger";
	
//	private static Connection connection = null;
	
//  终于才让我明白了！原来要使每个线程都拥有自己的连接，而不是共享同一个连接，否则线程1有可能会关闭线程2的连接，所以线程2就报错了。一定是这样！
//	我把 Connection 放到了 ThreadLocal 中，这样每个线程之间就隔离了，不会相互干扰了。 
	private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	
	public static Connection getConnection() {
		Connection connection = connContainer.get();
		try {
			if(connection == null) {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connContainer.set(connection);
		}
		return connection;
	}
	
	public static void closeConnection() {
		Connection connection = connContainer.get();
		try {
			if(connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connContainer.remove();
		}
	}
}
