package CAAP.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CAAP.model.EventLogsBean;
import CAAP.model.RecordsBean;
import CAAP.model.UserBean;
import CAAP.utility.sql.SQLOperations;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/pages/newrecord.html")
public class ProcessAddNewRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	
	public void init() throws ServletException {
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
		
		String mark = request.getParameter("mark");
		String author = request.getParameter("author");
		String company = request.getParameter("company");
		String destination = request.getParameter("destination");
		String receivedBy = request.getParameter("receivedBy");
		String dateIn = request.getParameter("dateIn");
		String dateInOriginal = request.getParameter("dateInOriginal");
		String dateOut = request.getParameter("dateOut");
		String remarks = request.getParameter("remarks");
		String subject = request.getParameter("subject");
		
		RecordsBean record = new RecordsBean();
		record.setMark(mark);
		record.setAuthor(author);
		record.setCompany(company);
		record.setDestination(destination);
		record.setReceivedBy(receivedBy);
		record.setRemarks(remarks);
		record.setSubject(subject);
		
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
		
		HttpSession session=request.getSession(false);
		try{
			if(session.getAttribute("user")!=null)
			{
				UserBean user = (UserBean) session.getAttribute("user");
				user = SQLOperations.getUser(user.getUsername(), connection);
				if(user.getStatus().equals("Admin") || user.getStatus().equals("Staff"))
				{
					if(user.getCanAdd().equals("Enabled"))
					{
						if (connection != null) {
							if (SQLOperations.addRecord(record, connection)){
								record = SQLOperations.getRecordLatest(record, connection);
								System.out.println("Record Creation SUCCESSFUL! \nControl Number: "+record.getId());
								
								EventLogsBean event = new EventLogsBean();
								event.setControlNumber(record.getId());
								user = (UserBean) session.getAttribute("user");
								event.setUsername(user.getUsername());
								SQLOperations.addEvent(event, destination, remarks, connection);
								SQLOperations.updateRecord(record, record.getId(), connection);
								request.setAttribute("link", "forms.html");
								request.setAttribute("message", "Record Creation SUCCESSFUL! Control Number: "+record.getId());
								getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
							} else {
								System.out.println("Error creating new record.");
								request.setAttribute("link", "forms.html");
								request.setAttribute("message", "An error occoured. Please try again later.");
								getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
							}
						} else {
							System.out.println("Error creating new record. Invalid Connection");
							request.setAttribute("link", "forms.html");
							request.setAttribute("message", "An error occoured. Please try again later.");
							getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
						}
					}
					else
					{
						request.setAttribute("link", "tables.html");
						request.setAttribute("message", "This account cannot add a new record.");
						getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);
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
				request.setAttribute("message", "Session expired please login.");
				getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);
			}
		}
		catch(Exception e){request.setAttribute("link", "login.html");
		request.setAttribute("message", "Session expired please login.");
		getServletContext().getRequestDispatcher("/alert.jsp").include(request, response);}
	}

}
