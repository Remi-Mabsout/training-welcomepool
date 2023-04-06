/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main.java;

import main.java.Querys.*;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 * A simple servlet taking advantage of features added in 3.0.
 * </p>
 *
 * <p>
 * The servlet is registered and mapped to /HelloServlet using the {@linkplain WebServlet
 * @HttpServlet}. The {@link} is injected by CDI.
 * </p>
 *
 */
@SuppressWarnings("serial")
@WebServlet("/addReview")
public class addReviewJspController extends HttpServlet {

    @Override
    public void init() {
        System.out.println("Servlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String URL = "jdbc:mysql://localhost:3306/welcome_pool_Code_Review";
        String USERNAME = "SVC_Java";
        String PASSWORD = "1xqOOMTNMjnzZ76TPaRA";
        PromotionDAO dao = new PromotionDAO(URL, USERNAME, PASSWORD);
        List<String> promotionsNames = new ArrayList<>();
        List<Promotion> classes = new ArrayList<>();

        try {
            classes = dao.getAll();
        } catch (Exception e){
            System.out.println(e);
        }
        req.setAttribute("classes", classes);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add_review.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = "jdbc:mysql://localhost:3306/welcome_pool_Code_Review";
        String USERNAME = "SVC_Java";
        String PASSWORD = "1xqOOMTNMjnzZ76TPaRA";

        if (request.getParameter("addReview") != null) {
            String name = request.getParameter("name");
            int c_id = Integer.parseInt(request.getParameter("classes_selected"));
            String d = request.getParameter("date");
            System.out.println("DATE : " + d);

            String description = request.getParameter("description");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            java.time.LocalDateTime dt = LocalDateTime.parse(d, formatter);

            Timestamp ts = Timestamp.valueOf(dt);

            ReviewDAO rDAO = new ReviewDAO(URL, USERNAME, PASSWORD);

            try {
                Review r = new Review(name,description,ts);
                r.setClassId(c_id);
                System.out.println(c_id);
                rDAO.add(r);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        response.sendRedirect("/Pool/index");
        //request.getRequestDispatcher("/index").forward(request,response);
        //request.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);
    }


}