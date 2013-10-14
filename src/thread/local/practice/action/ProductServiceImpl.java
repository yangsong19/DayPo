package thread.local.practice.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import thread.local.practice.service.ProductService;
import thread.local.practice.util.DBUtil;

public class ProductServiceImpl implements ProductService {

	private static final String PRODUCT_UDPATE_SQL = "update product p set p.price=? where p.productId=? ;";
	private static final String LOG_INSERT_SQL = "insert into log(createtime, description) values(?,?) ;";
	
	public void updateProduct(long productId, int price) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			updateProduct(conn, PRODUCT_UDPATE_SQL, price, productId);
			insertLog(conn, LOG_INSERT_SQL, "update product id:" + productId + ", price:" + price);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

	private void insertLog(Connection conn, String logInsertSql, String desc) {
		try {
			PreparedStatement statement = conn.prepareStatement(logInsertSql);
			statement.setObject(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			statement.setObject(2, desc);
			
			int rows = statement.executeUpdate();
			if(rows != 0)
				System.out.println("Insert product update log successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateProduct(Connection conn, String productUdpateSql,
			int price, long productId) {
		try {
			PreparedStatement statement = conn.prepareStatement(productUdpateSql);
			statement.setObject(1, price);
			statement.setObject(2, productId);
			
			int rows = statement.executeUpdate();
			if(rows != 0)
				System.out.println("Update product id:" + productId + ", price:" + price + " successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
