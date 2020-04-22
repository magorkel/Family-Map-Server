package Server.Handler;

import Request1.RegisterRequest;
import Response1.RegisterResponse;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        //boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                RegisterService rs = new RegisterService();
                InputStream is = exchange.getRequestBody();
                Gson gson = new Gson();
                String temp = readString(is);
                RegisterRequest request = gson.fromJson(temp, RegisterRequest.class);

                RegisterResponse response = rs.registerService(request);
                String jsonInString = gson.toJson(response);

                if (response.getSuccess())
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                    //success = true;
                }
                else
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonInString, respBody);
                respBody.close();
                //success = true;//needs to go in the first if?
            }
            /*if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }*/
        }
        catch(IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
