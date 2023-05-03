package ca.jrvs.apps.twitter.dao.helper;

import org.json.simple.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws Exception{
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        System.out.println("Keys: " + consumerKey + " | " + consumerSecret + " | " + accessToken + " | " + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);


        String status = "You Can Do That!";
        JSONObject json_string = new JSONObject();
        json_string.put("text", status);

        HttpResponse httpResponse = httpHelper.httpPost(new URI("https://api.twitter.com/2/tweets"), new StringEntity(json_string.toJSONString()));
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }
}