package springbook.user.dao;

import javax.sql.DataSource;

public class MessageDao {

	private DataSource dataSource;
		
	public MessageDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
