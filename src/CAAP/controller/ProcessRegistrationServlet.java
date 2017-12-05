package CAAP.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CAAP.utility.factory.UserBeanFactory;
import CAAP.model.UserBean;
import CAAP.utility.sql.SQLOperations;

import java.sql.*;

@WebServlet("/processregister.html")
public class ProcessRegistrationServlet extends HttpServlet {
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
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserBean user = UserBeanFactory.makeNewUser(firstName, lastName, username, password);
		
		if (connection != null) {
			if (SQLOperations.addUser(user, connection)){
				System.out.println("Account Creation SUCCESSFUL!");
				request.setAttribute("link", "pages/login.html");
				request.setAttribute("message", "Your account has been created successfully! Your account is disabled by default. To be able to use your account please ask your system administrator for assistance.");
				getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
			} else {
				System.out.println("Account Creation FAILED!");
				request.setAttribute("link", "pages/register.html");
				request.setAttribute("message", "Username already exists!");
				getServletContext().getRequestDispatcher("/alert.jsp").forward(request, response);
			}
		} else {
			System.out.println("invalid connection");
		}
	}

}
