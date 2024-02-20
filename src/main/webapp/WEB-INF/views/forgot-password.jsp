<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="head.jsp"></c:import>
<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand main-logo">MOVIE REVIEW</a>
        <!-- Navigation items -->
    </nav>
</header>
<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <div class="border-dashed view-height">
            <div class="container w-25">
                <form:form class="padding-small text-center" action="${pageContext.request.contextPath}/forgot-password" method="post">
                    <h1 class="text-color-darker">Zapomniałem hasła</h1>
                    <div class="form-group">
                        <input type="email" class="form-control" id="email" name="email" placeholder="Wpisz swój adres email" required>
                    </div>
                    <button class="btn btn-color rounded-0" type="submit">Wyślij link do resetowania hasła</button>
                </form:form>
            </div>
        </div>
    </div>
</section>

<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>
</body>
</html>
