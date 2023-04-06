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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import main.java.Querys.*;

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
@WebServlet("/indexReview")
public class reviewIndexJSPController extends HttpServlet {

    @Override
    public void init() {
        System.out.println("Servlet initialized successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(reviewIndexJSPController.class);
        String URL = "jdbc:mysql://localhost:3306/welcome_pool_Code_Review";
        String USERNAME = "SVC_Java";
        String PASSWORD = "1xqOOMTNMjnzZ76TPaRA";
        ReviewDAO r = new ReviewDAO(URL, USERNAME, PASSWORD);
        MemberDAO m = new MemberDAO(URL, USERNAME, PASSWORD);
        PromotionDAO pro = new PromotionDAO(URL,USERNAME,PASSWORD);

        List<Member> mems = new ArrayList<>();
        List<Promotion> prom = new ArrayList<>();
        List<Review> rev = new ArrayList<>();
        List<Review> rev2 = new ArrayList<>();
        try {
            rev = r.getAll();
            rev2 = r.getAllCurrent();
            prom = pro.getAll();
            mems = m.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<Review> short_reviews = new ArrayList<>();
        if (rev.size() > 0) {
            for(int i = 0; rev2.size() > 3 ? i < 3 : i < rev2.size(); i++){
                short_reviews.add(rev2.get(i));
            }

        }
        List<Promotion> short_proms = new ArrayList<>();
        if (prom.size() > 0) {
            for(int i = 0; prom.size() > 3 ? i < 3 : i < prom.size(); i++){
                short_proms.add(prom.get(i));
            }

        }

        req.setAttribute("sh_reviews",short_reviews);
        req.setAttribute("sh_promo",short_proms);

        req.setAttribute("review", rev);
        req.setAttribute("revSize",rev.size());
        req.setAttribute("memSize",mems.size());
        req.setAttribute("promSize",prom.size());



        req.getRequestDispatcher("review_index.jsp").forward(req, resp);

    }
}