<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.repositories.AccountRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.entities.Account" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.entities.Role" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.repositories.RoleRepository" %>
<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.repositories.LogRepository" %>
<%@ page import="vuquochuy.week01_lab_vuquochuy_21021491.entities.Log" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="java.io.IOException" %>
<%--
  Created by IntelliJ IDEA.
  User: Quoc Huy
  Date: 9/18/2024
  Time: 8:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="https://jakarta.apache.org/taglibs/core" prefix="c" %>--%>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</head>
<body>
<%!
    private AccountRepository accountRepository = new AccountRepository();
    private RoleRepository roleRepository = new RoleRepository();
    private LogRepository logRepository = new LogRepository();
%>
<%!
    private List<Account> accounts = accountRepository.findAll();
    private List<Role> roles = roleRepository.findAll();
    private List<Log> logs = logRepository.findAll();
    private Account account = new Account();
%>
<%
    Account accountFromLogin = (Account) session.getAttribute("account");
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light px-3">
    <div class="collapse navbar-collapse d-flex flex-row justify-content-between" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <div class="mx-3"><%= accountFromLogin.getEmail() %></div>
            </li>
            <li class="nav-item">
                <div class="mx-3"><%= accountFromLogin.getFull_name() %></div>
            </li>
            <li class="nav-item">
                <div class="mx-3"><%= accountFromLogin.getPhone() %></div>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="ControlServlet" method="post">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit" name="action" value="logout">Logout</button>
        </form>
    </div>
</nav>
<h1 class="text-center">Table Account</h1>
<table class="table">
    <thead>
    <tr>
        <th scope="col">numerical order</th>
        <th scope="col">account_id</th>
        <th scope="col">full name</th>
        <th scope="col">password</th>
        <th scope="col">email</th>
        <th scope="col">phone</th>
        <th scope="col">status</th>
        <th scope="col">actions</th>
    </tr>
    </thead>
    <tbody>
    <% int index = 1; %>
    <% for (Account account : accounts) { %>
    <tr>
        <th scope="row"><%= index++ %></th>
        <td><%= account.getAccount_id() %></td>
        <td><%= account.getFull_name() %></td>
        <td><%= account.getPassword() %></td>
        <td><%= account.getEmail() %></td>
        <td><%= account.getPhone() %></td>
        <td><%= account.getSatatus() %></td>
        <td>
            <button>edit</button>
            <button>delete</button>
            <button>view role</button>
            <button>grant access</button>
        </td>
    </tr>
    <% } %>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="8">
            <button type="button" data-bs-toggle="modal" data-bs-target="#addAccountModal">
                Add New Account
            </button>
            <br>
            <label>View accounts for a role:</label>
            <select name="role">
                <% for (Role role : roles) { %>
                <option value="<%= role.getRole_name() %>"><%= role.getRole_name() %></option>
                <% } %>
            </select>
        </td>
    </tr>
    </tfoot>
</table>
<div class="modal fade" id="addAccountModal" tabindex="-1" aria-labelledby="addAccountModalLabel" aria-hidden="true">
    <form id="addAccountForm" action="ControlServlet" method="post">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addAccountModalLabel">Add New Account</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="accountId" class="form-label">Account ID</label>
                        <input type="text" class="form-control" id="accountId" name="accountId" required>
                    </div>
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Status</label>
                        <select class="form-control" name="status" id="status">
                            <option value="0">Enable</option>
                            <option value="1">Disable</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" name="action" value="addAccount">Add</button>
                </div>
            </div>
        </div>
    </form>
</div>
<h1 class="text-center">Table Role</h1>
<table class="table">
    <thead>
    <tr>
        <th scope="col">numerical order</th>
        <th scope="col">role_id</th>
        <th scope="col">role_name</th>
        <th scope="col">description</th>
        <th scope="col">status</th>
    </tr>
    </thead>
    <tbody>
    <% int index1 = 1; %>
    <% for (Role role : roles) { %>
    <tr>
        <th scope="row"><%= index1++ %></th>
        <td><%= role.getRole_id() %></td>
        <td><%= role.getRole_name() %></td>
        <td><%= role.getDescription() %></td>
        <td><%= role.getStatus() %></td>
        <td>
            <button>edit</button>
            <button>delete</button>
        </td>
    </tr>
    <% } %>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="8">
            <button onclick="showAddAccountModal()">Add new Role</button>
            <br>
        </td>
    </tr>
    </tfoot>
</table>

<h1 class="text-center">Table Log</h1>
<table class="table">
    <thead>
    <tr>
        <th scope="col">numerical order</th>
        <th scope="col">account_id</th>
        <th scope="col">login_Time</th>
        <th scope="col">logout_Time</th>
        <th scope="col">notes</th>
    </tr>
    </thead>
    <tbody>
    <% int index2 = 1; %>
    <% for (Log log : logs) { %>
    <tr>
        <th scope="row"><%= index2++ %></th>
        <td><%= log.getAccount_id() %></td>
        <td><%= log.getLogin_date() %></td>
        <td><%= log.getLogout_date() %></td>
        <td><%= log.getDescription() %></td>
        <td>
            <button>edit</button>
            <button>delete</button>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
