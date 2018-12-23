
//打开聊天窗口
function openchat(send_user_id) {
    document.getElementById("message").style.display="block";
    send_id = send_user_id;
}

function opencrowdchat(send_crowd_id) {
    document.getElementById("crowdmessage").style.display="block";
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










//将信息显示在页面上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML +=innerHTML;
}
function  setAddMessageInnerHTML(innerHTML) {
    document.getElementById('addmessage').innerHTML += innerHTML ;
}
function  setCrowdMessageInnerHTML(innerHTML) {
    document.getElementById('crowdmessage').innerHTML += innerHTML ;
}
//关闭连接
function closeWebSocket(){
    websocket.close();
}












