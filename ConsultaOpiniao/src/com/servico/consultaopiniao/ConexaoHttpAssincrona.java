package com.servico.consultaopiniao;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class ConexaoHttpAssincrona extends AsyncTask<String, Void, HttpResponse> {
    File f;
	
	@Override
    protected HttpResponse doInBackground(String... params) {
        String link = params[0];
        HttpGet request = new HttpGet(link);
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        try {
        	Log.i("Conexao", "try");
            return client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        client.close();
    }
    }

    @Override
    protected void onPostExecute(HttpResponse result) {
        //Do something with result
        if (result != null)
			try {
				Log.i("Conexao", "Dentrodopost");
				//result.getEntity().writeTo(new FileOutputStream(f));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    }
}
