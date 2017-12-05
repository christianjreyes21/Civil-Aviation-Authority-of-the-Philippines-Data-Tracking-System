package CAAP.utility.sql;

import java.sql.*;

import javax.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Date;

import CAAP.model.EventLogsBean;
import CAAP.model.RecordsBean;
import CAAP.model.UserBean;
import CAAP.utility.Security;
import CAAP.utility.sql.SQLCommands;

public class SQLOperations implements SQLCommands {

	private static Connection connection;
	
	private SQLOperations() {
	}
	
	private static Connection getDBConnection() {
		try {
		    InitialContext context = new InitialContext();
		    DataSource dataSource = (DataSource) 
		     context.lookup("java:comp/env/jdbc/CAAP-DS");
		    
		    if (dataSource != null) {
		    	connection = dataSource.getConnection();
		    } else {
		    	System.err.println("DataSource is NULL.");
		    }
		} catch (NamingException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return connection;
	 }
	
	public static Connection getConnection() {
		return (connection!=null)?connection:getDBConnection();
	}
//RECORDS TABLE
	public static boolean addRecord(RecordsBean record, 
		Connection connection) {
		
		try {
	        PreparedStatement pstmt = connection.prepareStatement(INSERT_RECORD);
	        pstmt.setString(1, record.getMark());
	        pstmt.setString(2, record.getCompany()); 
	        pstmt.setString(3, record.getDestination());
	        pstmt.setString(4, record.getAuthor());
	        pstmt.setString(5, record.getReceivedBy());
	        pstmt.setDate(6, record.getDateInLatest());
	        pstmt.setDate(7, record.getDateInOriginal());
	        pstmt.setDate(8, record.getDateOut());
	        pstmt.setString(9, record.getRemarks());
	        pstmt.setString(10, record.getSubject());
	        pstmt.executeUpdate(); // execute insert statement  
		} catch (SQLException sqle) {
			System.out.println("SQLException - addRecord: " + sqle.getMessage());
			return false; 
		}	
		return true;
	}
	
	public static RecordsBean getRecord(int id, 
		Connection connection) {
		RecordsBean record = new RecordsBean();
		 
		try {
	        PreparedStatement pstmt = 
	        	connection.prepareStatement(GET_RECORD);
	        pstmt.setInt(1, id);             
	        ResultSet rs  = pstmt.executeQuery();
	        
	        while (rs.next()) { 
	        	record.setId(id);
	        	record.setMark(rs.getString("mark"));
	        	record.setCompany(rs.getString("company"));
	        	record.setDestination(rs.getString("destination"));
	        	record.setAuthor(rs.getString("author"));
	        	record.setReceivedBy(rs.getString("receivedBy"));
	        	record.setDateInLatest(rs.getDate("dateInLatest"));
	        	record.setDateInOriginal(rs.getDate("dateInOriginal"));
	        	record.setDateOut(rs.getDate("dateOut"));
	        	record.setRemarks(rs.getString("remarks"));
	        	record.setSubject(rs.getString("subject"));
	        }
		} catch (SQLException sqle) {
			System.out.println("SQLException - getRecord: " 
					+ sqle.getMessage());
			return record; 
		}	
		return record;
	}
	
	public static RecordsBean getRecordLatest(RecordsBean record, 
			Connection connection) {
			 
			try {
		        PreparedStatement pstmt = 
		        	connection.prepareStatement(GET_RECORD_LATEST);
		        ResultSet rs  = pstmt.executeQuery();
		        
		        while (rs.next()) { 
		        	record.setId(rs.getInt("controlNumber"));
		        	record.setMark(rs.getString("mark"));
		        	record.setCompany(rs.getString("company"));
		        	record.setDestination(rs.getString("destination"));
		        	record.setAuthor(rs.getString("author"));
		        	record.setReceivedBy(rs.getString("receivedBy"));
		        	record.setDateInLatest(rs.getDate("dateInLatest"));
		        	record.setDateInOriginal(rs.getDate("dateInOriginal"));
		        	record.setDateOut(rs.getDate("dateOut"));
		        	record.setRemarks(rs.getString("remarks"));
		        	record.setSubject(rs.getString("subject"));
		        }
			} catch (SQLException sqle) {
				System.out.println("SQLException - getRecord: " 
						+ sqle.getMessage());
				return record; 
			}	
			return record;
		}
	
	public static ResultSet getAllRecords(Connection connection) {
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(GET_ALL_RECORDS);  
		} catch (SQLException sqle) {
			System.out.println("SQLException - getAllRecords: " 
			  + sqle.getMessage());
			return rs; 
		}	
		return rs;
	}
	
