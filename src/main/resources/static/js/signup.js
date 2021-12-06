$(document).ready(function () {
    console.log("Document ready")
    $("#signup_form").submit(function (event) {
            $(".jquery-feedback").css("display", "none")

            let isValid = true

            if ($("#signup_username").val().length < 5) {
                isValid = false;
                $("#signup_username_feedback").css("display", "block")
                $("#signup_username_feedback").text("Le nom d'utilisaeur doit contenir au moins 5 caractères")
            }
            if (!/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/.test($("#signup_password").val())) {
                isValid = false;
                $("#signup_password_feedback").css("display", "block")
                $("#signup_password_feedback").text("Le mot de passe doit être composé d'un caractère spécial, un chiffre, une lettre, une maj et avoir plus de 8 caractères")
            }
            if($("#signup_password").val() !== $("#signup_password_repeat").val()) {
                isValid = false;
                $("#signup_password_repeat_feedback").css("display", "block")
                $("#signup_password_repeat_feedback").text("Les 2 mots de passe ne sont pas identiques !")

            }
            if(!isValid){
                event.preventDefault()
            }
        }
    )
})