package Server.Handler;

import DataAccess.DataAccessException;
import Request1.LoginRequest;
import Response1.LoginResponse;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.imageio.IIOException;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoginService ls = new LoginService();
                Gson gson = new Gson();
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                LoginRequest request = gson.fromJson(reader, LoginRequest.class);
                LoginResponse lr = ls.loginService(request);

                if(lr.getSuccess())
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                }
                String inJson = gson.toJson(lr);
                OutputStream respBody = exchange.getResponseBody();
                writeString(inJson, respBody);
                respBody.close();
                System.out.println("sent response for login");
                //exchange.close();
            }
        }
        catch (IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException
    {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
