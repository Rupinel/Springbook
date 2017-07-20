package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	public AccountDao accountDao() {
		return new AccountDao(dataSource());
	}
	
	public MessageDao messageDao() {
		return new MessageDao(dataSource());
	}
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://cookcook.czprhotkwfkj.ap-northeast-2.rds.amazonaws.com:3306/CookCook");
		dataSource.setUsername("admin");
		dataSource.setPassword("qwer1234");

		return dataSource;
	}
	
}
