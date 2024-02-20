<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand main-logo">MOVIE REVIEW</a>
        <ul class="nav nounderline text-uppercase">
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/">Home</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/movies">Filmy</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/new">Nowości</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/actors">Aktorzy</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/directors">Reżyserzy</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/quiz">Quiz</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/login">Logowanie</a></li>
            <li class="nav-item ml-4"><a class="nav-link" href="${pageContext.request.contextPath}/register">Rejestracja</a></li>
        </ul>
    </nav>
</header>