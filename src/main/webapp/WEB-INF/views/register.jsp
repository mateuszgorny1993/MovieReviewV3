<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <form:form class="padding-small text-center" action="${pageContext.request.contextPath}/register" method="POST" modelAttribute="registrationForm">
                    <h1 class="text-color-darker">Rejestracja</h1>
                    <c:if test="${!empty errorMessage}">
                        <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                        </div>
                    </c:if>
                    <!-- Username -->
                    <div class="form-group">
                        <form:input type="text" path="username" class="form-control" placeholder="Podaj username" required="true"/>
                        <form:errors path="username" class="text-danger" />
                    </div>
                    <!-- First Name -->
                    <div class="form-group">
                        <form:input type="text" path="firstname" class="form-control" placeholder="Podaj imię" required="true"/>
                        <form:errors path="firstname" class="text-danger" />
                    </div>
                    <!-- Last Name -->
                    <div class="form-group">
                        <form:input type="text" path="lastname" class="form-control" placeholder="Podaj nazwisko" required="true"/>
                        <form:errors path="lastname" class="text-danger" />
                    </div>
                    <!-- Email -->
                    <div class="form-group">
                        <form:input type="email" path="email" class="form-control" placeholder="Podaj email" required="true"/>
                        <form:errors path="email" class="text-danger" />
                    </div>
                    <!-- Password -->
                    <div class="form-group">
                        <form:password path="password" class="form-control" placeholder="Podaj hasło" id="password" onkeyup="checkPasswordStrength();" required="true"/>
                        <div id="password-strength-status" class="progress" style="margin-top: 10px;"></div>
                        <div id="password-strength-text" style="text-align: center; margin-top: 10px;"></div>
                        <span toggle="#password" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                        <form:errors path="password" class="text-danger" />
                    </div>

                    <!-- Notifications -->
                    <div class="form-group form-check">
                        <form:checkbox path="notifications" class="form-check-input" />
                        <form:label path="notifications" class="form-check-label">Chcę otrzymywać powiadomienia</form:label>
                    </div>
                    <button class="btn btn-color rounded-0" type="submit">Zarejestruj</button>
                </form:form>
            </div>
        </div>
    </div>
</section>
<c:import url="footer.jsp"></c:import>
<c:import url="script.jsp"></c:import>

</body>
</html>
