package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public interface HttpHelper {

    /**
     * Execute a HTTP Post call
     *
     * @param uri
     * @param stringEntity
     * @return
     */
    HttpResponse httpPost(URI uri, StringEntity stringEntity);

    /**
     * Execute a HTTP Get call
     * @param uri
     * @return
     */
    HttpResponse httpGet(URI uri);


    HttpResponse httpDelete(URI uri);

}