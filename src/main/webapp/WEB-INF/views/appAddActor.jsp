<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
      <div class="border-dashed view-height w-100">
        <div class="mt-4 ml-4 mr-4">
          <h2>Dodaj Aktora</h2>
          <form:form modelAttribute="actor" action="/app/addActor" method="post">
            <div class="mb-3">
              <label for="firstName" class="form-label">Imię</label>
              <form:input path="firstName" id="firstName" class="form-control" required="true"/>
            </div>
            <div class="mb-3">
              <label for="lastName" class="form-label">Nazwisko</label>
              <form:input path="lastName" id="lastName" class="form-control" required="true"/>
            </div>
            <button type="submit" class="btn btn-primary">Dodaj Aktora</button>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</section>

<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
