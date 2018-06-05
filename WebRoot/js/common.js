//判断文件是否符合要求（大小、类型）
//传递参数：obj 对象；type 文件允许上传类型；size 文件允许上传的大小；unit 文件大小单位 KB、MB、GB，默认为KB。
function getFileSize(obj, type, size, unit) {
	photoExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if (type.toLowerCase().indexOf(photoExt) < 0) {
		obj.outerHTML = obj.outerHTML; //重新初始化了obj的html
		alert("请上传后缀名为\"" + type + "\"的文件!");
		return false;
	}

	var fileSize = 0;
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	if (isIE && !obj.files) {
		obj.select();
		var filePath = document.selection.createRange().text;
		//var filePath = obj.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		if (!fileSystem.FileExists(filePath)) {
			obj.outerHTML = obj.outerHTML; //重新初始化了obj的html
			alert("附件不存在，请重新输入！");
			return false;
		}
		var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = obj.files[0].size;
	}
	var a = 1;
	if(unit.toUpperCase()=="K"||unit.toUpperCase()=="KB"){
		a = 1024;
	}else if(unit.toUpperCase()=="M"||unit.toUpperCase()=="MB"){
		a = 1024 * 1024;
	}else if(unit.toUpperCase()=="G"||unit.toUpperCase()=="GB"){
		a = 1024 * 1024 * 1024;
	}
	//fileSize = Math.round(fileSize/a * 100) / 100;
	if (fileSize/a >= size) {
		obj.outerHTML = obj.outerHTML; //重新初始化了obj的html
		alert("附件大小不能大于"+size+unit+"，请重新上传!");
		return false;
	}
	if(fileSize<=0){
		obj.outerHTML = obj.outerHTML; //重新初始化了obj的html
		alert("附件大小不能为0"+unit+"！");
		return false; 
	}
}