	public static ResultSet getAllRecordsRange(String mark, java.sql.Date date1, java.sql.Date date2, Connection connection) {
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(GET_RANGE_RECORDS);  
		} catch (SQLException sqle) {
			System.out.println("SQLException - getAllRecordsRange: " 
			  + sqle.getMessage());
			return rs; 
		}	
		return rs;
	}
	
	public static int updateRecord(RecordsBean record, 
		int id, Connection connection) {
		int updated = 0;
		try {
			connection.setAutoCommit(false);
	        PreparedStatement pstmt = 
	        	connection.prepareStatement(UPDATE_RECORD);
	        pstmt.setString(1, record.getMark());
	        pstmt.setString(2, record.getCompany()); 
	        pstmt.setString(3, record.getDestination());
	        pstmt.setString(4, record.getAuthor());
	        pstmt.setString(5, record.getReceivedBy());
	        pstmt.setDate(6, (Date) record.getDateInLatest());
	        pstmt.setDate(7, (Date) record.getDateInOriginal());
	        pstmt.setDate(8, (Date) record.getDateOut());
	        pstmt.setString(9, record.getRemarks());
	        pstmt.setString(10, record.getSubject());
	        pstmt.setInt(11, record.getId());
	        updated = pstmt.executeUpdate();   
	        connection.commit();
		} catch (SQLException sqle) {
			System.out.println("SQLException - updateRecord: " 
				+ sqle.getMessage());
			
			try {
				connection.rollback();
			} catch (SQLException sql) {
				System.err.println("Error on Update Connection Rollback - " 
					+ sql.getMessage());
			}
			return updated; 
		}	
		return updated;
	}
	
	public static synchronized int deleteRecord(int id, Connection connection) {
		int updated = 0;
		
		try {
			connection.setAutoCommit(false);
	        PreparedStatement pstmt = connection.prepareStatement(DELETE_RECORD);
	        pstmt.setInt(1, id);             
	        updated  = pstmt.executeUpdate();
	        connection.commit();
		} catch (SQLException sqle) {
			System.out.println("SQLException - deleteRecord: " + sqle.getMessage());
			
			try {
				connection.rollback();
			} catch (SQLException sql) {
				System.err.println("Error on Delete Connection Rollback - " + sql.getMessage());
			}
			return updated; 
		}	
		return updated;
	}
