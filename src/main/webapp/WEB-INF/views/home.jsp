<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>MOVIE-REVIEW</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" crossorigin="anonymous">
</head>
<body>
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
        </ul>
    </nav>
</header>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 bg-light sidebar">
            <section class="dashboard-section">
                <div class="row dashboard-nowrap">
                    <ul class="nav flex-column long-bg">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">
                                <span>Logowanie</span>
                                <i class="fas fa-angle-right"></i>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/register">
                                <span>Rejestracja</span>
                                <i class="fas fa-angle-right"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </section>
        </div>
        <div class="col-md-10">
            <!-- Treść główna strony -->
        </div>
    </div>
</div>

<footer class="footer-section pt-3 pb-3">
    <div class="container text-center">
        <h5 class="text-light">Copyright &copy; <span class="footer-text-color">moviereview.pl</span></h5>
    </div>
</footer>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
