
//打开聊天窗口
function openchat(send_user_id) {
    // document.getElementById("message").style.display="block";
    document.getElementById('mess').innerHTML="";
        send_id = send_user_id;
        var Finmessage=100019+"|"+id+"|"+send_user_id;
        websocket.send(Finmessage);
    document.getElementById("show_others").style.visibility="hidden";
    document.getElementById("show_ana").style.visibility="visible";
    setTimeout( function(){
        document.getElementById("show_ana").style.visibility="hidden";
        document.getElementById("show_others").style.visibility="visible";
        document.getElementById("shutdown").style.visibility="visible";
        // document.getElementById("mess").innerHTML="";



//
//		ss=get_oth_mess();
//                document.getElementById("show_others").innerHTML = ss;

    }, 1.2 * 1000 );
}

function openaddfriend() {
    document.getElementById('addmessage').style.visibility="visible";
}
function openaddcrowd() {
    document.getElementById('addcrowdmessage').style.visibility="visible";
}
function opencrowdchat(send_crowd_id) {
    //document.getElementById("crowdmessage").style.display="block";
    send_id = send_crowd_id;
    findcmid(send_id);
}














//发送消息
function send(){//向好友发送信息
    if (send_id < 1000) {//单聊
        var message = document.getElementById('text').value;
        var Finmessage = id+"|"+message + "|" +"100001"+"|"+ send_id;
        websocket.send(Finmessage);
    }
    else {//群聊
        var message = document.getElementById('crowdtext').value;
            var Finmessage = "100002" + "|" +id+"|"+message+"|"+send_id;
        websocket.send(Finmessage);
    }
}
function findcrowd(crowdid) {
    var Finmessage="100015"+"|"+id+"|"+crowdid;
    websocket.send(Finmessage);
}
function addsend(){//发送添加好友的信息

    var sendid = document.getElementById('addtext').value;
    var Finmessage=id+"|"+"100003"+"|"+sendid;
    websocket.send(Finmessage);
}
function addcrowdsend(){//发送添加群的信息

    var sendid = document.getElementById('addcrowdtext').value;
    var Finmessage="100006"+"|"+id+"|"+sendid;
    websocket.send(Finmessage);
}
function findcmid(crowdid) {
    var Finmessage="100011"+"|"+id+"|"+crowdid
    websocket.send(Finmessage);
}
function findfriend() {
    var friendid=document.getElementById('addtext').value;
    var Finmessage=id+"|"+"100009"+"|"+friendid
    websocket.send(Finmessage);
}
function agree() {

    var fromAnduser=fromuser_id+"|"+id;
    var addsuccess=fromuser_id+"|"+"100004"+"|"+id;
    websocket.send(addsuccess);
    $.ajax({
        url:'/FriendsController/getStringParam',
        data:{'fromAnduser':fromAnduser},
        type : 'post',
        traditional : true,
        async : false,
        success:function(){
            //

            alert('成功添加好友！');
            location.reload();
        },
        error : function(){
            alert(fromAnduser+'error');
        }
    });
}
function reject() {
    var adddefeat=fromuser_id+"|"+"100008"+"|"+id;
    websocket.send(adddefeat);
    location.reload();
}
function agreecrowd() {
    var sendid = fromuser_id;
    var Finmessage="100007"+"|"+sendid+"|"+crowd_id;
    websocket.send(Finmessage);
}
function rejectcrowd() {
    var sendid = fromuser_id;
    var Finmessage="100014"+"|"+sendid+"|"+crowd_id;
    websocket.send(Finmessage);
}
























