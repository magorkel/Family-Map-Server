package Server.Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

import static java.net.HttpURLConnection.HTTP_OK;

public class FileHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                String urlPath = exchange.getRequestURI().toString();
                if (urlPath == null || urlPath.equals("/"))
                {
                    urlPath = "/index.html";
                }
                else if (urlPath.contains("css") || urlPath.contains("img"))
                {
                    urlPath = urlPath;
                }
                else if (!urlPath.contains("index.html"))
                {
                    urlPath = "/HTML/404.html";
                }
                String filePath = "web" + urlPath;
                File file = new File(filePath);
                //boolean exists = file.exists();
                if (filePath.contains("404"))
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    /*OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();*/
                }
                else
                {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }
        }
        catch(IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
