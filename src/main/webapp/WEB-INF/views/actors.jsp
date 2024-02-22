<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="head.jsp"></c:import>
<body class="recipes-section">
<c:import url="navbar.jsp"></c:import>
<section>
    <div class="row padding-small">
        <i class="fas fa-users icon-users"></i>
        <h1>Aktorzy w naszym serwisie:</h1>
        <hr>
        <div class="orange-line w-100"></div>
    </div>
</section>

<section class="mr-4 ml-4">
    <div>
        <label for="sortOptions">Sortuj:</label>
        <select id="sortOptions" onchange="sortMovies(this.value)">
            <option value="?sortType=lastName">Alfabetycznie</option>
            <option value="?sortType=ratingDesc">Ocena (malejąco)</option>
            <option value="?sortType=viewsDesc">Wyświetlenia (malejąco)</option>
            <option value="?sortType=ratingAsc">Ocena (rosnąco)</option>
            <option value="?sortType=viewsAsc">Wyświetlenia (rosnąco)</option>
        </select>
    </div>
    <table class="table">
        <thead>
        <tr class="d-flex text-color-darker">
            <th scope="col" class="col-3">IMIĘ</th>
            <th scope="col" class="col-3">NAZWISKO</th>
            <th scope="col" class="col-2">OCENA</th>
            <th scope="col" class="col-2">WYŚWIETLENIA</th>
            <th scope="col" class="col-2">AKCJE</th>
        </tr>
        </thead>
        <tbody class="text-color-lighter">
        <c:forEach items="${actorsPage.content}" var="actor">
            <tr class="d-flex">
                <td class="col-3">${actor.firstName}</td>
                <td class="col-3">${actor.lastName}</td>
                <td class="col-2">${actor.rating}</td>
                <td class="col-2">${actor.views}</td>
                <td class="col-2"><a href="actors/details/${actor.id}" class="btn btn-info rounded-0 text-light">Szczegóły</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <c:forEach begin="0" end="${actorsPage.totalPages - 1}" var="i">
            <a href="?page=${i}&size=${actorsPage.size}&sortType=${param.sortType}">${i + 1}</a>
        </c:forEach>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
