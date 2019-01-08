export default function filterTeachers() {
    var $rows = window.$('#table div');
    var val = window.$.trim(window.$('#ricerca').val()).replace(/ +/g, ' ').toLowerCase();

    $rows.show().filter(function() {
        var text = window.$(this).text().replace(/\s+/g, ' ').toLowerCase();
        return !~text.indexOf(val);
    }).hide();
}