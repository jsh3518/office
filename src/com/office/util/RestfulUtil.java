package com.office.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class RestfulUtil {
	static String baseUrl = null;
	static String access_token = null;
	static Date start = null;

	private static Logger logger = Logger.getLogger(RestfulUtil.class);// 输出Log日志

	/**
	 * 获取restful接口数据
	 * 
	 * @param url 接口url，如有查询条件可在url中配置filter
	 * @param method OPTIONS,GET,HEAD,POST,PUT,PATCH,DELETE,TRACE,CONNECT
	 * @param paramHeader Header参数
	 * @param paramBody Body中如不需要传递参数，值为NULL(例：当method为“GET”时，paramBody=null)
	 * @return JSONObject 返回json对象 {"responseCode":"","result":""}
	 */
	public static JSONObject getRestfulData(String url, String method, Map<String, String> paramHeader,
			String paramBody) {

		JSONObject resultJson = new JSONObject();

		StringBuffer result = new StringBuffer();
		HttpURLConnection httpConnection = null;
		try {
			URL targetUrl = new URL(url);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();

			if ("PATCH".equals(method.toUpperCase())) {
				allowMethods(method.toUpperCase());
				httpConnection.setRequestMethod("PATCH");
				//httpConnection.setRequestMethod("POST");
				//httpConnection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
			} else {
				httpConnection.setRequestMethod(method.toUpperCase());
			}
			httpConnection.setRequestProperty("Charsert", "ISO-8859-1");
			if (paramHeader != null && !"".equals(paramHeader)) {
				Set<String> keySet = paramHeader.keySet();
				for (String key : keySet) {
					httpConnection.setRequestProperty(key, paramHeader.get(key));
				}
			}

			if (paramBody != null && !"".equals(paramBody)) {
				httpConnection.setDoOutput(true);
				httpConnection.setDoInput(true);
				httpConnection.setConnectTimeout(30000);
				httpConnection.setReadTimeout(30000);
				OutputStream outputStream = httpConnection.getOutputStream();
				outputStream.write(paramBody.getBytes());
				outputStream.flush();
			}

			String responseCode = String.valueOf(httpConnection.getResponseCode());
			resultJson.put("responseCode", responseCode);

			String output;

			logger.debug("Output from Server:\n");
			if (responseCode.startsWith("2")) {

				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));

				while ((output = responseBuffer.readLine()) != null) {
					result.append(output);
					logger.debug(result);
				}
			} else if (httpConnection.getErrorStream() == null) {

				result.append(httpConnection.getResponseMessage());
				logger.debug(result);
			} else {

				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader(httpConnection.getErrorStream(), "UTF-8"));

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

		} finally {
			if (httpConnection != null)
				httpConnection.disconnect();
		}
		return resultJson;
	}

	/**
	 * 通过restful接口从PartnerCenter（21V）获取数据
	 * 
	 * @param url 接口url，如有查询条件可在url中配置filter
	 * @param method OPTIONS,GET,HEAD,POST,PUT,PATCH,DELETE,TRACE,CONNECT
	 * @param paramHeader Header参数
	 * @param paramBody Body中如不需要传递参数，值为NULL(例：当method为“GET”时，paramBody=null)
	 * @return JSONObject 返回json对象 {"responseCode":"","result":""} (PartnerCenter接口返回result开头有特殊符号,需要截取处理)
	 */
	public static JSONObject getPartnerCenterData(String url, String method, Map<String, String> paramHeader,
			String paramBody) {

		JSONObject resultJson = new JSONObject();

		StringBuffer result = new StringBuffer();
		HttpURLConnection httpConnection = null;
		try {
			URL targetUrl = new URL(url);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			//解决HttpURLConnection不支持"PATCH"问题
			allowMethods(method.toUpperCase());
			httpConnection.setRequestMethod(method.toUpperCase());
			
			if (paramHeader != null && !"".equals(paramHeader)) {
				Set<String> keySet = paramHeader.keySet();
				for (String key : keySet) {
					httpConnection.setRequestProperty(key, paramHeader.get(key));
				}
			}

			if (paramBody != null && !"".equals(paramBody)) {
				httpConnection.setDoOutput(true);
				httpConnection.setDoInput(true);
				httpConnection.setConnectTimeout(30000);
				httpConnection.setReadTimeout(30000);
				OutputStream outputStream = httpConnection.getOutputStream();
				outputStream.write(paramBody.getBytes());
				outputStream.flush();
			}

			String responseCode = String.valueOf(httpConnection.getResponseCode());
			resultJson.put("responseCode", responseCode);

			String output;

			logger.debug("Output from Server:\n");
			if (responseCode.startsWith("2")) {

				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));

				while ((output = responseBuffer.readLine()) != null) {
					result.append(output);
					logger.debug(result);
				}
			} else if (httpConnection.getErrorStream() == null) {

				result.append(httpConnection.getResponseMessage());
				logger.debug(result);
			} else {

				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader(httpConnection.getErrorStream(), "UTF-8"));

				while ((output = responseBuffer.readLine()) != null) {
					result.append(output);
					logger.debug(result);
				}
			}
			//截取，PartnerCenter接口返回数据开头有特殊符号（空格或者不可识别的符号）
			resultJson.put("result", result.indexOf("{")>=0?result.substring(result.indexOf("{")):result);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			if (httpConnection != null)
				httpConnection.disconnect();
		}
		return resultJson;
	}
	
	//获取access_token，并放入静态变量中
	public static String getAccessToken(){
	    long current = System.currentTimeMillis();
	    Date date = new Date(current-30 * 60 * 1000);
		if(access_token!=null&&start!=null&&start.compareTo(date)>0 ){
			return access_token;
		}else{
			start = new Date();
			JSONObject jsonObject = getToken();
			access_token = jsonObject.get("access_token")==null?null:jsonObject.get("access_token").toString();
			return access_token;
		}
	}
	
	/**
	 * 获取总代Token
	 * 
	 * @return
	 */
	public static JSONObject getToken() {
		HashMap<String, String> map = getPartnerMap();

		String domain = map.get("username").substring(map.get("username").lastIndexOf("@")+1);
		String url = "https://login.chinacloudapi.cn/"+domain+"/oauth2/token?api-version="+map.get("version");
		String method = "POST";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Content-Type", "application/x-www-form-urlencoded");
		paramHeader.put("Cache-Control", "no-cache");
		StringBuffer paramBody = new StringBuffer();
		paramBody.append("grant_type=password");
		paramBody.append("&scope=openid");
		if(!map.isEmpty()){
			paramBody.append("&resource=").append(map.get("resource"));
			paramBody.append("&client_id=").append(map.get("appid"));
			paramBody.append("&username=").append(map.get("username"));
			paramBody.append("&password=").append(map.get("password"));
		}

		JSONObject resultJson = getRestfulData(url, method, paramHeader, paramBody.toString());
		if (resultJson.get("responseCode").toString().startsWith("2")) {
			return (JSONObject) resultJson.get("result");
		} else {
			return new JSONObject();
		}
	}

	/**
	 * 根据mpnId获取代理商信息
	 * 
	 * @param access_token
	 * @param mpnId
	 * @return
	 */
	public static JSONObject getMpnId(String mpnId) {
		String access_token = RestfulUtil.getAccessToken();
		String targetURL = "https://"+getBaseUrl()+"/profiles/mpn?mpnId=" + mpnId;
		String method = "GET";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Authorization", "Bearer " + access_token);
		return getRestfulData(targetURL, method, paramHeader, null);
	}
	
	/**
	 * 获取Restful接口baseUrl 形如：https://url/version
	 * @return
	 */
	public static String getBaseUrl() {
		if(baseUrl == null){
			HashMap<String, String> partnerMap = getPartnerMap();
			String resource = partnerMap.get("resource")==null||"".equals(partnerMap.get("resource"))?"https://partner.partnercenterapi.microsoftonline.cn":partnerMap.get("resource");
			String version = partnerMap.get("version")==null||"".equals(partnerMap.get("version"))?"v1":partnerMap.get("version");
			baseUrl = resource + "/v" + version;
		}
		return baseUrl;
	}
	
	private static HashMap<String, String> getPartnerMap(){
		HashMap<String, String> partnerMap = new HashMap<String, String>();
		String sql = "select * from partner where valid = 1 ";
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = JdbcPool.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if(rs.next()){
				partnerMap = new HashMap<String, String>();
				partnerMap.put("resource", rs.getString("resource"));
				partnerMap.put("appid", rs.getString("appid"));
				partnerMap.put("username", rs.getString("username"));
				partnerMap.put("password", AesUtil.aesDecrypt(rs.getString("password")));
				partnerMap.put("version", rs.getString("version"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcPool.release(rs,statement,conn);
		}
		
		return partnerMap;
	}
	
	/**
	 * 配置HttpURLConnection支持PATCH方法，默认不支持
	 * @param methods
	 */
	public static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);
			methodsField.setAccessible(true);
			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);
			methodsField.set(null, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
}