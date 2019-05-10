package com.tokeninc.altay.utilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Okan Engin Başar on 9.05.2019.
 */
public abstract class ConnectionUtility {

    private static final String CLIENT_ID = "7b441ce9-eb24-4375-bc54-6e340e660d93";
    private static final String CLIENT_SECRET = "gC3xQ8eP6bG4xP3sV2tH8hF7eJ7kX8fF1pE6tQ8xJ0sY7hD4vU";

    private static final String JSON_CONTENT_TYPE_STRING = "application/json";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse(JSON_CONTENT_TYPE_STRING);

    public static final String GET_QR_SALE_URL_STRING = "https://sandbox-api.payosy.com/api/get_qr_sale";
    public static final String PAYMENT_URL_STRING = "https://sandbox-api.payosy.com/api/payment";

    public static void post(String url, String content, Callback callback) {
        //OkHttpClient client = new OkHttpClient(); // This is the safe client!
        OkHttpClient client = getUnsafeOkHttpClient(); // The unsafe client is used since the sandbox-developer.payosy.com site has an expired certificate.

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("x-ibm-client-id", CLIENT_ID)
                .addHeader("x-ibm-client-secret", CLIENT_SECRET)
                .addHeader("content-type", JSON_CONTENT_TYPE_STRING)
                .addHeader("accept", JSON_CONTENT_TYPE_STRING)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.onResponse(call, response);
            }
        });
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
