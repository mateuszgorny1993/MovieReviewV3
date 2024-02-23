<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${movie.title}</title>
    <c:import url="head.jsp"></c:import>
</head>
<body class="recipes-section">
<c:import url="navbar.jsp"></c:import>
<section>
    <div class="row padding-small">
        <h1 align="center">${movie.title}</h1>
        <hr>
        <div class="orange-line w-100"></div>
    </div>
</section>
<section class="width-medium text-color-darker">
    <div class="pb-2">
        <div class="border-dashed view-height w-100">
            <div class="mt-4 ml-4 mr-4">
                <div class="row border-bottom border-3">
                    <div class="col"><h3 class="color-header text-uppercase">
                        Reżyseria: ${movie.director.firstName} ${movie.director.lastName}</h3></div>
                    <div class="col d-flex justify-content-end mb-2"><a href="/guest/movies"
                                                                        class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <table class="table borderless">
                    <tbody>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Kategorie</th>
                        <td class="col-7">
                            <c:forEach items="${movie.categories}" var="category">
                                <span>${category.name}</span><br/>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Opis filmu</th>
                        <td class="col-7">${movie.description}</td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Data premiery</th>
                        <td class="col-7">${movie.releaseDate}</td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Czas trwania</th>
                        <td class="col-7">${movie.duration} min</td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Nasza ocena</th>
                        <td class="col-7">${movie.rating}</td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Ocena OMDB</th>
                        <td class="col-7">${movie.ocenaOmdb}</td>
                    </tr>
                    </tbody>
                </table>

                <div class="row d-flex">
                    <div class="col-3 border-bottom border-3"><h3 class="text-uppercase">Plakat filmu</h3>
                        <img src="${movie.posterPath}" alt="Banner" style="width:80px;height:auto;">
                    </div>
                    <div class="col-3 border-bottom border-3"><h3 class="text-uppercase">Obsada filmu</h3>
                        <c:forEach items="${movie.actors}" var="actor">
                            <div>${actor.firstName} ${actor.lastName}</div>
                        </c:forEach>
                    </div>
                    <div class="col-6 border-bottom border-3"><h3 class="text-uppercase">Zwiastuny filmu</h3>
                        <c:forEach items="${movie.trailers}" var="trailer">
                            <iframe width="560" height="315" src="https://www.youtube.com/embed/${trailer.youtubeTrailerId}" frameborder="0" allowfullscreen></iframe>
                        </c:forEach>
                    </div>
                </div>
                <!-- Miejsce na podobne filmy, recenzje i komentarze -->
            </div>
        </div>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
