var pageEnum = Object.freeze({"listcorsi": "listcorsi"});

function bodychange(page){
    switch(page) {
        case pageEnum.listcorsi:
            //jQuery('#body').load('template/courses_list.html');
            
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
                            $('#cdl').replaceWith(newTemp);
                        }
                    });
                });
                }
            });
            
            /*$.ajax({
                url:'template/courses_list.html',
                dataType:'html',
                type:'GET',
                success:function(html){
                $.ajax({
                    url:'http://localhost:8084/AdvancedWeb/rest/cdl',
                    dataType:'json',
                    type:'GET',
                    success:function(json){
                        var str = html.toString();
                        var temp = html.jQuery('#cdl').html();
                        json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                        var newTemp=Mustache.render(temp,{'cdl':json});
                        html.$('#cdl').replaceWith(newTemp);
                    }
                });
                ('#body').replaceWith(html);
                }
                
            });*/
            
            
            /*$.ajax({
                url:'template/courses_list.html',
                dataType:'html',
                type:'GET',
                success:function(html){
                    var template=html;
                    $('#body').replaceWith(html)
                    $.ajax({
                        url:'http://localhost:8084/AdvancedWeb/rest/courses',
                        dataType:'json',
                        type:'GET',
                        success:function(json){
                            json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                            template=Mustache.render(template,{'corsi':json});
                            //$('#body').replaceWith(template);
                        }
                    });
                    $.ajax({
                        url:'http://localhost:8084/AdvancedWeb/rest/cdl',
                        dataType:'json',
                        type:'GET',
                        success:function(json){
                            var temp = ('#cdl').html();
                            json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                            var newTemp=Mustache.render(temp,{'cdl':json});
                            $('#cdl').replaceWith(newTemp);
                        }
                    });
                    //console.log(template);
                    //$('#body').replaceWith(template);
                }
            });*/
        
    }
    
            
            
            
    
    //$('#body').load('template/courses_list.html');
    /*
    console.log('ciao');
        var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("body").innerHTML = this.responseText;
    }
  };
  xhttp.open("GET", "template/courses_list.html", true);
  console.log(xhttp);
  xhttp.send();*/
  }