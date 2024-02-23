<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Nadchodzące Premiery</title>
  <c:import url="head.jsp"></c:import>
</head>
<body class="recipes-section">
<c:import url="navbar.jsp"></c:import>
<section>
  <div class="row padding-small">
    <h1>Nadchodzące Premiery Filmowe</h1>
    <hr>
    <div class="orange-line w-100"></div>
  </div>
</section>

<section class="mr-4 ml-4">
  <select id="sortOptions" onchange="sortMovies(this.value)">
    <option value="?sortType=title">Alfabetycznie</option>
    <option value="?sortType=releaseDateDesc">Data premiery (malejąco)</option>
    <option value="?sortType=releaseDateAsc">Data premiery (rosnąco)</option>
  </select>
  <table class="table">
    <thead>
    <tr class="d-flex text-color-darker">
      <th scope="col" class="col-2">BANNER</th>
      <th scope="col" class="col-4">TYTUŁ</th>
      <th scope="col" class="col-4">OPIS</th>
      <th scope="col" class="col-2">DATA PREMIERY</th>
    </tr>
    </thead>
    <tbody class="text-color-lighter">
    <c:forEach items="${newReleasesPage.content}" var="movie">
      <tr class="d-flex">
        <td class="col-2"><img src="${movie.posterPath}" alt="Banner" style="width:80px;height:auto;"></td>
        <td class="col-4">${movie.title}</td>
        <td class="col-4">${movie.description}</td>
        <td class="col-2">${movie.releaseDate}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div>
    <c:forEach begin="0" end="${newReleasesPage.totalPages - 1}" var="i">
      <a href="?page=${i}&size=${newReleasesPage.size}&sortType=${param.sortType}">${i + 1}</a>
    </c:forEach>
  </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
