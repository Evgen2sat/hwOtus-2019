package ru.otus.hw12.jetty.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream htmlFile = AdminServlet.class.getResourceAsStream("/static/users.html");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        byte[] bytes=new byte[htmlFile.available()];
        htmlFile.read(bytes);
        resp.setContentLength(bytes.length);
        writer.print(new String(bytes));
        writer.flush();
        writer.close();
    }
}
