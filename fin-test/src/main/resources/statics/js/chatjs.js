
var send_id="";
var send_id_array;
var fromuser_id;
function openchat(send_user_id) {
    document.getElementById("message").style.display="block";
    send_id = send_user_id;
}
var id = [[${user_id}]];
var websocket = null;

//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
    websocket = new WebSocket("ws://localhost:8080/WebSocket/"+id);
}
else{
    alert('Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function(){
    setMessageInnerHTML("error");
};

//连接成功建立的回调方法
websocket.onopen = function(event){
    setMessageInnerHTML("open");
}

//接收到消息的回调方法
websocket.onmessage = function(event){
    var message=event.data;
    var Message=message.split("|");
    if(Message.length==4) {//处理聊天消息
        var tag = Message[2];
        switch (tag) {
            case '100001':
                var realmessage=Message[1];
                var fromuserid=Message[0];
                var Finmessage="<div>"+"用户"+fromuserid+":  "+ realmessage + "</div>"
                setMessageInnerHTML(Finmessage);
                break;

        }
    }
    else if(Message.length==3){//处理操作消息
        var tag = Message[1];
        switch (tag) {
            case '100003':
                var fromuserid=Message[0];
                var senduserid=Message[2];
                var Finmessage="<div>"+"用户"+fromuserid+":  请求加您为好友！" + "</div>"+ " <a href='javascript:agree()'>同意</a><a href='javascript:reject()'>拒绝</a><br/>";
                fromuser_id=fromuserid;
                setAddMessageInnerHTML(Finmessage)
                break;
            case '100004':
                var fromuserid=Message[0];
                var senduserid=Message[2];
                var Finmessage="<div>"+"用户"+fromuserid+": 和您已加为好友！" + "</div>"+ "<button onclick='location.reload()'>确认</button>";
                fromuser_id=fromuserid;
                setAddMessageInnerHTML(Finmessage)
                break;
            case '100008':
                var fromuserid=Message[0];
                var senduserid=Message[2];
                var Finmessage="<div>"+"用户"+fromuserid+": 拒绝加您为好友！" + "</div>"+ "<button onclick='location.reload()'>确认</button>";
                fromuser_id=fromuserid;
                setAddMessageInnerHTML(Finmessage)
                break;
        }
    }
    else{//处理群消息
        var tag = Message[2];
    }


    //fromuser_id = message.split("|")[1];
    //setAddMessageInnerHTML(message.split("|")[0]+message.split("|")[1]+message.split("|")[2]);
}

//连接关闭的回调方法
websocket.onclose = function(){
    setMessageInnerHTML("close");
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
    websocket.close();
}
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML +=innerHTML;
}
function  setAddMessageInnerHTML(innerHTML) {
    document.getElementById('addmessage').innerHTML += innerHTML ;
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

//关闭连接
function closeWebSocket(){
    websocket.close();
}

//发送消息
function send(){//向好友发送信息
    if (send_id < 1000) {
        var message = document.getElementById('text').value;
        var Finmessage = id+"|"+message + "|" +"100001"+"|"+ send_id;
        websocket.send(Finmessage);
    }
    else {
        var message = document.getElementById('text').value;
        for (var i = 0; i < send_id_array.length; i++) {
            var Finmessage = message + "|" + send_id_array.valueOf(i);
        }
        websocket.send(Finmessage);
    }
}
function addsend(){//发送添加好友的信息

    var sendid = document.getElementById('addtext').value;
    var Finmessage=id+"|"+"100003"+"|"+sendid;
    websocket.send(Finmessage);
}
function findfriend() {
    var friend=document.getElementById('addtext').value;
    $.ajax({
        url:'/FriendsController/getfriend',
        data:{'friend':friend},
        type : 'post',
        traditional : true,
        async : false,
        success:function(){
            //
        },
        error : function(){
            alert(fromAnduser+'error');
        }
    });

}