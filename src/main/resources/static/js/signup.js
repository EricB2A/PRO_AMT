$(document).ready(function () {
    console.log("Document ready")
    $("#signup_form").submit(function (event) {
        //event.preventDefault()
        console.log("SIGN UP BUTTON CLICKED")

        console.log("INPUT :", $(".signup-input"))
        $(".signup-input").each(function (_, value) {
            console.log("is empty = ",$(value).val());
        })
    })
})