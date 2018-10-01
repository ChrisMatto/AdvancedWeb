var pageEnum = Object.freeze({"listcorsi": "listcorsi", "home": "home", "insegnanti": "insegnanti"});

function bodychange(page){
    switch(page) {
        case pageEnum.listcorsi:
            
            $.ajax({
                url:'template/courses_list.html',
                dataType:'html',
                type:'GET',
                success:function(html){
                    $($('#body').replaceWith(html)).ready(function() {
                        $.ajax({
                            url:'http://localhost:8084/AdvancedWeb/rest/cdl',
                            dataType:'json',
                            type:'GET',
                            success:function(json){
                                var temp = $('#cdl').html();
                                json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                var newTemp = Mustache.render(temp,{'cdl' : json});
                                //$('#cdl').replaceWith(newTemp);
                                $('#cdl').html(newTemp);
                            $.ajax({
                            url: 'http://localhost:8084/AdvancedWeb/rest/cdlm',
                            dataType: 'json',
                            type: 'GET',
                            success: function(json) {
                                var temp = $('#cdlm').html();
                                console.log(temp);
                                json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                var newTemp = Mustache.render(temp,{'cdlm' : json});
                                //$('#cdlm').replaceWith(newTemp);
                                $('#cdlm').html(newTemp);
                            }
                    }); 
                        }
                    });
                        $.ajax({
                            url: 'http://localhost:8084/AdvancedWeb/rest/courses',
                            dataType: 'json',
                            type: 'GET',
                            success: function(json) {
                                if(json !== null) {
                                    json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                    for(i=0; i < json.length; i++) {
                                        var html = '<tr> \n\
                                                    <td><strong><a href="DetailsCorso?n =' + json[i].id + '&lin=it">' + json[i].nome_it + '</a></strong></td> \n\
                                                    <td>'+ json[i].ssd +'</td>\n\
                                                    <td>'+ json[i].cfu +'</td>\n\
                                                    <td>'+ json[i].lingua +'</td>\n\
                                                    <td>'+ json[i].semestre +'</td>\n\
                                                    <td>'+ json[i].tipologia +'</td>';
                                        $('#courses').append(html);
                                    }
                                }
                            }
                        });           
                });
                
                }
            });
            
            
            
            break;
        case pageEnum.home:
            location.reload();
        
    }
}
