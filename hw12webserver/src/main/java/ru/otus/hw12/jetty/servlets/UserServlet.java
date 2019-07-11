package ru.otus.hw12.jetty.servlets;

import com.google.gson.Gson;
import ru.otus.hw12.Main;
import ru.otus.hw12.dbService.DBService;
import ru.otus.hw12.dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    private final DBService<User> userDBService;

    public UserServlet(DBService<User> userDBService) {
        this.userDBService = userDBService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd= req.getRequestDispatcher("html/index.html");

        rd.forward(req, resp);

//        String resultAsString = null;
//
//        try {
//            List<User> items = userDBService.getItems();
//
//            //resultAsString = gson.toJson(items);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        } finally {
//            try {
//                userDBService.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        resp.setContentType("application/json");
//        resp.setStatus(HttpServletResponse.SC_OK);
//        PrintWriter printWriter = resp.getWriter();
//        printWriter.print(resultAsString);
//        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
