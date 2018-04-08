package com.office.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class RestfulUtil {
	
	private static Logger logger = Logger.getLogger(RestfulUtil.class);//输出Log日志
	
	/**
	 * 获取restful接口数据
	 * 
	 * @param url 接口url，如有查询条件可在url中配置filter
	 * @param method OPTIONS,GET,HEAD,POST,PUT,PATCH,DELETE,TRACE,CONNECT
	 * @param paramHeader Header参数
	 * @param paramBody Body中如不需要传递参数，值为NULL(例：当method为“GET”时，paramBody=null)
	 * @return JSONObject 返回json对象 {"responseCode":"","result":""}
	 */
	public static JSONObject getRestfulData(String url,String method,Map<String, String> paramHeader, String paramBody){
		
		JSONObject resultJson = new JSONObject();

		StringBuffer result = new StringBuffer();
		HttpURLConnection httpConnection =null;
		try{
			URL targetUrl = new URL(url);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			if("PATCH".equals(method.toUpperCase())){
				httpConnection.setRequestMethod("POST");
				httpConnection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
			}else{
				httpConnection.setRequestMethod(method.toUpperCase());
			}
			httpConnection.setRequestProperty("Charsert", "ISO-8859-1");
	        if (paramHeader != null&&!"".equals(paramHeader)) {
	            Set<String> keySet = paramHeader.keySet();
	            for (String key : keySet) {
	            	httpConnection.setRequestProperty(key, paramHeader.get(key));
	            }
	        }

	        if(paramBody != null&&!"".equals(paramBody)){
	        	httpConnection.setDoOutput(true);
	        	httpConnection.setDoInput(true);
	        	
	            OutputStream outputStream = httpConnection.getOutputStream();
	            outputStream.write(paramBody.getBytes());
	            outputStream.flush();
	        }
	        
	        String responseCode = String.valueOf(httpConnection.getResponseCode());
	        resultJson.put("responseCode", responseCode);
	        
	        String output;
	    	
	        logger.debug("Output from Server:\n");
	        if (responseCode.startsWith("2")) {

		        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
	                      httpConnection.getInputStream(),"UTF-8"));
		        
		        while ((output = responseBuffer.readLine()) != null) {
		        	result.append(output);
		        	logger.debug(result);
		        }
	        }else if(httpConnection.getErrorStream()==null){
	        	
	        	result.append(httpConnection.getResponseMessage());
	        	logger.debug(result);
        	}else{
        		
		        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
	                      httpConnection.getErrorStream(),"UTF-8"));
		        
		        while ((output = responseBuffer.readLine()) != null) {
		        	result.append(output);
		        	logger.debug(result);
		        }
        	}
			resultJson.put("result", result.toString());

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally{
			if(httpConnection!=null)
	        httpConnection.disconnect();
		}
		return resultJson;
	}
	
	public static JSONObject getToken(){
		String url = "https://login.chinacloudapi.cn/vstecs.partner.onmschina.cn/oauth2/token?api-version=1.0";
		String method = "POST";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Content-Type", "application/x-www-form-urlencoded");
		paramHeader.put("Cache-Control", "no-cache");
		StringBuffer paramBody = new StringBuffer();
		paramBody.append("grant_type=password");
		paramBody.append("&resource=https://partner.partnercenterapi.microsoftonline.cn");
		paramBody.append("&client_id=c60c238f-7be3-43d2-874e-2a185c65bf8f");
		paramBody.append("&username=administrator@vstecs.partner.onmschina.cn");
		paramBody.append("&password=Vstecs@123");
		paramBody.append("&scope=openid");
		
		JSONObject resultJson = getRestfulData(url,method,paramHeader,paramBody.toString());
		if(resultJson.get("responseCode").toString().startsWith("2")){
			return (JSONObject)resultJson.get("result");
		}else{
			return new JSONObject();
		}
	}
}