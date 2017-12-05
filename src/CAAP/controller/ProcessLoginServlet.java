package CAAP.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CAAP.model.UserBean;
import CAAP.utility.sql.SQLOperations;

import java.sql.*;

@WebServlet("/pages/processlogin.html")
public class ProcessLoginServlet extends HttpServlet {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		try {
			if (connection != null) {
				UserBean user = SQLOperations.getUser(username,
						connection);
				if (user.getPassword().equals(password)) {
					System.out.println("successful login");
					request.setAttribute("user", user);
					HttpSession session=request.getSession();
					session.setAttribute("user",user);
					if(user.getCanAdd().equals("Enabled"))
					{
						getServletContext().getRequestDispatcher(
								"/pages/forms.html").forward(request,
								response);
					}
					else
					{
						getServletContext().getRequestDispatcher(
								"/pages/tables.html").forward(request,
								response);
					}
				} else {
					System.out.println("invalid password");
					String link = "../pages/login.html";
					String message = "Invalid Username or Password";
					request.setAttribute("link", link);
					request.setAttribute("message", message);
					getServletContext().getRequestDispatcher("/alert.jsp")
							.forward(request, response);
				}
			} else {
				System.out.println("invalid connection");
				String link = "../pages/login.html";
				String message = "Something went wrong! Please try again later.";
				request.setAttribute("link", link);
				request.setAttribute("message", message);
				getServletContext().getRequestDispatcher("/alert.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("Username does not exist");
			String link = "../pages/login.html";
			String message = "Invalid Username or Password";
			request.setAttribute("link", link);
			request.setAttribute("message", message);
			getServletContext().getRequestDispatcher("/alert.jsp")
					.forward(request, response);
		}
	}

}
