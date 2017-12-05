package CAAP.utility.sql;

public interface SQLCommands {
//RECORDS TABLE
	String INSERT_RECORD = "insert into records(mark, company, destination, author, receivedBy, dateInLatest, dateInOriginal, dateOut, remarks, subject) values(?,?,?,?,?,?,?,?,?,?)";
	String GET_ALL_RECORDS = "select * from records";
	String GET_RANGE_RECORDS = "select * from records where mark=? and dateInLatest between ? and ?";
	String GET_RECORD = "select * from records where controlNumber=?";
	String GET_RECORD_LATEST = "select TOP 1 * from records order by controlNumber desc";
	String GET_RECORD_MATCH = "select * from records where mark = ? and company = ? and destination = ? and author = ? and receivedBy = ? and remarks = ? and subject = ?";
	String UPDATE_RECORD = "update records set mark = ?, company = ?, destination = ?, author = ? ,receivedBy = ?, dateInLatest = ?, dateInOriginal = ?, dateOut = ?, remarks = ?, subject = ? where controlNumber = ?";
	String DELETE_RECORD = "delete from records where controlNumber = ?";
//USERS TABLE	
	String INSERT_USER = "insert into users(username, password, status, auth, name, canAdd) values(?,?,?,?,?,?)";
	String GET_ALL_USERS = "select * from users";
	String GET_USER = "select * from users where username=?";
	String UPDATE_USER_STATUS = "update users set status = ?, auth = ?, canAdd = ? where username = ?";
	String UPDATE_USER_PASSWORD = "update users set password = ? where username = ?";
	String DELETE_USER = "delete from users where username = ?";
//LOGS TABLE	
	String INSERT_EVENT = "insert into logs(controlNumber, username, destination, remarks) values(?,?,?,?)";
	String GET_ALL_EVENTS = "select * from logs, users";
	String GET_EVENT = "select * from logs, users where logs.controlNumber=? and logs.username=users.username";
}
