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
    PrintWriter printWriter = response.getWriter();
    Account accountFromLogin = (Account) session.getAttribute("account");
%>
<%!
    public String renderNavbar(Account accountFromLogin) {
        StringBuilder sb = new StringBuilder();
        sb.append("<nav class=\"navbar navbar-expand-lg navbar-light bg-light px-3\">");
        sb.append("<div class=\"collapse navbar-collapse d-flex flex-row justify-content-between\" id=\"navbarSupportedContent\">");
        sb.append("<ul class=\"navbar-nav mr-auto\">");
        sb.append("<li class=\"nav-item\">");
        sb.append("<div class=\"mx-3\">").append(accountFromLogin.getEmail()).append("</div>");
        sb.append("</li>");
        sb.append("<li class=\"nav-item\">");
        sb.append("<div class=\"mx-3\">").append(accountFromLogin.getFull_name()).append("</div>");
        sb.append("</li>");
        sb.append("<li class=\"nav-item\">");
        sb.append("<div class=\"mx-3\">").append(accountFromLogin.getPhone()).append("</div>");
        sb.append("</li>");
        sb.append("</ul>");
        sb.append("<form class=\"form-inline my-2 my-lg-0\" action=\"ControlServlet\" method=\"post\">");
        sb.append("<button class=\"btn btn-outline-success my-2 my-sm-0\" type=\"submit\" name=\"action\" value=\"logout\">Logout</button>");
        sb.append("</form>");
        sb.append("</div>");
        sb.append("</nav>");
        return sb.toString();
    }
%>
<%!
    public String renderTableAccount(List<Account> accounts) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1 class=\"text-center\">Table Account</h1>");
        sb.append("<table class=\"table\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th scope=\"col\">numerical order</th>");
        sb.append("<th scope=\"col\">account_id</th>");
        sb.append("<th scope=\"col\">full name</th>");
        sb.append("<th scope=\"col\">password</th>");
        sb.append("<th scope=\"col\">email</th>");
        sb.append("<th scope=\"col\">phone</th>");
        sb.append("<th scope=\"col\">status</th>");
        sb.append("<th scope=\"col\">role of account</th>");
        sb.append("<th scope=\"col\">actions</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        int index = 1;
        for (Account account : accounts) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(index++).append("</th>");
            sb.append("<td>").append(account.getAccount_id()).append("</td>");
            sb.append("<td>").append(account.getFull_name()).append("</td>");
            sb.append("<td>").append(account.getPassword()).append("</td>");
            sb.append("<td>").append(account.getEmail()).append("</td>");
            sb.append("<td>").append(account.getPhone()).append("</td>");
            sb.append("<td>").append(account.getSatatus()).append("</td>");
            sb.append("<td>");
            accountRepository.getRoleForAccount(account.getAccount_id()).forEach(grantAccess -> {
                sb.append(grantAccess.getRole_id()).append(",");
            });
            sb.append("</td>");
            sb.append("<td>");
            sb.append("<button value=\"edit-Account\" onclick=\"setEditAccountActive()\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#addAccountModal\">edit</button>&nbsp;");
            sb.append("<form action=\"ControlServlet\" method=\"post\" style=\"display:inline;\">");
            sb.append("<input type=\"hidden\" name=\"accountId\" value=\"").append(account.getAccount_id()).append("\">");
            sb.append("<button type=\"submit\" name=\"action\" value=\"deleteAccount\">delete</button>");
            sb.append("</form>&nbsp;");
            sb.append("<button value=\"grant-access\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#grantAccessModal\">grant access</button>");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("<tfoot>");
        sb.append("<tr>");
        sb.append("<td colspan=\"8\">");
        sb.append("<button onclick=\"setAddAccountActive()\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#addAccountModal\">Add New Account</button>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<label>View accounts for a role:</label>");
        sb.append("<select name=\"role\">");
        sb.append("<option value=\"all\">All</option>");
        for (Role role : roles) {
            sb.append("<option value=\"").append(role.getRole_id()).append("\">")
                    .append(role.getRole_name()).append("</option>");
        }
        sb.append("</select>");
        sb.append("</tfoot>");
        sb.append("</table>");
       return sb.toString();
    }
