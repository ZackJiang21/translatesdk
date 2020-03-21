package org.iiai.translate.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClient {
    private HttpClient() {

    }
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();

    static {
        CONNECTION_MANAGER.setMaxTotal(128);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(32);
    }

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();

    public static CloseableHttpClient getInstance() {
        return HTTP_CLIENT;
    }
}
