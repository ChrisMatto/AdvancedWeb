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
                    var corsiTemp;
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
                               console.log("cdlTemp")
                           },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
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
                               console.log("cdlmTemp");
                           },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
                           }
                        }),
                        
                        $.ajax({
                            url: 'http://localhost:8084/AdvancedWeb/rest/courses',
                            dataType: 'json',
                            type: 'GET',
                            success: function(json) {
                                var temp = $('#courses_script', html).html();
                                Mustache.parse(temp);
                                json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                corsiTemp = Mustache.to_html(temp, {corso: json});
                                console.log("corsiTemp");
                            },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
                           }
                        }),
                    ).then(function() {
                        $('#cdl', newHtml).html(cdlTemp);
                        $('#cdlm', newHtml).html(cdlmTemp);
                        $('#courses', newHtml).html(corsiTemp);
                        $('#body').replaceWith(newHtml);
                    });
                }
            });
            
            
            
            break;
        case pageEnum.home:
            location.reload();
        
    }
}