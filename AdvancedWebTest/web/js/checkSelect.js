

function CheckSelect(select){
  var result = [];
  var result2=[];
  var options = select && select.options;
  var opt;

  for (var i=0, iLen=options.length; i<iLen; i++) {
    opt = options[i];

    if (opt.selected) {
      result.push(opt.value);
      result2.push(opt.value);
    }
  }
  if(result.length>0){
  
    var async=$.ajax({
                url : 'change', // Your Servlet mapping or JSP(not suggested)
                data :{result:result}, 
                type : 'POST',
                async: true,
                dataType : 'html', // Returns HTML as plain text; included script tags are evaluated when inserted in the DOM.
                success : function(data){
                   $('.cacca1').empty().end();
                    
                    $('.cacca1').html(data).end().trigger('chzen:updated');
                    
                    $(".cacca1 .chzn-select").multipleSelect("refresh");
                
                }
                
            });
            
    $.when(async).done(function(){
        $.ajax({
                url : 'change2', // Your Servlet mapping or JSP(not suggested)
                data :{result:result}, 
                type : 'POST',
                async: true,
                dataType : 'html', // Returns HTML as plain text; included script tags are evaluated when inserted in the DOM.
                success : function(data){
                   $('.cacca2').empty().end();
                    
                    $('.cacca2').html(data).end().trigger('chzen:updated');
                    
                    $(".cacca2 .chzn-select").multipleSelect("refresh");
                
                }
                
            });
    });
        }
        else{
            $('.cacca1').empty().end();
            $('.cacca1').html("<select class='chzn-select' name='propedeudici' id='special-cazzafà' multiple></select>").end().trigger('chzen:updated');
            $('.cacca2').empty().end();
            $('.cacca2').html("<select class='chzn-select' name='modulo' id='special-cazzafà' multiple></select>").end().trigger('chzen:updated');
            $(".chzn-select").multipleSelect("refresh");
        }
}