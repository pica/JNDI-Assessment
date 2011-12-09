package com.pica.jndisource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

public class CheckDataSource extends HttpServlet {

    private static final String PASSWORD = "I've got a lovely bunch of";
    private static final String EXPECTED_USERNAME = "picaUser";
    private static final String EXPECTED_PASSWORD = "superSecret";
    private static final String EXPECTED_OLD_URL = "jdbc:oracle:thin:@pica:1521:db";
    private static final String EXPECTED_NEW_URL = "jdbc:oracle:thin:@//pica:1521/db";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/PicaDatabase");
        } catch (NamingException e) {
            throw new ServletException(e);
        }

        System.out.println(ds.getClass());
        BasicDataSource basicDataSource = (BasicDataSource) ds;

        if (!EXPECTED_USERNAME.equals(basicDataSource.getUsername()) ||
                !EXPECTED_PASSWORD.equals(basicDataSource.getPassword())) {
            throw new ServletException("Invalid JNDI Data Source credentials");
        }

        if (!EXPECTED_NEW_URL.equals(basicDataSource.getUrl()) && !EXPECTED_OLD_URL.equals(basicDataSource.getUrl())) {
            throw new ServletException("Invalid Data Source Connection String");
        }

        request.setAttribute("password", PASSWORD);
        request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
    }

}
