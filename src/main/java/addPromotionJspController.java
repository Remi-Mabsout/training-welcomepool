package main.java;

import main.java.Querys.Promotion;
import main.java.Querys.PromotionDAO;
import main.java.Querys.Review;
import main.java.Querys.ReviewDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@WebServlet("/addProm")
public class addPromotionJspController extends HttpServlet {

    @Override
    public void init() {
        System.out.println("Servlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add_promotion.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("je suis dans post de PromotionController");
        if (request.getParameter("addProm") != null) {

            String name = request.getParameter("name");
            String promotion = request.getParameter("classes_selected");

            String URL = "jdbc:mysql://localhost:3306/welcome_pool_Code_Review";
            String USERNAME = "SVC_Java";
            String PASSWORD = "1xqOOMTNMjnzZ76TPaRA";
            PromotionDAO pDAO = new PromotionDAO(URL, USERNAME, PASSWORD);

            try {
                pDAO.add(new Promotion(name));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        response.sendRedirect("/Pool/index");
    }
}
