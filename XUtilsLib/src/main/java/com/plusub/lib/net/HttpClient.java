package com.plusub.lib.net;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.constant.NetConstant;
import com.plusub.lib.util.TextUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * http get and post method
 * @author Administrator
 * <b>#BaseListActivity#
 */
public class HttpClient{

	protected static final Logger LOG = Logger.getLogger(HttpClient.class.getCanonicalName());

    private static final String DEFAULT_CLIENT_VERSION = "com.plusub";
    private static final String CLIENT_VERSION_HEADER = "User-Agent";
//    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
//    private static final String ENCODING_GZIP = "gzip";
    private static final int TIMEOUT = NetConstant.TIME_OUT;

    private final DefaultHttpClient mHttpClient;
    private final String mClientVersion;

    /**
     * Http utility
     * @param httpClient	
     * @param clientVersion allow null
     */
    public HttpClient(DefaultHttpClient httpClient, String clientVersion) {
        mHttpClient = httpClient;
        if (clientVersion != null) {
            mClientVersion = clientVersion;
        } else {
            mClientVersion = DEFAULT_CLIENT_VERSION;
        }
    }
    
    /**
     * http post method
     * <p>Title: doHttpPost
     * <p>Description: 
     * @param url
     * @param entity
     * @return
     * @throws Exception
     */
    public String doHttpPost(String url, HttpEntity entity, boolean haveFile) throws Exception{
    	HttpPost httpPost = createHttpPost(url, entity, haveFile);
    	HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity(),"UTF-8");
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }

    /**
     * execute a HttpPost
     * @return string
     */
    public String doHttpPost(String url, NameValuePair... nameValuePairs)
            throws Exception {
        HttpPost httpPost = createHttpPost(url, nameValuePairs);
        HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity(),"UTF-8");
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }
    
    /**
     * execute a HttpPost
     * @return InputStream
     */
    public InputStream doHttpPost2(String url, NameValuePair... nameValuePairs)
            throws Exception {
        HttpPost httpPost = createHttpPost(url, nameValuePairs);
        HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return response.getEntity().getContent();
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }
    
    public String doMultiHttpPost(String url,Map<String, File> fileMap, NameValuePair... nameValuePairs)
    		throws Exception{
		HttpPost httpPost = createMultiHttpPost(url, fileMap, nameValuePairs);
		HttpResponse response = executeHttpRequest(httpPost);
		switch (response.getStatusLine().getStatusCode()) {
			case 200:
				try {
					return EntityUtils.toString(response.getEntity(),"UTF-8");
				} catch (ParseException e) {
					throw new Exception(e.getMessage());
				}
			default:
				response.getEntity().consumeContent();
				throw new Exception(response.getStatusLine().toString());
		}
    }
    
    /**
     * execute a HttpGet
     * @param url
     * @param nameValuePairs
     * @return
     * @throws Exception
     */
    public String doHttpGet(String url, NameValuePair... nameValuePairs ) throws Exception{
    	 HttpGet httpGet = createHttpGet(url, nameValuePairs);
         HttpResponse response = executeHttpRequest(httpGet);

         switch (response.getStatusLine().getStatusCode()) {
             case 200:
                 try {
                     return EntityUtils.toString(response.getEntity());
                 } catch (ParseException e) {
                     throw new Exception(e.getMessage());
                 }

             case 401:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             case 404:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             default:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());
         }
    }
    
    /**
     * execute a HttpGet
     * @param url
     * @param nameValuePairs
     * @return
     * @throws Exception
     */
    public InputStream doHttpGet2(String url, NameValuePair... nameValuePairs ) throws Exception{
    	 HttpGet httpGet = createHttpGet(url, nameValuePairs);

         HttpResponse response = executeHttpRequest(httpGet);

         switch (response.getStatusLine().getStatusCode()) {
             case 200:
                 try {
                     return response.getEntity().getContent();
                 } catch (ParseException e) {
                     throw new Exception(e.getMessage());
                 }

             case 401:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             case 404:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             default:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());
         }
    }

    /**
     * execute() an httpRequest catching exceptions and returning null instead.
     *
     * @param httpRequest
     * @return
     * @throws IOException
     */
    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) throws IOException {
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
            return mHttpClient.execute(httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw e;
        }
    }

    
    /***
     * create multi httpPost
     * @param url
     * @param fileMap
     * @param nameValuePairs
     * @return
     */
    public HttpPost createMultiHttpPost(String url,Map<String,File> fileMap, NameValuePair... nameValuePairs) {
        HttpPost httpPost = new HttpPost(url);
        if (TextUtils.notEmpty(BaseApplication.getInstance().getSessionId())) {
            httpPost.setHeader("Cookie", "JSESSIONID="+BaseApplication.getInstance().getSessionId());
		}
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
//        if (!httpPost.containsHeader(HEADER_ACCEPT_ENCODING)) {
//            httpPost.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
//        }
        //使用MultipartEntity来封装参数
        MultipartEntity entity = new MultipartEntity();
        Charset chars = Charset.forName("UTF-8");
        //添加其他数据，字符数据
        for (int i = 0; i < nameValuePairs.length; i++) {
            NameValuePair param = nameValuePairs[i];
            if (param.getValue() != null) {
            	try {
					entity.addPart(param.getName(), new StringBody(param.getValue(),chars));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
            }
        }
        //添加上传文件，可以是多个
        for (String name : fileMap.keySet()) {
        	File value=fileMap.get(name);
			if(value!=null&&value.exists()){
				entity.addPart(name, new FileBody(value));  
			}
		}
        
        httpPost.setEntity(entity); 
        return httpPost;
	}
    
    /**
     * create HttpGet
     */
    public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
        String query = URLEncodedUtils.format(createParams(nameValuePairs), HTTP.UTF_8);
        HttpGet httpGet = new HttpGet(url + "?" + query);
        if (TextUtils.notEmpty(BaseApplication.getInstance().getSessionId())) {
			httpGet.setHeader("Cookie", "JSESSIONID=" +BaseApplication.getInstance().getSessionId());
        }
        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
//        if (!httpGet.containsHeader(HEADER_ACCEPT_ENCODING)) {
//            httpGet.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
//        }
        return httpGet;
    }
    
    /**
     * create http post
     * <p>Title: createHttpPost
     * <p>Description: 
     * @param url
     * @param entity
     * @return
     */
    public HttpPost createHttpPost(String url, HttpEntity entity, boolean haveFile) {
    	HttpPost httpPost = new HttpPost(url);
    	if (TextUtils.notEmpty(BaseApplication.getInstance().getSessionId())) {
            httpPost.setHeader("Cookie", "JSESSIONID="+BaseApplication.getInstance().getSessionId());
		}
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
//        if (!httpPost.containsHeader(HEADER_ACCEPT_ENCODING)) {
//            httpPost.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
//        }
        //解决utf-8，服务器接收乱码(不包含文件)
        if (!haveFile) {
        	httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		}
        httpPost.setEntity(entity);
        return httpPost;
    }

    /**
     * create HttpPost
     */
    public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
        HttpPost httpPost = new HttpPost(url);
        if (TextUtils.notEmpty(BaseApplication.getInstance().getSessionId())) {
            httpPost.setHeader("Cookie", "JSESSIONID="+BaseApplication.getInstance().getSessionId());
		}
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        if (!httpPost.containsHeader(HEADER_ACCEPT_ENCODING)) {
//            httpPost.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
//        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(createParams(nameValuePairs), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new IllegalArgumentException("Unable to encode http parameters.");
        }
        return httpPost;
    }

    /**
     * create parameter
     * @param nameValuePairs
     * @return
     */
    private List<NameValuePair> createParams(NameValuePair... nameValuePairs) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < nameValuePairs.length; i++) {
            NameValuePair param = nameValuePairs[i];
            if (param.getValue() != null) {
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Create a thread-safe client. This client does not do redirecting, to allow us to capture
     * correct "error" codes.
     *
     * @return HttpClient
     */
    public static final DefaultHttpClient createHttpClient() {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));

        // Set some client http client parameter defaults.
        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);

        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpParams,
                supportedSchemes);
        return new DefaultHttpClient(ccm, httpParams);
    }

    /**
     * Create the default HTTP protocol parameters.
     */
    private static final HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        return params;
    }

	public Object getConnectionManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpResponse execute(HttpPost httppost) {
		// TODO Auto-generated method stub
		return null;
	}
}
