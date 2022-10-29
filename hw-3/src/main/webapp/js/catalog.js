var filterView = false;
$('.filter-block').hide()
$('.pelena').hide()

$('.filter-button').click(function () {
    if (filterView) {

        $('.filter-block').hide()
        $('.pelena').hide()
        filterView = false;
    } else {
        $('.filter-block').show()
        $('.pelena').show()
        filterView = true;
    }
})

$('.pelena').click( function () {
    if (filterView) {
        $('.filter-block').hide()
        $('.pelena').hide()
        filterView = false;
    }
})

$('.message-block').hide().show(1000).fadeOut(6000)

