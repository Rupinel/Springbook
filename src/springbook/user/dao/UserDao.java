package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.jdbc.MysqlErrorNumbers;

import springbook.user.domain.User;

abstract public class UserDao {
	
	abstract protected PreparedStatement makeStatement(Connection c) throws SQLException;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public UserDao(){
	}
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};

	public void add(final User user) throws DuplicateUserIdException, SQLException {
		try {
			this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
		} catch (SQLException e) {
			if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY)
				throw new DuplicateUserIdException(e);
			else
				throw new RuntimeException(e);
		}
	}
	
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, this.userMapper);
	}
	
	public void deleteAll() { 
		this.jdbcTemplate.update("delete from Users");
	}
	
	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {

			c = dataSource.getConnection();
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
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}
}
