<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>MOVIE REVIEW</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
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
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" crossorigin="anonymous"></script>
<script>
    function checkPasswordStrength() {
        var strengthStatus = document.getElementById('password-strength-status');
        var strengthText = document.getElementById('password-strength-text');
        var strongRegex = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})');
        var mediumRegex = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})');
        var password = document.getElementById('password').value;

        if(strongRegex.test(password)) {
            strengthStatus.innerHTML = '<div class="progress-bar strength-strong" style="width: 100%">Silne</div>';
            strengthText.innerHTML = "Twoje hasło jest silne.";
        } else if(mediumRegex.test(password)) {
            strengthStatus.innerHTML = '<div class="progress-bar strength-medium" style="width: 70%">Średnie</div>';
            strengthText.innerHTML = "Twoje hasło jest średnie.";
        } else {
            strengthStatus.innerHTML = '<div class="progress-bar strength-weak" style="width: 40%">Słabe</div>';
            strengthText.innerHTML = "Twoje hasło jest słabe.";
        }
    }
</script>

</body>
</html>
