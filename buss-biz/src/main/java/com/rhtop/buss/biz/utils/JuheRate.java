package com.rhtop.buss.biz.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
/**
*货币汇率调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/23
**/
 
public class JuheRate {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="6dd3ef78903969b8ca7447ca132b4c6b";
 
    //1.人民币牌价
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getRequestRate(String moneyName){
    	String rate = null;
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/exchange/rmbquot";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//APP Key
        params.put("type","1");//两种格式(0或者1,默认为0)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                String json = object.get("result").toString();
                JSONArray jsonArray = JSONArray.fromObject(json);  
                List<Map<String,MorphDynaBean>> list = (List<Map<String,MorphDynaBean>>) JSONArray.toCollection(jsonArray,  
                		Map.class);  
                
                for(Map<String,MorphDynaBean> map:list){
                	MorphDynaBean mdBean = map.get(moneyName);
                	BigDecimal bd = new BigDecimal(mdBean.get("bankConversionPri").toString());
                	bd = bd.divide(BigDecimal.valueOf(100));
                	rate = bd.toString();
                }
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rate;
    }
 
    //2.外汇汇率
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getRequest2(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/exchange/frate";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//APP Key
            params.put("type","");//两种格式(0或者1,默认为0)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlencode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    @SuppressWarnings("rawtypes")
	public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}