


var counter = 1;
var limit = 5;

function addInput(divName){
     if (counter == limit){
          alert("You have reached the limit of adding " + counter + " inputs");
     }
    
     else {
          var newdiv = document.createElement('div');
         
          newdiv.innerHTML = "<div class='col-md-6'>" + "<h5>Nome materiale</h5>" + (counter + 1) + 
          "<input type='text' class='form-control style_2' placeholder='nome' name='nomem+ (counter + 1) +[]'>" + "</div>" +  "<div class='col-md-6'>" + "<h5>Upload materiale</h5>"+
         + "<input type='file' name='linkm+ (counter + 1) +[]' onchange='ValidateCurr(this)'/>" + "</div>" + "<div class='col-md-12'>" + "<h5>Descrizione materiale IT</h5>" +
         "<textarea rows='5' name='descrizioneitm+ (counter + 1) +[]' class='tinymce1'></textarea>"+ "<h5>Descrizione materiale EN</h5>"+ "<textarea rows='5' name='descrizioneenm+ (counter + 1) +[]' class='tinymce1'></textarea>"
         + "</div>"
          
         document.getElementById(divName).appendChild(newdiv);
         counter++;
        

         
     }
}