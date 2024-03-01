<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-between">
        <a href="/dashboard" class="navbar-brand main-logo main-logo-smaller">
            Movie <span>Review</span>
        </a>
        <div class="d-flex justify-content-around">
            <h4 class="text-light mr-3">${username}</h4>
            <div class="circle-div text-center"><i class="fas fa-user icon-user"></i></div>
        </div>

    </nav>
</header>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <c:import url="sidebar.jsp"></c:import>
        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Lista Filmów</h3></div>
                    <div class="col noPadding d-flex justify-content-end mb-2"><a href="/app/addMovie" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Dodaj film</a></div>
                </div>
                <table class="table">
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

            </div>
        </div>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
