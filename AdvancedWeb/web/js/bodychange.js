function bodychange(){
    $.ajax({
        url:'template/courses_list.html',
        dataType:'html',
        type:'GET',
        success:function(html){
            var template=html;
            $.ajax({
                url:'http://localhost:8084/AdvancedWeb/rest/courses',
                dataType:'json',
                type:'GET',
                success:function(json){
                    json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                    template=Mustache.render(template,{'corsi':json});
                }
            });
            $.ajax({
                url:'http://localhost:8084/AdvancedWeb/rest/cdl',
                dataType:'json',
                type:'GET',
                success:function(json){
                    json.sort(function(a,b){return compareStrings(a.nome_it,b.nome_it);});
                    template=Mustache.render(template,{'cdl':json});
                }
            });
        }
    });
            
            
            
    
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