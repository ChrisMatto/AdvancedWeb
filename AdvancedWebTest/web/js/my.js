/*$( '#ricerca' ).on( 'click', '.p-title', function( e )
{
    e.preventDefault();
    $( this ).parents( 'div' ).toggleClass( 'is-active' );
});*/


var $rows = $('#table div');
$('#ricerca').keyup(function() {
    var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();
    
    $rows.show().filter(function() {
        var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
        return !~text.indexOf(val);
    }).hide();
});