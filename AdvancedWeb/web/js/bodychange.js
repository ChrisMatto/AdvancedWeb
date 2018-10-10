var pageEnum = Object.freeze({"listcorsi": "listcorsi", "home": "home", "insegnanti": "insegnanti"});

function bodychange(page){
    switch(page) {
        case pageEnum.listcorsi:
            var newHtml;
            $.ajax({
                url:'template/courses_list.html',
                dataType:'html',
                type:'GET',
                success:function(html){
                    //$($('#body').replaceWith(html)).ready(function() {
                    newHtml = $(html);
                    var cdlTemp;
                    var cdlmTemp;
                    $.when(
                        $.ajax({
                           url: 'http://localhost:8084/AdvancedWeb/rest/cdl',
                           dataType: 'json',
                           type: 'GET',
                           success: function(json) {
                               var temp = $('#cdl', html).html();
                               Mustache.parse(temp);
                               json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                               cdlTemp = Mustache.to_html(temp, {cdl: json});
                               //$('#cdl', newHtml).html(newTemp);
                           }
                        }),
                        
                        $.ajax({
                           url: 'http://localhost:8084/AdvancedWeb/rest/cdlm',
                           dataType: 'json',
                           type: 'GET',
                           success: function(json) {
                               var temp = $('#cdlm', html).html();
                               Mustache.parse(temp);
                               json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                               cdlmTemp = Mustache.to_html(temp, {cdlm: json});
                               //$('#cdlm', newHtml).html(newTemp);
                               //console.log(newHtml);
                           }
                        }),
                    ).then(function() {
                        $('#cdl', newHtml).html(cdlTemp);
                        $('#cdlm', newHtml).html(cdlmTemp);
                        $('#body').replaceWith(newHtml);
                    });
                }
            });
            
            
            
            break;
        case pageEnum.home:
            location.reload();
        
    }
}