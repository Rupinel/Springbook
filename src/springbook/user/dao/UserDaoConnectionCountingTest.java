package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
		
		CountingConnectionMaker ccm = context.getBean("connectionMaker",CountingConnectionMaker.class);
		System.out.println("Connection counter : " + ccm.getCounter());
		
	}
}
