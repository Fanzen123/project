 
function deconnect() {
	 $.ajax({
		    url: "/myges/index/connect",
		    context: document.body,
		    type : 'POST',
			data : 'deconnect=', 
		    success: function(){
		      $(this).addClass("done");
		    }
		});
	 
	 document.location.href="/myges/index/index/";
	 
	 alert("Vous avez été déconnecté, à bientot");
}

function connect() {
	var login = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var response = $.ajax({
		    url: "/myges/index/connect",
		    async: false,
		    context: document.body,
		    type : 'POST',
			data : 'login=' + login + '&password=' + password
	}).responseText;;
	
	if(response == '1') {
		alert('Vous etes maintenant connecte !');
		document.location.href="/myges/index/index/";
	} else {
		alert('Identifiant errone !');
	}
}

function sleep(time){
    var start = new Date().getTime();
    while(start+time > new Date().getTime()) true;
    return;
}