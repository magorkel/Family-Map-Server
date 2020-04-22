package Server.Handler;

import Request1.LoadRequest;
import Response1.LoadResponse;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoadService ls = new LoadService();
                InputStream is = exchange.getRequestBody();
                Gson gson = new Gson();
                LoadRequest request = new Gson().fromJson(readString(is), LoadRequest.class);
                LoadResponse lr = ls.loadService(request);
                String jsonInString = gson.toJson(lr);

                if (lr.getSuccess())
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonInString, respBody);
                respBody.close();
                success = true;
            }
            if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                LoadResponse loadResponse = new LoadResponse("Load was not successful", false);
                String inJson = new Gson().toJson(loadResponse);
                writeString(inJson, respBody);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = exchange.getResponseBody();
            LoadResponse loadResponse = new LoadResponse("Load was not successful", false);
            String inJson = new Gson().toJson(loadResponse);
            writeString(inJson, respBody);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    private String readString(InputStream is ) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char [] buf = new char[1024];
        int len;
        while((len = sr.read(buf)) > 0)
        {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    private void writeString(String str, OutputStream os) throws IOException
    {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
