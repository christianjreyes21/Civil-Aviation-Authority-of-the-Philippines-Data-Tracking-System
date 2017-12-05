package CAAP.model;

public class UserBean {

	private String username;
	private String password;
	private String status;
	private String auth;
	private String canAdd;
	private String name;
	
	
	public String getCanAdd() {
		return canAdd;
	}
	public void setCanAdd(String canAdd) {
		this.canAdd = canAdd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
