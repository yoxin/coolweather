package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	protected static final String TAG = "HttpUtil";

	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
					StringBuffer response = new StringBuffer();
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						response.append(line);
					}
					LogUtil.d(TAG, address);
					LogUtil.d(TAG, response.toString());
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					connection.disconnect();
				}
			}
		}).start();
	}
	
	public interface HttpCallbackListener {
		void onFinish(String response);
		void onError(Exception e);
	}
}
