package Server.Handler;

import Response1.FillResponse;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class FillHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            String fillLine = exchange.getRequestURI().toString();
            Scanner scanner = new Scanner(fillLine);
            scanner.useDelimiter("/");
            String fill = scanner.next();
            String userName = scanner.next();
            boolean yesGenterations = scanner.hasNext();
            int generations = 4;
            if (yesGenterations) { generations = scanner.nextInt(); }

            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                FillService fs = new FillService();
                FillResponse fr = fs.fillService(userName, generations);

                if(fr.getSuccess())
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Gson gson = new Gson();
                String jsonInString = gson.toJson(fr);
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonInString, respBody);
                respBody.close();
                success = true;
            }
            if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e)
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
