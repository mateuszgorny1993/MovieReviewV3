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
              <div class="col"><h3 class="color-header text-uppercase">Szczegóły reżysera</h3></div>
              <div class="col d-flex justify-content-end mb-2">
                <a href="/app/directors" class="btn btn-color rounded-0 pt-0 pb-0 pr-2 pl-2">Powrót</a>
                <c:if test="${not empty errorMessage}">
                  <div class="alert alert-danger" role="alert">
                      ${errorMessage}
                  </div>
                </c:if>
                <form action="/app/ratedirector" method="POST" style="display: inline-block;">
                  <input type="hidden" name="directorId" value="${director.id}"/>
                  <select class="form-select rounded-0 pt-0 pb-0 pr-2 pl-2" id="directorRating" name="rating"
                          onsubmit="return validateForm()" style="width: auto; margin-right: 10px;">
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
                <th scope="row" class="col-2">Imię:</th>
                <td class="col-7">${director.firstName}</td>
              </tr>
              <tr class="d-flex">
                <th scope="row" class="col-2">Nazwisko:</th>
                <td class="col-7">${director.lastName}</td>
              </tr>
              <tr class="d-flex">
                <th scope="row" class="col-2">Ocena:</th>
                <td class="col-7">${director.rating}</td>
              </tr>
              <tr class="d-flex">
                <th scope="row" class="col-2">Ilość wyświetleń:</th>
                <td class="col-7">${director.views}</td>
              </tr>
              <tr class="d-flex">
                <th scope="row" class="col-2">Filmy reżyserowane:</th>
                <td class="col-7">
                  <ul>
                    <c:forEach items="${director.movies}" var="movie">
                      <li><a href="/app/movies/details/${movie.id}">${movie.title}</a></li>
                    </c:forEach>
                  </ul>
                </td>
              </tr>
              </tbody>
            </table>

            <div class="row d-flex">
              <div class="col-12 border-bottom border-3">
                <h3 class="text-uppercase">Powiązani aktorzy</h3>
                <ul>
                  <c:forEach items="${relatedActors}" var="relatedActor">
                    <li>
                      <a href="/app/actors/details/${relatedActor.id}">
                          ${relatedActor.firstName} ${relatedActor.lastName}
                      </a>
                    </li>
                  </c:forEach>
                </ul>
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