%>
<%!
    public String renderTableRole(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1 class=\"text-center\">Table Role</h1>");
        sb.append("<table class=\"table\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th scope=\"col\">numerical order</th>");
        sb.append("<th scope=\"col\">role_id</th>");
        sb.append("<th scope=\"col\">role_name</th>");
        sb.append("<th scope=\"col\">description</th>");
        sb.append("<th scope=\"col\">status</th>");
        sb.append("<th scope=\"col\">actions</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        int index1 = 1;
        for (Role role : roles) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(index1++).append("</th>");
            sb.append("<td>").append(role.getRole_id()).append("</td>");
            sb.append("<td>").append(role.getRole_name()).append("</td>");
            sb.append("<td>").append(role.getDescription()).append("</td>");
            sb.append("<td>").append(role.getStatus()).append("</td>");
            sb.append("<td>");
            sb.append("<button value=\"edit-Role\" onclick=\"setEditRoleActive()\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#addRoleModal\">edit</button>&nbsp");
            sb.append("<form action=\"ControlServlet\" method=\"post\" style=\"display:inline;\">");
            sb.append("<input type=\"hidden\" name=\"roleId\" value=\"").append(role.getRole_id()).append("\">");
            sb.append("<button type=\"submit\" name=\"action\" value=\"deleteRole\">delete</button>&nbsp");
            sb.append("</form>&nbsp;");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("<tfoot>");
        sb.append("<tr>");
        sb.append("<td colspan=\"8\">");
        sb.append("<button onclick=\"setAddRoleActive()\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#addRoleModal\">Add new Role</button>");
        sb.append("<br>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tfoot>");
        sb.append("</table>");
        return sb.toString();
    }
%>

<%!
    public String renderTableLog(List<Log> logs) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1 class=\"text-center\">Table Log</h1>");
        sb.append("<table class=\"table\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th scope=\"col\">numerical order</th>");
        sb.append("<th scope=\"col\">account_id</th>");
        sb.append("<th scope=\"col\">login_Time</th>");
        sb.append("<th scope=\"col\">logout_Time</th>");
        sb.append("<th scope=\"col\">notes</th>");
        sb.append("<th scope=\"col\">actions</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        int index2 = 1;
        for (Log log : logs) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(index2++).append("</th>");
            sb.append("<td>").append(log.getAccount_id()).append("</td>");
            sb.append("<td>").append(log.getLogin_date()).append("</td>");
            sb.append("<td>").append(log.getLogout_date()).append("</td>");
            sb.append("<td>").append(log.getDescription()).append("</td>");
            sb.append("<td>");
            sb.append("<form action=\"ControlServlet\" method=\"post\" style=\"display:inline;\">");
            sb.append("<input type=\"hidden\" name=\"loginTime\" value=\"").append(log.getLogin_date()).append("\">");
            sb.append("<button type=\"submit\" name=\"action\" value=\"deleteLog\">delete</button>");
            sb.append("</form>&nbsp;");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }
%>

<%
    printWriter.print(renderNavbar(accountFromLogin));
    printWriter.print(renderTableAccount(accounts));
    printWriter.print(renderTableRole(roles));
    printWriter.print(renderTableLog(logs));
%>
</body>
</html>


