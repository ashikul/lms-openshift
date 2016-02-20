/**
 * Created by Ashikul on 2/19/2016.
 */
$(document).ready(function () {
    $('ul.nav > li').click(function (e) {
        //e.preventDefault();
        //$('ul.nav > li').removeClass('active');
        $(this).addClass('active');
    });
});