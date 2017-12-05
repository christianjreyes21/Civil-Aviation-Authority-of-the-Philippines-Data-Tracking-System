package CAAP.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import CAAP.model.EventLogsBean;
import CAAP.model.RecordsBean;
import CAAP.model.UserBean;
import CAAP.utility.sql.SQLOperations;

@WebServlet("/pages/acceptrecord.html")
public class ProcessEditRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		connection = SQLOperations.getConnection();
		
		if (connection != null) {
			getServletContext().setAttribute("dbConnection", connection);
			System.out.println("connection is READY.");
		} else {
			System.err.println("connection is NULL.");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		HttpSession session=request.getSession(false);
		try{
			if(session.getAttribute("user")!=null)
			{
				UserBean user = (UserBean) session.getAttribute("user");
				user = SQLOperations.getUser(user.getUsername(), connection);
				if(user.getStatus().equals("Admin") || user.getStatus().equals("Staff"))
				{
					String mark = request.getParameter("mark");
					String destination = request.getParameter("destination");
					String dateIn = request.getParameter("dateIn");
					String dateInOriginal = request.getParameter("dateInOriginal");
					String dateOut = request.getParameter("dateOut");
					String remarks = request.getParameter("remarks");
					
					user = (UserBean) session.getAttribute("user");
					int id = Integer.parseInt(request.getParameter("id"));
					try {
						if (connection != null) {
							RecordsBean record = SQLOperations.getRecord(id, connection);
							record.setMark(mark);
							record.setDestination(destination);
							record.setRemarks(remarks);
							java.util.Date date;
							java.sql.Date date1 = null;
							try {
								date = formatter.parse(dateIn);
								date1 = new Date(date.getTime());
							} catch (ParseException e2) {
								date=null;
							}
							record.setDateInLatest(date1);
							
							java.sql.Date date2 = null;
							try {
								date = formatter.parse(dateInOriginal);
								date2 = new Date(date.getTime());
							} catch (ParseException e1) {
								date=null;
							}
							record.setDateInOriginal(date2);
							
							java.sql.Date date3 = null;
							try {
								date = formatter.parse(dateOut);
								date3 = new Date(date.getTime());
							} catch (ParseException e) {
								date=null;
							}
							record.setDateOut(date3);
							System.out.println("pumasok dito");
							
							request.setAttribute("record", record);
							user = SQLOperations.getUser(user.getUsername(), connection);
							if(user.getAuth().equals("Full"))
							{
								System.out.println("pumasok dito");
								String author = request.getParameter("author");
								String company = request.getParameter("company");
								String receivedBy = request.getParameter("receivedBy");
								String subject = request.getParameter("subject");
								record.setAuthor(author);
								record.setCompany(company);
								record.setReceivedBy(receivedBy);
								record.setSubject(subject);
								
								
								EventLogsBean event = new EventLogsBean();
								event.setControlNumber(record.getId());
								event.setUsername(user.getUsername());
								SQLOperations.addEvent(event, destination, remarks, connection);
								SQLOperations.updateRecord(record, id, connection);
								
								request.setAttribute("link", "edit.html?id="+id);
								request.setAttribute("message", "Update SUCCESSFUL! Control Number: "+record.getId());
								getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
							}
							else if(user.getAuth().equals("Part"))
							{
								SQLOperations.updateRecord(record, id, connection);
								EventLogsBean event = new EventLogsBean();
								event.setControlNumber(record.getId());
								event.setUsername(user.getUsername());
								SQLOperations.addEvent(event, destination, remarks, connection);
								
								request.setAttribute("link", "edit.html?id="+id);
								request.setAttribute("message", "Update SUCCESSFUL! Control Number: "+record.getId());
								getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
							}
							else
								getServletContext().getRequestDispatcher(
										"/recordview.jsp").forward(request,
										response);
							
						} else {
							System.out.println("AN ERROR HAS OCCOURED. TRY AGAIN LATER. connection null");
							request.setAttribute("link", "pages/login.html");
							request.setAttribute("message", "AN ERROR HAS OCCOURED. TRY AGAIN LATER.");
							getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
						}
					} catch (NullPointerException npe) {
						System.err.println("Invalid Connection resource - "
								+ npe.getMessage());
						System.out.println("AN ERROR HAS OCCOURED. TRY AGAIN LATER. connection null");
						request.setAttribute("link", "pages/login.html");
						request.setAttribute("message", "AN ERROR HAS OCCOURED. TRY AGAIN LATER.");
						getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
					}
				}
				else
				{  
					request.setAttribute("link", "login.html");
					request.setAttribute("message", "USER DISABLED. Please contact your system administrator.");
					getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);
				}
				
			}
			else
			{  
				request.setAttribute("link", "login.html");
				request.setAttribute("message", "Session expired please login. Err1");
				getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);
			}
		}
		catch(Exception e){request.setAttribute("link", "login.html");
		request.setAttribute("message", "Session expired please login. Err2");
		e.printStackTrace();
		getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);}
		
		
				
		}
	

}
