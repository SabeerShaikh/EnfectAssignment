package com.e.enfecassigmentkotlin.config;

import android.app.Application;
import android.util.Log;

import com.e.enfecassigmentkotlin.BuildConfig;
import com.e.enfecassigmentkotlin.domain.repository.MainRepository;
import com.e.enfecassigmentkotlin.domain.repository.MainRepositoryImpl;
import com.e.enfecassigmentkotlin.module.base.EnfecProducationViewModelFactory;
import com.enfecassignment.domain.repository.remote.api.MainService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EnfecProducationDI implements DI {

    public static final String HEADER_AUTHORIZTION = "Authorization";
    /**
     * 512MB of video cache
     */
    private static final long DEFAULT_MAX_VIDEO_CACHE_SIZE = 536870912L;
    protected static Retrofit singletonRetrofit;
    protected static EnfecProducationViewModelFactory singletonStarGazeCommonVMFactory;

    /**
     * 2 min request timeout wait
     **/
    protected final long REQUEST_TIME_OUT_S = 180L;
    /**
     * 10MB cache for okhttp, if required
     **/
    protected final long HTTP_CACHE_SIZE_MB = 10 * 1024 * 1024;
    /**
     * shared preference db
     **/
    protected final String DB_KEY_VALUE = "school_key_value.db";
    protected final String HEADER_CONTENT_TYPE = "Content-Type";
    protected final String JSON_CONTENT = "application/json";
    protected final Application application;

    public EnfecProducationDI(Application application) {
        this.application = application;
    }

    /*@Override
    public KeyValueDao provideKeyValueDataStore() {
        return new SharedPrefKeyValueDao(
                provideApplication().getSharedPreferences(DB_KEY_VALUE,
                        Context.MODE_PRIVATE));
    }*/
/*

    @Override
    public HashMap<String, String> getAuthHeader() {
        HashMap<String, String> authHeader = new HashMap<>();
        authHeader.put(HEADER_AUTHORIZTION,
                "");
        authHeader.put(HEADER_CONTENT_TYPE, "text/plain");
        return authHeader;
    }
*/

    @Override
    public MainRepository provideMainRepository() {
        return new MainRepositoryImpl(provideMainService());
    }

    @Override
    public MainService provideMainService() {

        return provideRetrofit().create(MainService.class);
    }


    @Override
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.readTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);
        okHttpClientBuilder.connectTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);


        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(HEADER_AUTHORIZTION,
                                "")
                        .build();
                return chain.proceed(request);
            }
        });
        disableSSLCertificateChecking();
        return okHttpClientBuilder.build();
    }

    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // not implemented
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Application provideApplication() {
        return application;
    }

    @Override
    public Retrofit provideRetrofit() {
        if (singletonRetrofit == null) {
            synchronized (Retrofit.class) {
                singletonRetrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(provideOkHttpClient())
                        .baseUrl(BuildConfig.API_SERVER)
                        .build();
            }
        }
        Log.d("Response", "" + singletonRetrofit.baseUrl());

        return singletonRetrofit;
    }


    @Override
    public EnfecProducationViewModelFactory provideViewModelFactory() {
        if (singletonStarGazeCommonVMFactory == null) {
            synchronized (EnfecProducationViewModelFactory.class) {
                singletonStarGazeCommonVMFactory = new EnfecProducationViewModelFactory(provideApplication());
            }
        }
        return singletonStarGazeCommonVMFactory;
    }

}
