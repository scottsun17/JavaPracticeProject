import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class PreparedStatementMySQLConnection {

	private String userName = "ilovecat";
	private String password = "catmanalone4ever";
	private String loginName = "jack";
	private String loginPassword = "123456";
	private String createTable = "create table userInfo (userName char(30), password char(30));";

	@Test
	public void createTable() {
		PreparedStatement ps = null;
		Connection conn = null;

		try {
			// 1. Use the JDBC tool we created(JDBCUtil) to get connection
			conn = JDBCUtil.getConnection();

			// 2. create SQL Statement
			String sql = createTable;

			// 3.get PreparedStatement
			ps = conn.prepareStatement(sql);

			System.out.println(ps);
			// 4. use preparedStatement to excute SQL to create table with plug in parameter
			int count = ps.executeUpdate();

			// 5. check the result
			System.out.println("line affected:" + count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. release resource
			JDBCUtil.close(conn, ps);
		}
	}

	@Test
	public void insertLine() {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			// 1. Use the JDBC tool we created(JDBCUtil) to get connection
			conn = JDBCUtil.getConnection();

			// 2. create SQL statement
			String sql = "insert into userInfo(userName, password) values(?, ?);";

			// 3.get PreparedStatement
			ps = conn.prepareStatement(sql);

			// 4.plug in parameter userName and password
			// unnecessary step for inserting sql, put here as practice
			ps.setString(1, userName);
			ps.setString(2, password);

			// 5. execute sql statement
			int count = ps.executeUpdate();

			// 6. check result
			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7. release resource
			JDBCUtil.close(conn, ps);
		}
	}

	@Test
	public void deleteLine() {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCUtil.getConnection();

			String sql = "delete from userInfo where userName=?;";

			ps = conn.prepareStatement(sql);

			ps.setString(1, userName);

			int count = ps.executeUpdate();

			System.out.println(count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, ps);
		}
	}

	@Test
	public void updateLine() {
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = JDBCUtil.getConnection();

			String sql = "update userInfo set password='555555' where userName=?;";

			ps = conn.prepareStatement(sql);

			ps.setString(1, userName);

			int count = ps.executeUpdate();

			System.out.println(count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, ps);
		}
	}

	@Test
	public void testLoginUser() {
		ResultSet set = null;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCUtil.getConnection();

			String sql = "select * from userInfo where userName =? and password=?;";

			ps = conn.prepareStatement(sql);

			ps.setString(1, loginName);
			ps.setString(2, loginPassword);

			set = ps.executeQuery();

			if (set.next()) {
				System.out.println("Login successfully");
			} else {
				System.out.println("Login failed");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, ps, set);
		}
	}
}
