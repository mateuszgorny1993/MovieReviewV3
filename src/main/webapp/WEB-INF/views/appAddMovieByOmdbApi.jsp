<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <c:import url="head.jsp"></c:import>
</head>
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
      <div class="pb-2">
        <div class="border-dashed view-height w-100">
          <div class="mt-4 ml-4 mr-4">
            <div class="row border-bottom border-3">
              <div class="col d-flex justify-content-end mb-2">
                <a href="/app/addMovie" class="btn btn-color rounded-0 pt-0 pb-0 pr-2 pl-2">Powrót</a>
              </div>
            </div>
            <form action="/app/addMovie/addByOmdbApi" method="post" class="mt-4">
              <div class="form-group">
                <label for="imdbId">ID filmu z IMDB:</label>
                <input type="text" class="form-control" id="imdbId" name="imdbId" placeholder="np. tt0111161" required>
              </div>
              <button type="submit" class="btn btn-primary">Pobierz film z serwisu IMDB</button>
            </form>
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
