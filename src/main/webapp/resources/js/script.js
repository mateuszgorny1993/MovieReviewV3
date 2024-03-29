
document.addEventListener('DOMContentLoaded', function() {
    // Pobranie bieżącego parametru sortowania z localStorage
    var currentSortType = localStorage.getItem('currentSortType');
    // Jeśli istnieje bieżący parametr sortowania, ustawiamy wartość wybraną w rozwijanym menu na podstawie tego parametru
    if (currentSortType) {
        document.getElementById('sortOptions').value = currentSortType;
    }
});

function sortMovies(sortType) {
    // Zapisanie bieżącego parametru sortowania do localStorage
    localStorage.setItem('currentSortType', sortType);
    // Przekierowanie do bieżącego URL z dodanym parametrem sortowania
    window.location.href = window.location.pathname + sortType;
}

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
function validateForm() {
    var ratingValue = document.getElementById("movieRating").value;
    if (ratingValue === "Twoja ocena...") { // Załóżmy, że "Twoja ocena..." to wartość domyślna
        alert("Proszę wybrać ocenę.");
        return false;
    }
    return true;
}

document.addEventListener("DOMContentLoaded", function () {
    $(".toggle-password").click(function () {
        var input = $($(this).attr("toggle"));
        // Sprawdzenie typu inputa i zmiana typu
        if (input.attr("type") === "password") {
            input.attr("type", "text");
            $(this).addClass("fa-eye-slash");
            $(this).removeClass("fa-eye");
        } else {
            input.attr("type", "password");
            $(this).removeClass("fa-eye-slash");
            $(this).addClass("fa-eye");
        }
    });
});