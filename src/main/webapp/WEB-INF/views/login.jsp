<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand main-logo">MOVIE REVIEW</a>
        <!-- Navigation items -->
    </nav>
</header>
<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <div class="border-dashed view-height">
            <div class="container w-25">
                <!-- Dodanie action="/login" i method="post" do formularza -->
                <form class="padding-small text-center" action="${pageContext.request.contextPath}/login" method="post" modelAttribute="user">
                    <h1 class="text-color-darker">Logowanie</h1>
                    <!-- Ustawienie pola input dla emaila -->
                    <div class="form-group">
                        <input type="text" class="form-control" id="username" name="username" placeholder="Podaj adres email">
                    </div>
                    <!-- Zmiana typu input na password -->
                    <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Podaj hasło">
                    </div>
                    <!-- Dodanie przycisku do logowania -->
                    <button class="btn btn-color rounded-0" type="submit">Zaloguj</button>
                </form>
                <!-- Opcjonalnie: Dodanie komunikatu o błędzie logowania -->
                <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Niepoprawny login lub hasło.
                </div>
                <% } %>
            </div>
        </div>
    </div>
</section>

<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</html>