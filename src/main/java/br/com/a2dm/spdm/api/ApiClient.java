package br.com.a2dm.spdm.api;

import br.com.a2dm.spdm.utils.AuthUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import br.com.a2dm.spdm.utils.JsonUtils;

public class ApiClient {

	private String url;

	public ApiClient(String url) {
		this.url = url;
	}

	protected String buildResource(String resource) {
		return new StringBuilder(this.url).append(resource).toString();
	}

	protected String toJson(Object value) {
		return JsonUtils.toJson(value);
	}

	public ApiClientResponse post(String resource, Object payload) {
		try {
			String json = toJson(payload);
			HttpPost httpPost = new HttpPost(this.buildResource(resource));
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(new StringEntity(json));
			try (CloseableHttpClient client = HttpClients.createDefault();
					CloseableHttpResponse response = client.execute(httpPost)) {
				return new ApiClientResponse.Builder().build(response);
			}
		} catch (Exception e) {
			throw new ApiClientException(e);
		}
	}

	public ApiClientResponse post(String resource, Object payload, AuthUtils<String, String> auth) {
		try {
			String json = toJson(payload);
			HttpPost httpPost = new HttpPost(this.buildResource(resource));
			httpPost.addHeader("content-type", "application/json");
			httpPost.addHeader(auth.getAuthKey(), auth.getAuthValue());
			httpPost.setEntity(new StringEntity(json));
			try (CloseableHttpClient client = HttpClients.createDefault();
				 CloseableHttpResponse response = client.execute(httpPost)) {
				return new ApiClientResponse.Builder().build(response);
			}
		} catch (Exception e) {
			throw new ApiClientException(e);
		}
	}

	public ApiClientResponse get(String endpoint, AuthUtils<String, String> auth) {
		try {
			HttpGet httpGet = new HttpGet(this.buildResource(endpoint));
			httpGet.addHeader(auth.getAuthKey(), auth.getAuthValue());
			try (CloseableHttpClient client = HttpClients.createDefault();
				 CloseableHttpResponse response = client.execute(httpGet)) {
				return new ApiClientResponse.Builder().build(response);
			}
		} catch (Exception e) {
			throw new ApiClientException(e);
		}
	}
}
