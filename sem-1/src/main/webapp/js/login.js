var flagSignup = false;


$('.signup-text').click(function () {
    if (!flagSignup) {
        $('.fix-block').animate({
            opacity: '1'
        }, 1000)
        $('.login-form').animate({
            height: "0px"
        }, 1000, function () {
            $('.login-form').hide()
        })
        flagSignup = true;
    } else {

        $('.fix-block').animate({
            opacity: '0'
        }, 1000)
        $('.login-form').show()
        $('.login-form').animate({
            height: "80%"
        }, 1000)
        flagSignup = false;
    }

})