var pageEnum = Object.freeze({"listcorsi": "listcorsi", "detailscorso": "detailscorso", "home": "home", "docenti": "docenti"});

function bodychange(page, id = -1){
    switch(page) {
        case pageEnum.listcorsi:
            var newHtml;
            var corsiUrl;
            if(id === -1) {
                corsiUrl = 'http://localhost:8080/AdvancedWeb/rest/courses/current';
            } else {
                corsiUrl = 'http://localhost:8080/AdvancedWeb/rest/courses/current?cdl=' + id;
            }
            $.ajax({
                url:'template/courses_list.html',
                dataType:'html',
                type:'GET',
                success:function(html){
                    newHtml = $(html);
                    var cdlTemp;
                    var cdlmTemp;
                    var corsiTemp;
                    $.when(
                        $.ajax({
                           url: 'http://localhost:8080/AdvancedWeb/rest/cdl/triennale',
                           dataType: 'json',
                           type: 'GET',
                           success: function(json) {
                               var temp = $('#cdl', html).html();
                               Mustache.parse(temp);
                               json.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
                               cdlTemp = Mustache.to_html(temp, {cdl: json});
                               console.log("cdlTemp")
                           },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
                           }
                        }),
                        
                        $.ajax({
                           url: 'http://localhost:8080/AdvancedWeb/rest/cdl/magistrale',
                           dataType: 'json',
                           type: 'GET',
                           success: function(json) {
                               var temp = $('#cdlm', html).html();
                               Mustache.parse(temp);
                               json.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
                               cdlmTemp = Mustache.to_html(temp, {cdlm: json});
                               console.log("cdlmTemp");
                           },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
                           }
                        }),
                        
                        $.ajax({
                            url: corsiUrl,
                            dataType: 'json',
                            type: 'GET',
                            success: function(json) {
                                var temp = $('#courses_script', html).html();
                                Mustache.parse(temp);
                                json.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
                                for(var corso in json) {
                                    for(var i = 0; i < json[corso]["docenti"].length; i++) {
                                        if(json[corso]["docenti"][i + 1] != null) {
                                            json[corso]["docenti"][i]["hasNext"] = true;
                                        }
                                    }
                                    for(var i = 0; i < json[corso]["cdl"].length; i++) {
                                        if(json[corso]["cdl"][i + 1] != null) {
                                            json[corso]["cdl"][i]["hasNext"] = true;
                                        }
                                    }
                                }
                                corsiTemp = Mustache.to_html(temp, {corso: json});
                                console.log("corsiTemp");
                            },
                           error : function(xhr, textStatus, errorThrown ) {
                              console.log(textStatus); 
                              console.log(xhr);
                              console.log(errorThrown);
                           }
                        })
                    ).then(function() {
                        document.title = "Lista Corsi";
                        $('#cdl', newHtml).html(cdlTemp);
                        $('#cdlm', newHtml).html(cdlmTemp);
                        $('#courses', newHtml).html(corsiTemp);
                        $('#body').html(newHtml);
                    }).fail(function() {
                        if(cdlTemp === undefined)
                            $('#cdl', newHtml).empty();
                        else
                            $('#cdl', newHtml).html(cdlTemp);
                        if(cdlmTemp === undefined)
                            $('#cdlm', newHtml).empty();
                        else
                            $('#cdlm', newHtml).html(cdlmTemp);
                        if(corsiTemp === undefined)
                            $('#courses', newHtml).empty();
                        else
                            $('#courses', newHtml).html(corsiTemp);
                        $('#body').html(newHtml);
                    });
                }
            });
            break;
            case pageEnum.detailcorso:
            break;
            
        case pageEnum.home:
            location.reload();
            break;  
    }
}
