package vuquochuy.week01_lab_vuquochuy_21021491.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Log;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.AccountRepository;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.LogRepository;
import vuquochuy.week01_lab_vuquochuy_21021491.services.AccountServices;
import vuquochuy.week01_lab_vuquochuy_21021491.services.RoleServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/ControlServlet")
public class ControllerServlet extends HttpServlet {
    AccountServices accountServices = new AccountServices();
    AccountRepository accountRepository = new AccountRepository();
    LogRepository logRepository = new LogRepository();
    RoleServices roleServices = new RoleServices();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            switch (action) {
                case "login":
                    handleLogin(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = resp.getWriter();
        out.println("Error");
    }

    private boolean handleLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int checkLoginSuccess = accountServices.checkLogin(username, password);
        if (checkLoginSuccess == -1) {
            resp.getWriter().println(("Email not exist"));
            return false;
        } else if (checkLoginSuccess == 1) {
            resp.getWriter().println(("Password incorrect"));
            return false;
        }
        Account acount = accountRepository.findByEmail(username);
        Log log = new Log(acount.getAccount_id(), Date.valueOf(LocalDate.now()),null,"First Login");
        logRepository.add(log);
        HttpSession session = req.getSession();
        session.setAttribute("account", acount);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("dashborad.jsp");
        requestDispatcher.forward(req, resp);
        return true;
    }
}
