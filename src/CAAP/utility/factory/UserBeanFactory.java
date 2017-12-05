package CAAP.utility.factory;

import CAAP.model.UserBean;

public class UserBeanFactory {
	
	public static UserBean makeNewUser(String firstName, String lastName, String username, String password)
	{
		UserBean user = new UserBean();
		user.setName(lastName+", "+firstName);
		user.setUsername(username);
		user.setPassword(password);
		user.setAuth("Disabled");
		user.setStatus("Disabled");
		user.setCanAdd("Disabled");
		return user;
	}

}
