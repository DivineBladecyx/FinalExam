 function show_others1() {
	 document.getElementById("show_others").style.visibility="visible";
		document.getElementById("shutdown").style.visibility="visible";
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "show_others.html", true);
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState == 4) {
                show_others.innerHTML = "xmlHttp.responseText";
            }
        }
        xmlHttp.send();
    }
