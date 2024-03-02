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
    <c:import url="adminsidebar.jsp"></c:import>
    <div class="m-4 p-4 width-medium">
      <div class="dashboard-header m-4">
        <div class="dashboard-menu">
          <div class="menu-item border-dashed">
          </div>
          <div class="menu-item border-dashed">
          </div>
          <div class="menu-item border-dashed">
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