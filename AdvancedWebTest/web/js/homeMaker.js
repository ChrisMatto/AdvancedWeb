function homeMaker(){
    $.ajax({
        url:'http://localhost:8080/AdvancedWeb/rest/cdl/random',
        dataType: 'json',
        type:'GET',
        success: function(json){
            var template=$('#cdl').html();
            var newTemp=Mustache.render(template,{'cdl':json});
            $('#cdl').replaceWith(newTemp);
            
            $.ajax({
                url:'http://localhost:8080/AdvancedWeb/rest/cdlm/random',
                dataType: 'json',
                type:'GET',
                success: function(json){
                    var template=$('#cdlm').html();
                    var newTemp=Mustache.render(template,{'cdlm':json});
                    $('#cdlm').replaceWith(newTemp);
                }
            });
        }
    });

}