//USERS TABLE
	public static boolean addUser(UserBean user, 
			Connection connection) {
			
			try {
		        PreparedStatement pstmt = connection.prepareStatement(INSERT_USER);
		        pstmt.setString(1, user.getUsername());
		        pstmt.setString(2, Security.encrypt(user.getPassword())); 
		        pstmt.setString(3, user.getStatus());
		        pstmt.setString(4, user.getAuth());
		        pstmt.setString(5, user.getName());
		        pstmt.setString(6, user.getCanAdd());
		        pstmt.executeUpdate(); // execute insert statement  
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				System.out.println("SQLException - addUser: " + sqle.getMessage());
				return false; 
			}	
			return true;
		}
		
		public static UserBean getUser(String username, 
			Connection connection) {
			UserBean user = new UserBean();
			 
			try {
		        PreparedStatement pstmt = 
		        	connection.prepareStatement(GET_USER);
		        pstmt.setString(1, username);             
		        ResultSet rs  = pstmt.executeQuery();
		        
		        while (rs.next()) { 
		        	user.setUsername(username);
		        	user.setPassword(Security.decrypt(rs.getString("password")));
		        	user.setStatus(rs.getString("status"));
		        	user.setAuth(rs.getString("auth"));
		        	user.setName(rs.getString("name"));
		        	user.setCanAdd(rs.getString("canAdd"));
		        }
			} catch (SQLException sqle) {
				System.out.println("SQLException - getUser: " 
						+ sqle.getMessage());
				return user; 
			}	
			return user;
		}
		
		public static ResultSet getAllUsers(Connection connection) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(GET_ALL_USERS);  
			} catch (SQLException sqle) {
				System.out.println("SQLException - getAllUsers: " 
				  + sqle.getMessage());
				return rs; 
			}	
			return rs;
		}
		
		public static int updateUserStatus(UserBean user, Connection connection) {
			int updated = 0;
			try {
				connection.setAutoCommit(false);
		        PreparedStatement pstmt = 
		        	connection.prepareStatement(UPDATE_USER_STATUS);
		        pstmt.setString(1, user.getStatus());
		        pstmt.setString(2, user.getAuth()); 
		        pstmt.setString(3, user.getCanAdd()); 
		        pstmt.setString(4, user.getUsername());
		        updated = pstmt.executeUpdate();   
		        connection.commit();
			} catch (SQLException sqle) {
				System.out.println("SQLException - updateUserStatus: " 
					+ sqle.getMessage());
				
				try {
					connection.rollback();
				} catch (SQLException sql) {
					System.err.println("Error on Update Connection Rollback - " 
						+ sql.getMessage());
				}
				return updated; 
			}	
			return updated;
		}
		
		public static int updateUserPassword(UserBean user, Connection connection) {
				int updated = 0;
				try {
					connection.setAutoCommit(false);
			        PreparedStatement pstmt = 
			        	connection.prepareStatement(UPDATE_USER_PASSWORD);
			        pstmt.setString(1, Security.encrypt(user.getPassword()));
			        pstmt.setString(2, user.getUsername()); 
			        updated = pstmt.executeUpdate();   
			        connection.commit();
				} catch (SQLException sqle) {
					System.out.println("SQLException - updateUserPassword: " 
						+ sqle.getMessage());
					
					try {
						connection.rollback();
					} catch (SQLException sql) {
						System.err.println("Error on Update Connection Rollback - " 
							+ sql.getMessage());
					}
					return updated; 
				}	
				return updated;
			}
		
		public static synchronized int deleteUser(String username, Connection connection) {
			int updated = 0;
			
			try {
				connection.setAutoCommit(false);
		        PreparedStatement pstmt = connection.prepareStatement(DELETE_USER);
		        pstmt.setString(1, username);             
		        updated  = pstmt.executeUpdate();
		        connection.commit();
			} catch (SQLException sqle) {
				System.out.println("SQLException - deleteUser: " + sqle.getMessage());
				
				try {
					connection.rollback();
				} catch (SQLException sql) {
					System.err.println("Error on Delete Connection Rollback - " + sql.getMessage());
				}
				return updated; 
			}	
			return updated;
		}
//EVENTS TABLE
		public static boolean addEvent(EventLogsBean event, String destination, String remarks,
				Connection connection) {
				
				try {
			        PreparedStatement pstmt = connection.prepareStatement(INSERT_EVENT);
			        pstmt.setInt(1, event.getControlNumber());
			        pstmt.setString(2, event.getUsername());
			        pstmt.setString(3, destination);
			        pstmt.setString(4, remarks);
			        pstmt.executeUpdate(); // execute insert statement  
				} catch (SQLException sqle) {
					System.out.println("SQLException - addEvent: " + sqle.getMessage());
					return false; 
				}	
				return true;
			}
			
		public static ResultSet getAllEvents(int controlNumber, 
			Connection connection) {
			ResultSet rs = null;
			try {
		        PreparedStatement pstmt = 
		        	connection.prepareStatement(GET_EVENT);
		        pstmt.setInt(1, controlNumber);             
		        rs  = pstmt.executeQuery();
		        
			} catch (SQLException sqle) {
				System.out.println("SQLException - getAllEvents: " 
						+ sqle.getMessage());
				return rs; 
			}	
			return rs;
		}
		
		
}
