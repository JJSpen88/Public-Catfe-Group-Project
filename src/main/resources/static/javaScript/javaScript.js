window.onload = choosePic;

var myPix = new Array("../static/images/avatar1.svg","../static/images/avatar2.svg","../static/images/avatar3.svg", "/images/avatar3.svg" );

function choosePic() {
     var randomNum = Math.floor(Math.random() * myPix.length);
     document.getElementById("myPicture").src = myPix[randomNum];