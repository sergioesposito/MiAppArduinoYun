package upm.miw.saesposito.miarduinoyun.utils;

import java.io.IOException;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public final class GestorWebServices {
	private final static int ERRORRESPUESTAYUN = -1;
	private final static String MENSAJEERRORRESPUESTAYUN = "Error obteniendo respuesta del  Arduino YÚN";
	private static GestorWebServices gestorWebServices = new GestorWebServices();
	
	private  GestorWebServices() {
		
	}

	public static GestorWebServices getGestorWebServices() {
		return GestorWebServices.gestorWebServices;
	}
	
	public HttpResponse doInBackgroundAsincrono(String laURL) {
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(laURL);
			response = httpClient.execute(httpget);

		} catch (Exception e) {
			Log.e("GestorWebServices.DoInBackgroundAsincrono", e.toString());
		}

		Log.i("GestorWebServices.DoInBackgroundAsincrono",
				"saliendo de DoInBackgroundAsincrono");
		return response;

	}

	public String postExecuteAsincrono(HttpResponse response) {
		int responseCode;
		String responseMessage;

		try {
			HttpEntity entity = response.getEntity();
			responseCode = response.getStatusLine().getStatusCode();
			responseMessage = response.getStatusLine().getReasonPhrase();
			if (entity != null) {
				String responseString = null;
				try {

					responseString = EntityUtils.toString(entity);
					Log.i("GestorWebServices.PostExecuteAsincrono-responseString",
							responseString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return responseString;
			} else {
				return "[{\"ResponseCode\":" + responseCode
						+ "}, {\"ResponseMessage\":\"" + responseMessage
						+ "\"}]";
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "[{\"ResponseCode\":" + ERRORRESPUESTAYUN
					+ "}, {\"ResponseMessage\":\"" + MENSAJEERRORRESPUESTAYUN + "\"}]";
		}
		
	}
}
