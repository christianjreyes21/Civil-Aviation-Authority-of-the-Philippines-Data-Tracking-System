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

@WebServlet("/pages/deleteuser.html")
public class ProcessDeleteUserServlet extends HttpServlet {
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
		
		HttpSession session=request.getSession(false);
		try{
			if(session.getAttribute("user")!=null)
			{
				UserBean user = (UserBean) session.getAttribute("user");
				user = SQLOperations.getUser(user.getUsername(), connection);
				if(user.getStatus().equals("Admin") || user.getStatus().equals("Staff"))
				{
					if(user.getStatus().equals("Admin"))
					{
						String username = request.getParameter("username");
						try {
							if (connection != null) 
							{
								SQLOperations.deleteUser(username, connection);
								request.setAttribute("link", "settings.html");
								request.setAttribute("message", username+" has been deleted successfully!");
								getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
								
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
						request.setAttribute("link", "tables.html");
						request.setAttribute("message", "ACTION PROHIBITED. Please contact your system administrator.");
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
