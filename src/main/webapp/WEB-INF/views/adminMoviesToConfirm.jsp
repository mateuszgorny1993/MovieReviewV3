<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
  <nav class="navbar navbar-expand-lg justify-content-between">
    <a href="/app/admin/dashboard" class="navbar-brand main-logo main-logo-smaller">
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
    <c:import url="adminsidebar.jsp"></c:import>
    <div class="m-4 p-3 width-medium">
      <div class="dashboard-content border-dashed p-3 m-4 view-height">
        <div class="row border-bottom border-3 p-1 m-1">
          <div class="col noPadding"><h3 class="color-header text-uppercase">Lista Filmów</h3></div>
        </div>
        <table class="table">
          <thead>
          <tr class="d-flex text-color-darker">
            <th scope="col" class="col-3">TYTUŁ</th>
            <th scope="col" class="col-2">OPIS</th>
            <th scope="col" class="col-2">AKCJE</th>
          </tr>
          </thead>
          <tbody class="text-color-lighter">
          <c:forEach var="movie" items="${movies}">
            <tr class="d-flex">
              <td class="col-3">${movie.title}</td>
              <td class="col-2">${movie.description}</td>
              <td class="col-2"><a href="/app/admin/confirmMovie/${movie.id}" class="btn btn-info rounded-0 text-light">Zatwierdź</a></td>
              <td class="col-2"><a href="/app/admin/deleteMovie/${movie.id}" class="btn btn-info rounded-0 text-light">Usuń</a></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
