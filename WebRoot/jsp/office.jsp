<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8"> 
    <head>  
        <title>office jsp </title>  
        <style type="text/css">  
            #content {  
                width: 400px;  
                height: 200px;  
            }  
              
            #tab_bar {  
                width: 400px;  
                height: 20px;  
                float: left;  
            }  
            #tab_bar ul {  
                padding: 0px;  
                margin: 0px;  
                height: 20px;  
                text-align: center;  
            }  
              
            #tab_bar li {  
                list-style-type: none;  
                float: left;  
                width: 133.3px;  
                height: 20px;  
                background-color: gray;  
            }  
              
            .tab_css {  
                width: 400px;  
                height: 200px;  
                background-color: orange;  
                display: none;  
                float: left;  
            }  
              
        </style>  
        <script type="text/javascript">  
            var myclick = function(v) {  
                var llis = document.getElementsByTagName("li");  
                for(var i = 0; i < llis.length; i++) {  
                    var lli = llis[i];  
                    if(lli == document.getElementById("tab" + v)) {  
                        lli.style.backgroundColor = "orange";  
                    } else {  
                        lli.style.backgroundColor = "gray";  
                    }  
                }  
  
                var divs = document.getElementsByClassName("tab_css");  
                for(var i = 0; i < divs.length; i++) {  
  
                    var divv = divs[i];  
  
                    if(divv == document.getElementById("tab" + v + "_content")) {  
                        divv.style.display = "block";  
                    } else {  
                        divv.style.display = "none";  
                    }  
                }  
  
            }  
        </script>  
    </head>  
    <body>  
        <div id="content">  
            <div id="tab_bar">  
                <ul>  
                    <li id="tab1" onclick="myclick(1)" style="background-color: orange">  
                        tab1  
                    </li>  
                    <li id="tab2" onclick="myclick(2)">  
                        tab2  
                    </li>  
                    <li id="tab3" onclick="myclick(3)">  
                        tab3  
                    </li>  
                </ul>  
            </div>  
            <div class="tab_css" id="tab1_content" style="display: block">  
                <div>页面一</div>  
            </div>  
            <div class="tab_css" id="tab2_content">  
                <div>页面二</div>  
            </div>  
            <div class="tab_css" id="tab3_content">  
                <div>页面三</div>  
            </div>  
        </div>  
    </body>  
</html>  