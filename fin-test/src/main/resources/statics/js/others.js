//   
//<script>   
//        window.onload = function(){ 
//	var anopeople=document.getElementById('anopeople');
//            var arrIcon = ['1.jpg','2.jpg'];    
//            var num = 0;     <!--控制头像改变-->    
//            var iNow = -1;    <!--用来累加改变左右浮动-->    
//            var icon = document.getElementById('user_face_icon').getElementsByTagName('img');    
//            var btn = document.getElementById('btn');    
//            var text = document.getElementById('text');    
//            var content = document.getElementsByTagName('ul')[0];    
//            var img = content.getElementsByTagName('img');    
//            var span = content.getElementsByTagName('span');    
//   
//              
//               
//	btn.onclick = function(){    
//                if(text.value ==''){    
//                    alert('不能发送空消息');    
//                }else {    
//                    content.innerHTML += '<li><img  src="'+arrIcon[num]+'" ><span>'+text.value+'</span></li>';    
//                    iNow++;        
//                    img[iNow].className += 'imgright';    
//                    span[iNow].className += 'spanright';
//    
//                    text.value = '';    
////     	<!-- 内容过多时,将滚动条放置到最底端 -->
//   
//     	contentcontent.scrollTop=content.scrollHeight;      
//                }  
//            }
// 
//anopeople.onclick=function(){
//content.innerHTML += '<li><img  src="'+arrIcon[1]+'" ><span>还行</span></li>';
// iNow++;
//img[iNow].className += 'imgleft';   
//span[iNow].className += 'spanleft';
////<!-- 内容过多时,将滚动条放置到最底端 -->   
//contentcontent.scrollTop=content.scrollHeight;           
//}     
//	}    
//    </script>
//<script>
//    function mytime(){
//        var a = new Date();
//        var b = a.toLocaleTimeString();
//        var c = a.toLocaleDateString();
//        document.getElementById("time1").innerHTML = c+"&nbsp"+b;
//        }
//    setInterval(function() {mytime()},1000);
//</script>