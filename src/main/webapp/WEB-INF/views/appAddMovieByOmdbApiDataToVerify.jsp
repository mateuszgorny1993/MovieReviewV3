<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-between">
        <a href="/dashboard" class="navbar-brand main-logo main-logo-smaller">Movie <span>Review</span></a>
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
            <div class="pb-2">
                <div class="border-dashed view-height w-100">
                    <div class="mt-4 ml-4 mr-4">
                        <div class="row">
                            <div class="col"><h3 class="color-header text-uppercase">Szczegóły Filmu Pobrane z IMDB</h3>
                            </div>
                            <div class="col d-flex justify-content-end"><a href="/app/addMovie" class="btn btn-color rounded-0">Powrót</a>
                            </div>
                        </div>

                        <table class="table borderless">
                            <tbody>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Tytuł:</th>
                                <td class="col-10">${movieDetails.title}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Reżyser:</th>
                                <td class="col-10">${movieDetails.director}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Kategorie:</th>
                                <td class="col-10">${movieDetails.categories}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Opis:</th>
                                <td class="col-10">${movieDetails.description}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Data Premiery:</th>
                                <td class="col-10">${movieDetails.releaseDate}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Czas Trwania:</th>
                                <td class="col-10">${movieDetails.duration}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Ocena OMDB:</th>
                                <td class="col-10">${movieDetails.ocenaOmdb}</td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Plakat:</th>
                                <td class="col-10"><img src="${movieDetails.posterPath}" alt="Poster"
                                                        style="width:100px;height:auto;"></td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Aktorzy:</th>
                                <td class="col-10">${movieDetails.actors}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <form action="/app/addMovie/addByOmdbApiAdd" method="post">
                        <input type="hidden" name="imdbId" value="${movieDetails.imdbId}">
                        <input type="hidden" name="title" value="${movieDetails.title}"/>
                        <input type="hidden" name="director" value="${movieDetails.director}"/>
                        <input type="hidden" name="categories" value="${movieDetails.categories}"/>
                        <input type="hidden" name="description" value="${movieDetails.description}"/>
                        <input type="hidden" name="releaseDate" value="${movieDetails.releaseDate}"/>
                        <input type="hidden" name="duration" value="${movieDetails.duration}"/>
                        <input type="hidden" name="ocenaOmdb" value="${movieDetails.ocenaOmdb}"/>
                        <input type="hidden" name="posterPath" value="${movieDetails.posterPath}"/>
                        <input type="hidden" name="actors" value="${movieDetails.actors}"/>
                        <div class="row mt-4">
                            <div class="col text-right">
                                <button type="submit" class="btn btn-success">Zatwierdź i dodaj film</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
