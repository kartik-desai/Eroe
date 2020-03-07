package com.example.eroe;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;
/**
 * Created by Kartik on 30-01-2018.
 */
public class SendData {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
    public static String sendData(String f,JSONObject p) throws Exception {
        URL url = new URL("http://10.110.7.225:8085/Eroep/"+f);
        //URL url = new URL("http://hitfit.com/"+f);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000/* milliseconds */);
        conn.setConnectTimeout(15000/* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(p));
        writer.flush();
        writer.close();
        os.close();
        int responseCode=conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in=new BufferedReader(new
                    InputStreamReader(
                    conn.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            conn.disconnect();
            return sb.toString();
        }
        else {
            conn.disconnect();
            return new String("false : "+responseCode);
        }
    }
    public static String searchItems(String f) throws Exception{
        String result="";
        String inputLine;
        URL myUrl = new URL(f);
        //Create a connection
        HttpURLConnection connection = (HttpURLConnection)
                myUrl.openConnection();
        //Set methods and timeouts
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        //Connect to our url
        connection.connect();
        //Create a new InputStreamReader
        InputStreamReader streamReader = new
                InputStreamReader(connection.getInputStream());
        //Create a new buffered reader and String Builder
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        //Check if the line we are reading is not null
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        //Close our InputStream and Buffered reader
        reader.close();
        streamReader.close();
        //Set our result equal to our stringBuilder
        result = stringBuilder.toString();
        return result;
    }
}