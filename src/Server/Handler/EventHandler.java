package Server.Handler;

import Model1.Event;
import Response1.EventResponse;
import Response1.SingleEventResponse;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class EventHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                String fillLine = exchange.getRequestURI().toString();
                Scanner scanner = new Scanner(fillLine);
                scanner.useDelimiter("/");
                String eventWord = scanner.next();
                boolean yesNext = scanner.hasNext();
                String eventID = null;
                if(yesNext) { eventID = scanner.next(); }

                EventService es = new EventService();
                Headers header = exchange.getRequestHeaders();
                if (header.containsKey("Authorization"))
                {
                    String authToken = header.getFirst("Authorization");
                    Gson gson = new Gson();
                    EventResponse response = es.eventService(authToken, eventID);
                    String jsonInString;

                    if ((eventID != null) && (response.getData() != null))
                    {
                        Event event = response.getData().get(0);
                        SingleEventResponse singleEventResponse = new SingleEventResponse(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), response.getMessage(), response.getSuccess());
                        jsonInString = gson.toJson(singleEventResponse);

                        if (singleEventResponse.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    else
                    {
                        jsonInString = gson.toJson(response);

                        if (response.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_OK, 0);
                        } else
                        {
                            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonInString, respBody);
                    respBody.close();
                    //success = true;
                }
            }
            /*if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }*/
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
