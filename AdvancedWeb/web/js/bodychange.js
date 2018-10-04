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
                                $('#cdl').html(newTemp);
                            $.ajax({
                            url: 'http://localhost:8084/AdvancedWeb/rest/cdlm',
                            dataType: 'json',
                            type: 'GET',
                            success: function(json) {
                                var temp = $('#cdlm').html();
                                json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                var newTemp = Mustache.render(temp,{'cdlm' : json});
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
                                    $('#courses').empty();
                                    json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                                    for(i=0; i < json.length; i++) {
                                        var row = document.createElement("tr");
                                        var cell = document.createElement("td");
                                        var strong = document.createElement("strong");
                                        var a = document.createElement("a");
                                        var text = document.createTextNode(json[i].nome_it);
                                        a.appendChild(text);
                                        strong.appendChild(a);
                                        cell.appendChild(strong);
                                        row.appendChild(cell);
                                        cell = document.createElement("td");
                                        text = document.createTextNode(json[i].ssd);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        cell = document.createElement("td");
                                        text = document.createTextNode(json[i].cfu);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        cell = document.createElement("td");
                                        text = document.createTextNode(json[i].lingua);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        cell = document.createElement("td");
                                        text = document.createTextNode(json[i].semestre);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        cell = document.createElement("td");
                                        text = document.createTextNode(json[i].tipologia);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        row.appendChild(document.createElement("td"));
                                        row.appendChild(document.createElement("td"));
                                        $('#courses').append(row);
                                        getCDL(i,json[i].id);
                                        /*var post = new XMLHttpRequest();
                                        post.open('POST', 'http://localhost:8084/AdvancedWeb/rest/courses/cdl', false);
                                        post.send(JSON.stringify({'id': json[i].id}));
                                        var cdlText = "";
                                        var cdl = JSON.parse(post.responseText);
                                        for(j=0; j < cdl.length; j++) {
                                            var abbr;
                                            if(cdl[j].abbr_it === null || cdl[j].abbr_it === undefined || cdl[j].abbr_it === "")
                                                abbr = cdl[j].abbr_en;
                                            else 
                                                abbr = cdl[j].abbr_it;
                                            cdl += abbr;
                                            if(j !== cdl.length - 1)
                                                cdlText += ",  ";
                                        }
                                        cell = document.createElement("td");
                                        text = document.createTextNode(cdlText);
                                        cell.appendChild(text);
                                        row.appendChild(cell);
                                        $("#courses").append(row);*/
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

function getCDL(index, id) {
    $.ajax({
        url: 'http://localhost:8084/AdvancedWeb/rest/courses/cdl',
        dataType: 'json',
        type: 'POST',
        data: JSON.stringify({'id': id}),
        success: function(cdl) {
            var column = 6;
            var cdlText = "";
            for(j=0; j < cdl.length; j++) {
                var abbr;
                if(cdl[j].abbr_it === null || cdl[j].abbr_it === undefined || cdl[j].abbr_it === "")
                    abbr = cdl[j].abbr_en;
                else 
                    abbr = cdl[j].abbr_it;
                cdlText += abbr;
                if(j !== cdl.length - 1)
                    cdlText += ",  ";
            }
            console.log(index);
            console.log(cdlText);
            var r = document.getElementsByTagName("tr").item(index);
            console.log(r);
            r.childNodes[column].appendChild(document.createTextNode(cdlText));
        }
    });
}
