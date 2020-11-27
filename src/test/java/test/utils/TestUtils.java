package test.utils;

import org.junit.Test;
import org.lwj.app.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestUtils {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	
	@Test
	public void myTest(){
		String password = userService.queryPasswordByUserName("lwj");
		System.out.println(password);
	}
}
