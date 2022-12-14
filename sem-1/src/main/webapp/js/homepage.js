function changeBackground (e) {

    console.log(e.offsetX)

}

var background = document.getElementsByClassName('back-block')

background[0].addEventListener("mouseover", (e) => {
    $('body').css('background-image', 'url(../images/cat_1.jpg)')
    $('body').css('background-size', 'contain')
})

background[1].addEventListener("mouseover", (e) => {
    $('body').css('background-image', 'url(../images/dog_1.jpg)')
    $('body').css('background-size', 'contain')
})

background[2].addEventListener("mouseover", (e) => {
    $('body').css('background-image', 'url(../images/cat_2.jpg)')
    $('body').css('background-size', 'contain')
})

background[3].addEventListener("mouseover", (e) => {
    $('body').css('background-image', 'url(../images/dog_2.jpg)')
    $('body').css('background-size', '100%')
})

