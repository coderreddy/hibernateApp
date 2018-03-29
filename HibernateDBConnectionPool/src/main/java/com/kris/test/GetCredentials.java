package com.kris.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Servlet implementation class GetCredentials
 */
@WebServlet("/GetDetails")
public class GetCredentials extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = Logger.getLogger(GetCredentials.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCredentials() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("empId"));
		logger.info("User Provided ID "+ id);
		
		SessionFactory sf = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
		Session session = sf.getCurrentSession();
		Transaction tx = session.beginTransaction();
		LoginDetails ld = session.get(LoginDetails.class, id);
		tx.commit();
		logger.info("Transaction is committed");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(String.format("EMPID : %s, USERNAME : %s, PASSWORD : %s", ld.getEmpid(),ld.getUsername(),ld.getPassword()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("empId"));
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginDetails ld = new LoginDetails();
		ld.setEmpid(id);
		ld.setUsername(name);
		ld.setPassword(password);
		
		SessionFactory sf = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
		Session session = sf.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(ld);
		tx.commit();
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		pw.print("Values: UserName: " + name + " Password: " + password + " is saved into DB sucessfully");
		
	}

}
