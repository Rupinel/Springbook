package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	
	private DataSource datasource;
	
	public void setDataSource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = this.datasource.getConnection();
			ps = stmt.makePrepareStatement(c);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			} 
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
				}
			} 
		}	
	}
	
	public void executeSql(final String query) throws SQLException {
		workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePrepareStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(query);
				return ps;
			}
		});	
	}
}
