package test;



import java.text.ParseException;

import org.junit.Test;
import org.lwj.app.login.entity.User;
import org.lwj.app.system.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class WebTest {

	@Autowired
	@Qualifier("authorityService")
	AuthorityService authorityService;
	
	
	@Test
	public void test() throws ParseException {
		
		User authorityUser = null;
		authorityUser = authorityService.queryAuthorityByUserId(1);
		System.out.println(authorityUser); 
		 
	}
	
}
