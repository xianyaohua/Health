package com.ttyc.health.utils.http;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 网络请求工具类
 */
public class HttpsTool {
	private static HttpsTool instance;
	public HttpsTool() {
	}
	/**
	 *返回实例
	 * @return
	 */
	public static HttpsTool getInstance() {
		if (instance == null) {
			instance = new HttpsTool();
		}
		return instance;
	}


	/**Post请求方法*/
	public String runPost(final Activity activity, final String urls, final String param, final String model){
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urls);
			conn = (HttpURLConnection) url.openConnection();
			//Log.i("请求的参数=====>", param);
			//Log.i("请求的地址=====>", Constant.NETWORK_REQUEST + urls);
			//Log.i("请求的方式=====>", model);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod(model);
			conn.setUseCaches(false); // 禁止缓存
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
			DataOutputStream out = new DataOutputStream(conn.getOutputStream()); // 获取输出流
			out.writeBytes(param);//将要传递的数据写入数据输出流
			out.flush();    //输出缓存
			out.close();    //关闭数据输出流
			int code = conn.getResponseCode();
			if(code == 200){
				result = parseSendMessageResponse(conn.getInputStream());
			}
			conn.connect();
			conn.disconnect();
			//Log.i("请求的响应状态码=====>", String.valueOf(code));
			//Log.i("请求的结果=====>", result);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (KeyManagementException e){//a
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public String runPostJson(final Activity activity, final String urls, final String toKen, final String param, final String model){
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urls);
			conn = (HttpURLConnection) url.openConnection();
			//Log.i("请求的参数=====>", param);
			//Log.i("请求的地址=====>", Constant.NETWORK_REQUEST + urls);
			//Log.i("请求的方式=====>", model);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod(model);
			conn.setUseCaches(false); // 禁止缓存
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("token",toKen );
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 设置内容类型
			DataOutputStream out = new DataOutputStream(conn.getOutputStream()); // 获取输出流
			out.writeBytes(param);//将要传递的数据写入数据输出流
			out.flush();    //输出缓存
			out.close();    //关闭数据输出流
			int code = conn.getResponseCode();
			//Log.e("测试服务器异常",""+code);
			if(code == 200){
				result = parseSendMessageResponse(conn.getInputStream());
			}
			conn.connect();
			conn.disconnect();
			//Log.i("请求的响应状态码=====>", String.valueOf(code));
			//Log.i("请求的结果=====>", result);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch (KeyManagementException e){//a
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		/*try{
			JSONObject jb = new JSONObject(result);
			if(jb.getString("code").equals("602")){
                String code = jb.getString("code");
				if(!App.getService().getPerferenceInfo().get("token").equals("")) {
					App.getService().saveInfo("", "", "", "", "", "", "", "", "", "", "","");
					token = "";
					//deleteAlias();//hxy
					//logout();//hxy
				}
			}
		}catch (Exception e){}
		if(result.equals("")){
			if(!isNetworkConnected()){
				toastTest("请检查网络");
			}else{
				int num = App.getShared().getInt("except");
				num++;
				App.getShared().putInt("except",num);
				if(num>1) {
					toastTest("服务器异常");
					App.getShared().putInt("except",0);
				}
			}
		}else{
			App.getShared().putInt("except",0);
		}*/
		return result;
	}
	/*private void toastTest(final String str) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Handler handler = new Handler(Looper.getMainLooper());
				handler.post(new Runnable() {
								 @Override
								 public void run() {
									 //放在UI线程弹Toast
									 Toast.makeText(App.getContext(),str, Toast.LENGTH_SHORT).show();
								 }
							 });
			}
		}).start();
	}
*/
	/*
   判断是否有网络连接
    *//*
	public boolean isNetworkConnected() {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}

		return false;
	}*/

	public String runGet(final String urls, final String param, String toKen){
		String result="";
		HttpURLConnection conn = null;

		try {
			URL url = new URL(urls+"?"+param);
			conn = (HttpURLConnection) url.openConnection();

			//Log.i("请求的地址=====>", Constant.NETWORK_REQUEST + urls + "?" + param);
			//Log.i("请求的方式=====>", model);//结果 返回的结果
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("token",toKen);
			int code = conn.getResponseCode();
			//Log.e("测试服务器异常",""+code);
			if(code == 200){
				result = parseSendMessageResponse(conn.getInputStream());
			}
			//关闭连接
			conn.disconnect();
			//Log.i("请求的响应状态码=====>", String.valueOf(code));
			//Log.i("请求的结果=====>", result);

		}catch (Exception e){
			e.printStackTrace();
		}

		/*try{
			JSONObject jb = new JSONObject(result);
			if(jb.getString("code").equals("602")){
                String code = jb.getString("code");
				if(!App.getService().getPerferenceInfo().get("token").equals("")) {
					App.getService().saveInfo("", "", "", "", "", "", "", "", "", "", "","");
					token = "";

					*//*deleteAlias();//hxy
					logout();*//*//hxy
				}
			}
		}catch (Exception e){}*/

		/*if(result.equals("")){
			if(!isNetworkConnected()){
				toastTest("请检查网络");
			}else{
				int num = App.getShared().getInt("except");
				num++;
				App.getShared().putInt("except",num);
				if(num>1) {
					toastTest("服务器异常");
					App.getShared().putInt("except",0);
				}
			}
		}else{
			App.getShared().putInt("except",0);
		}*/
		return result;
	}

	public String parseSendMessageResponse(InputStream in) throws Exception {
		// Read from the input stream and convert into a String.
		InputStreamReader inputStream = new InputStreamReader(in);
		BufferedReader buff = new BufferedReader(inputStream);

		StringBuilder sb = new StringBuilder();
		String line = buff.readLine();
		while(line != null){
			sb.append(line);
			line = buff.readLine();
		}
		return sb.toString();
	}


}

