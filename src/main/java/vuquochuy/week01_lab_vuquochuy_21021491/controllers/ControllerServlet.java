package vuquochuy.week01_lab_vuquochuy_21021491.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.GrantAccess;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Log;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Role;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.AccountRepository;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.LogRepository;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.RoleRepository;
import vuquochuy.week01_lab_vuquochuy_21021491.services.AccountServices;
import vuquochuy.week01_lab_vuquochuy_21021491.services.RoleServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(urlPatterns = "/ControlServlet")
public class ControllerServlet extends HttpServlet {
    AccountServices accountServices = new AccountServices();
    AccountRepository accountRepository = new AccountRepository();
    LogRepository logRepository = new LogRepository();
    RoleRepository roleRepository = new RoleRepository();
    RoleServices roleServices = new RoleServices();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            switch (action) {
                case "login":
                    handleLogin(req, resp);
                    break;
                case "logout":
                    handleLogout(req, resp);
                    break;
                case "addAccount":
                    handleAddAccount(req, resp);
                    break;
                case "editAccount":
                    handleEditAccount(req, resp);
                    break;
                case "deleteAccount":
                    handleDeleteAccount(req, resp);
                    break;
                case "grantAccessRole":
                    handleGrantAccessRole(req, resp);
                    break;
                case "addRole":
                    handleAddRole(req, resp);
                    break;
                case "editRole":
                    handleEditRole(req, resp);
                    break;
                case "deleteRole":
                    handleDeleteRole(req, resp);
                    break;
                case "deleteLog":
                    handleDeleteLog(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } else {
            Account account = accountRepository.findByEmail(username);
            Log log = new Log(account.getAccount_id(), Timestamp.valueOf(LocalDateTime.now()), null, "First Login");
            System.out.println("Creating new log for account: " + account.getAccount_id());
            logRepository.add(log);
            System.out.println("Log created");
            HttpSession session = req.getSession();
            session.setAttribute("account", account);
            resp.sendRedirect("dashboard.jsp");
            return true;
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("account");
        logRepository.updateLogoutTime(Timestamp.valueOf(LocalDateTime.now()), account.getAccount_id());
        session.removeAttribute("account");
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }


    private void handleAddAccount(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String account_id = req.getParameter("accountId");
        String full_name = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        int status = Integer.parseInt(req.getParameter("status"));
        Account account = new Account(account_id, full_name, email, password, phone, status);
        accountRepository.add(account);
        resp.sendRedirect("dashboard.jsp");
    }

    private void handleDeleteAccount(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String account_id = req.getParameter("accountId");
        accountRepository.delete(account_id);
        resp.sendRedirect("dashboard.jsp");
    }

    private void handleEditAccount(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String account_id = req.getParameter("accountId");
        String full_name = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        int status = Integer.parseInt(req.getParameter("status"));
        Account account = new Account(account_id, full_name, email, password, phone, status);
        accountRepository.update(account);
        resp.sendRedirect("dashboard.jsp");
    }

    public void handleGrantAccessRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String account_id = req.getParameter("accountId");
        String role_id = req.getParameter("roleId");
        boolean is_grant = true;
        String notes = req.getParameter("notes");
        GrantAccess grantAccess = new GrantAccess(account_id, role_id, is_grant, notes);
        roleRepository.addGrantAccess(grantAccess);
        resp.sendRedirect("dashboard.jsp");
    }

    public void handleAddRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String role_id = req.getParameter("roleId");
        String role_name = req.getParameter("role_name");
        String description = req.getParameter("description");
        int status = Integer.parseInt(req.getParameter("status"));
        Role role = new Role(role_id, role_name, description, status);
        roleRepository.addRole(role);
        resp.sendRedirect("dashboard.jsp");
    }

    public void handleEditRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String role_id = req.getParameter("roleId");
        String role_name = req.getParameter("role_name");
        String description = req.getParameter("description");
        int status = Integer.parseInt(req.getParameter("status"));
        Role role = new Role(role_id, role_name, description, status);
        System.out.println(role);
        roleRepository.update(role);
        resp.sendRedirect("dashboard.jsp");
    }

    public void handleDeleteRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String role_id = req.getParameter("roleId");
        roleRepository.delete(role_id);
        resp.sendRedirect("dashboard.jsp");
    }
    public void handleDeleteLog(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Timestamp loginTime = Timestamp.valueOf(req.getParameter("loginTime"));
        logRepository.delete(loginTime);
        resp.sendRedirect("dashboard.jsp");
    }
}
