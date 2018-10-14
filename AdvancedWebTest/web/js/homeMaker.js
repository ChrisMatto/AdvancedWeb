function homeMaker(){
    $.ajax({
        url:'http://localhost:8084/AdvancedWeb/rest/cdl/random',
        dataType: 'json',
        type:'GET',
        success: function(json){
            //console.log(json);
            //console.log(json[0]);

            /*for(var i=0;i<1;i++){
                $('#cdlink').attr('href','OnClick()');
                $('#cdimg').attr('src',json[i].immagine);
                if(json[i].nome_it.trim())
                    $('#nome').text(json[i].nome_it);
                else
                    $('#nome').text(json[i].nome_en);
                if(json[i].descrizione_it.trim())
                    $('#descrizione').html(json[i].descrizione_it);
                else
                    $('#descrizione').html(json[i].descrizione_en);
                $('#cfu').text(json[i].cfu+' CFU');
                $('#cdlink2').attr('href','OnClick()');
            }*/
            
            //var template=$('#cdl').wrap('<p/>').parent().html();
            //$('#cdl').unwrap();
            var template=$('#cdl').html();
            //console.log(template);
            var newTemp=Mustache.render(template,{'cdl':json});
            $('#cdl').replaceWith(newTemp);
            
            $.ajax({
                url:'http://localhost:8084/AdvancedWeb/rest/home/cdlm',
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
    /*var home={};
    $.get('http://localhost:8084/AdvancedWeb/rest/cdl','json',function(data){home['cdl']=data;});
    $.get('http://localhost:8084/AdvancedWeb/rest/cdlm','json',function(data){home['cdlm']=data;});
    console.log(home);
    var template=$('#home').html();
    var newTemp=Mustache.render(template,home);
    $('#home').replaceWith(newTemp);*/
    
    
}