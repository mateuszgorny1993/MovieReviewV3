<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Filmy</title>
    <c:import url="head.jsp"></c:import>
</head>
<body class="recipes-section">
<c:import url="navbar.jsp"></c:import>
<section>
    <div class="row padding-small">
        <i class="fas fa-users icon-users"></i>
        <h1>Filmy w naszym serwisie:</h1>
        <hr>
        <div class="orange-line w-100"></div>
    </div>
</section>

<section class="mr-4 ml-4">
    <div>
        <label for="sortOptions">Sortuj:</label>
        <select id="sortOptions" onchange="sortMovies(this.value)">
            <option value="?sortType=title">Alfabetycznie</option>
            <option value="?sortType=ratingDesc">Ocena (malejąco)</option>
            <option value="?sortType=viewsDesc">Wyświetlenia (malejąco)</option>
            <option value="?sortType=ratingAsc">Ocena (rosnąco)</option>
            <option value="?sortType=viewsAsc">Wyświetlenia (rosnąco)</option>
        </select>
    </div>
    <table class="table">
        <thead>
        <tr class="d-flex text-color-darker">
            <th scope="col" class="col-2">BANNER</th>
            <th scope="col" class="col-3">TYTUŁ</th>
            <th scope="col" class="col-2">OPIS</th>
            <th scope="col" class="col-1">OCENA</th>
            <th scope="col" class="col-1">WYŚWIETLENIA</th>
            <th scope="col" class="col-2">AKCJE</th>
        </tr>
        </thead>
        <tbody class="text-color-lighter">
        <c:forEach items="${moviesPage.content}" var="movie">
            <tr class="d-flex">
                <td class="col-2"><img src="${movie.posterPath}" alt="Banner" style="width:80px;height:auto;"></td>
                <td class="col-3">${movie.title}</td>
                <td class="col-2">${movie.description}</td>
                <td class="col-1">${movie.rating}</td>
                <td class="col-1">${movie.views}</td>
                <td class="col-2"><a href="movies/details/${movie.id}" class="btn btn-info rounded-0 text-light">Szczegóły</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <c:forEach begin="0" end="${moviesPage.totalPages - 1}" var="i">
            <a href="?page=${i}&size=${moviesPage.size}&sortType=${param.sortType}">${i + 1}</a>
        </c:forEach>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
