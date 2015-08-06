package com.servico.consultaopiniao;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import android.util.Log;

public class ConexaoHttpClient {
	public static final int HTTP_TIMEOUT = 30 * 1000;
	private static HttpClient httpClient;
	
	private static HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
			final HttpParams httpParamns = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParamns, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParamns, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(httpParamns, HTTP_TIMEOUT);
		}
		return httpClient;
	}
	public static String executaHttpPost(String url, ArrayList<NameValuePair> parametrosPost) throws Exception {
		BufferedReader bufferedReader = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parametrosPost);
			Log.i("Conexao", "Vai entrar no httpost");
			httpPost.setEntity(formEntity);
			Log.i("Conexao", "Vai entrar no httpresponse");
			//HttpResponse httpResponse = client.execute(httpPost);
			
	
			//ConexaoHttpAssincrona n = new ConexaoHttpAssincrona();
			Log.i("Conexao", "antes do httpresponse");
			
			Log.i("Conexao", "Vai chegar aqui");
			//n.execute(url);
			HashMap<String, String> dataa = new HashMap<String,String>();
			ConexaoHttpPostAssincrona n = new ConexaoHttpPostAssincrona(dataa);
			n.execute(url);
			Log.i("Conexao", "Vai entrar no buffer");
			//HttpResponse httpResponse = (HttpResponse) n.get();
			//InputStream httpResponse = (String) n.get();
			
			
			
			//bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			//bufferedReader = new BufferedReader(new InputStreamReader(httpResponse);
			Log.i("Conexao", "Vai entrar no proximo");
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator"); // \s
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();

			String resultado = stringBuffer.toString();
			return resultado;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}			

	}
	
	public static String executaHttpGet(String url) throws Exception {
		BufferedReader bufferedReader = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url);			
			httpGet.setURI(new URI(url));
			Log.i("Conexao", "Onde erra");
			//Object teste;
			
			ConexaoHttpAssincrona n = new ConexaoHttpAssincrona();
			n.execute(url);
			HttpResponse httpResponse = (HttpResponse) n.get();
			//new NetworkTask().execute(url);//HttpResponse httpResponse = client.execute(httpGet);
			
			//HttpResponse httpResponse = (HttpResponse) NetworkTask.THREAD_POOL_EXECUTOR;
			Log.i("Conexao", "passou onde errra try");
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			//String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferedReader.close();

			String resultado = stringBuffer.toString();
			return resultado;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}			
	}
	
}