<script>
    function setEditAccountActive() {
        let modalLabel = document.getElementById('addAccountModalLabel');
        let submitButton = document.getElementById('modalSubmitButton');
        let accountId = document.getElementById('accountId');
        let password = document.getElementById('password');
        let email = document.getElementById('email');
        accountId.readOnly = true;
        password.readOnly = true;
        email.readOnly = true;
        modalLabel.innerText = 'Edit Account';
        submitButton.value = 'editAccount';
        submitButton.innerText = 'Save Changes';
        document.querySelectorAll('button[data-bs-target="#addAccountModal"]').forEach(button => {
            button.addEventListener('click', function() {
                let row = this.closest('tr');
                let accountId = row.cells[1].innerText;
                let fullName = row.cells[2].innerText;
                let password = row.cells[3].innerText;
                let email = row.cells[4].innerText;
                let phone = row.cells[5].innerText;
                let status = row.cells[6].innerText;
                document.getElementById('accountId').value = accountId;
                document.getElementById('fullName').value = fullName;
                document.getElementById('password').value = password;
                document.getElementById('email').value = email;
                document.getElementById('phone').value = phone;
                document.getElementById('status').value = status;
            });
        });
    }

    function setAddAccountActive() {
        let modalLabel = document.getElementById('addAccountModalLabel');
        let submitButton = document.getElementById('modalSubmitButton');
        let accountId = document.getElementById('accountId');
        let password = document.getElementById('password');
        let email = document.getElementById('email');
        accountId.disabled = false;
        password.disabled = false;
        email.disabled = false;
        modalLabel.innerText = 'Add Account';
        submitButton.value = 'addAccount';
        submitButton.innerText = 'Add';
    }

    function setEditRoleActive() {
        let modalLabel = document.getElementById('addRoleModalLabel');
        let submitButton = document.getElementById('roleSubmitButton');
        modalLabel.innerText = 'Edit Role';
        submitButton.value = 'editRole';
        submitButton.innerText = 'Save Changes';
        let roleId = document.getElementById('roleId');
        let roleName = document.getElementById('role_name')
        let Description =  document.getElementById('description')
        let Status = document.getElementById('role_status')
        document.querySelectorAll('button[data-bs-target="#addRoleModal"]').forEach(button => {
            button.addEventListener('click', function() {
                let row = this.closest('tr');
                let role_id = row.cells[1].innerText;
                let role_name = row.cells[2].innerText;
                let description = row.cells[3].innerText;
                let status = row.cells[4].innerText;
                roleId.value = role_id;
                roleName.value = role_name;
                Description.value = description;
                Status.value = status;
            });
        });
        roleId.readOnly = true;
    }

    function setAddRoleActive() {
        let modalLabel = document.getElementById('addRoleModalLabel');
        let submitButton = document.getElementById('roleSubmitButton');
        let roleId = document.getElementById('role_id');
        roleId.disabled = false;
        modalLabel.innerText = 'Add Role';
        submitButton.value = 'addRole';
        submitButton.innerText = 'Add';
    }
    function viewRole(button) {
        let row = button.closest('tr');
        let accountId = row.cells[1].innerText;
    }
    document.querySelectorAll('button[data-bs-target="#grantAccessModal"]').forEach(button => {
        button.addEventListener('click', function() {
            let row = this.closest('tr');
            let accountId = row.cells[1].innerText;
            document.getElementById('accountIdGrantAccess').value = accountId;
        });
    });
    document.querySelector('select[name="role"]').addEventListener('change', function() {
        let selectedRole = this.value;
        filterAccountsByRole(selectedRole);
    });

    function filterAccountsByRole(role) {
        let rows = document.querySelectorAll('table tbody tr');
        rows.forEach(row => {
            let accountRoles = row.cells[7].innerText.split(',').map(role => role.trim());
            if (role === 'all' || accountRoles.includes(role) || accountRoles.includes('administrator')) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    }
</script>
<!-- Account Modal -->
<div class="modal fade" id="addAccountModal" tabindex="-1" aria-labelledby="addAccountModalLabel" aria-hidden="true">
    <form id="addAccountForm" action="ControlServlet" method="post">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addAccountModalLabel">Edit Account</h5>
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
                            <option value="1">Enable</option>
                            <option value="0">Disable</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" id="modalSubmitButton" name="action"
                            value="addAccount">
                        Add
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- Role Modal -->
<div class="modal fade" id="addRoleModal" tabindex="-1" aria-labelledby="addRoleModalLabel" aria-hidden="true">
    <form id="addRoleForm" action="ControlServlet" method="post">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addRoleModalLabel">Add Role</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="roleId" class="form-label">Role Id</label>
                        <input type="text" class="form-control" id="roleId" name="roleId" required>
                    </div>
                    <div class="mb-3">
                        <label for="role_name" class="form-label">Role Name</label>
                        <input type="text" class="form-control" id="role_name" name="role_name" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" required>
                    </div>
                    <div class="mb-3">
                        <label for="role_status" class="form-label">Status</label>
                        <select class="form-control" name="status" id="role_status">
                            <option value="1">Enable</option>
                            <option value="0">Disable</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" id="roleSubmitButton" name="action" value="addRole">
                        Add
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- Grant_Access Modal -->
<div class="modal fade" id="grantAccessModal" tabindex="-1" aria-labelledby="grantAccessModalLabel" aria-hidden="true">
    <form id="grantAccessForm" action="ControlServlet" method="post">
        <input type="hidden" id="accountIdGrantAccess" name="accountId" value=""/>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="grantAccessModalLabel">Grant access to the account of your choice</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="notes" class="form-label">Note for grant access</label>
                        <input type="text" class="form-control" id="notes" name="notes" required>
                    </div>
                    <label for="role-list" class="form-label">Role</label>
                    <select class="form-control" name="roleId" id="role-list">
                        <% for (Role role : roles) { %>
                        <option value="<%=role.getRole_id()%>"><%=role.getRole_name()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" id="grantAccessSubmitButton" name="action" value="grantAccessRole">
                        Grant Access
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>


