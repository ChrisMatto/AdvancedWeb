export function compareStrings(a, b) {
  // Assuming you want case-insensitive comparison
  a = a.toLowerCase();
  b = b.toLowerCase();

  return (a < b) ? -1 : (a > b) ? 1 : 0;
}

export function formatBytes(bytes,decimals) {
  if(bytes === 0) return '0 Bytes';
  var k = 1024,
      dm = decimals <= 0 ? 0 : decimals || 2,
      sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
      i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}

export function validateImg(fileName) {
  var _validFileExtensions = [".jpg", ".jpeg", ".bmp", ".gif", ".png"];    
      var sFileName = fileName;
        if (sFileName.length > 0) {
          var blnValid = false;
          for (var j = 0; j < _validFileExtensions.length; j++) {
              var sCurExtension = _validFileExtensions[j];
              if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                  blnValid = true;
                  break;
              }
          }

          if (!blnValid) {
              alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
              return false;
          }
      }
      return true;
}

export function validateCurr(fileName) {
  var _validFileExtensions = [".doc", ".docx", ".txt", ".pdf"];    
      var sFileName = fileName;
        if (sFileName.length > 0) {
          var blnValid = false;
          for (var j = 0; j < _validFileExtensions.length; j++) {
              var sCurExtension = _validFileExtensions[j];
              if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                  blnValid = true;
                  break;
              }
          }

          if (!blnValid) {
              alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
              return false;
          }
      }
      return true;
}

export function fileToBase64(file) {
  return new Promise(resolve => {
    var reader = new FileReader();

    reader.onload = function(event) {
      resolve(btoa(event.target.result));
    };
    
    reader.readAsBinaryString(file);
  });
}