<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
  <nav class="navbar navbar-expand-lg justify-content-between">
    <a href="/app/dashboard" class="navbar-brand main-logo main-logo-smaller">
      Movie <span>Review</span>
    </a>
    <div class="d-flex justify-content-around">
      <h4 class="text-light mr-3">${username}</h4>
      <div class="circle-div text-center"><i class="fas fa-user icon-user"></i></div>
    </div>

  </nav>
</header>

<section class="dashboard-section">
  <c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
  </c:if>
  <div class="row dashboard-nowrap">
    <c:import url="sidebar.jsp"></c:import>
    <div class="m-4 p-3 width-medium">
      <div class="pb-2">
        <div class="border-dashed view-height w-100">
          <div class="mt-4 ml-4 mr-4">
            <div class="row border-bottom border-3">
              <div class="col"><h3 class="color-header text-uppercase">${movie.title}</h3></div>
              <div class="col d-flex justify-content-end mb-2">
                <a href="/app/movies" class="btn btn-color rounded-0 pt-0 pb-0 pr-2 pl-2">Powrót</a>
                <c:if test="${not empty errorMessage}">
                  <div class="alert alert-danger" role="alert">
                      ${errorMessage}
                  </div>
                </c:if>
                <form action="/app/ratemovie" method="POST" style="display: inline-block;">
                  <input type="hidden" name="movieId" value="${movie.id}"/>
                  <select class="form-select rounded-0 pt-0 pb-0 pr-2 pl-2" id="movieRating" name="rating" onsubmit="return validateForm()" style="width: auto; margin-right: 10px;">
                    <option selected>Twoja ocena...</option>
                    <option value="10">10</option>
                    <option value="9">9</option>
                    <option value="8">8</option>
                    <option value="7">7</option>
                    <option value="6">6</option>
                    <option value="5">5</option>
                    <option value="4">4</option>
                    <option value="3">3</option>
                    <option value="2">2</option>
                    <option value="1">1</option>
                    <option value="0">0</option>
                  </select>
                  <button type="submit" class="btn btn-primary rounded-0 pt-0 pb-0">Oceń</button>
                </form>
              </div>
            </div>
            <table class="table borderless">
              <tbody>
              <tr class="d-flex">
                <th scope="row" class="col-2">Reżyseria</th>
                <td class="col-7">${movie.director.firstName} ${movie.director.lastName}</td>
              </tr>
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
                  <a href="/app/actors/details/${actor.id}">
                    <div>${actor.firstName} ${actor.lastName}</div>
                  </a>
                </c:forEach>
              </div>
              <div class="col-6 border-bottom border-3"><h3 class="text-uppercase">Zwiastuny filmu</h3>
                <c:forEach items="${movie.trailers}" var="trailer">
                  <iframe width="560" height="315"
                          src="https://www.youtube.com/embed/${trailer.youtubeTrailerId}" frameborder="0"
                          allowfullscreen></iframe>
                </c:forEach>
              </div>
            </div>
            <div class="row d-flex">
              <div class="col-12 border-bottom border-3"><h3 class="text-uppercase">Podobne filmy</h3>
                <table class="table">
                  <thead>
                  <tr class="d-flex text-color-darker">
                    <th scope="col" class="col-3">TYTUŁ</th>
                    <th scope="col" class="col-3">OPIS</th>
                    <th scope="col" class="col-2">OCENA</th>
                    <th scope="col" class="col-2">WYŚWIETLENIA</th>
                    <th scope="col" class="col-2">SZCZEGÓŁY</th>
                  </tr>
                  </thead>
                  <c:if test="${not empty similarMovies}">
                    <tbody class="text-color-lighter">
                    <c:forEach items="${similarMovies}" var="similarMovie">
                      <tr class="d-flex">
                        <td class="col-3">${similarMovie.title}</td>
                        <td class="col-3">${similarMovie.description}</td>
                        <td class="col-2">${similarMovie.rating}</td>
                        <td class="col-2">${similarMovie.views}</td>
                        <td class="col-2"><a href="/app/movies/details/${similarMovie.id}"
                                             class="btn btn-info rounded-0 text-light">Szczegóły</a>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </c:if>
                </table>
              </div>
            </div>
            <!-- Miejsce na podobne filmy, recenzje i komentarze -->
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
