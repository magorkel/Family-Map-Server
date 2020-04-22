package Server.Handler;

import Model1.Person;
import Response1.PersonResponse;
import Response1.SinglePersonResponse;
import Service.PersonService;
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

public class PersonHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        //boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                String fillLine = exchange.getRequestURI().toString();//////////check to make sure userName is the same as well
                Scanner scanner = new Scanner(fillLine);
                scanner.useDelimiter("/");
                String personWord = scanner.next();
                boolean yesNext = scanner.hasNext();
                String personID = null;
                if (yesNext) { personID = scanner.next(); }

                PersonService ps = new PersonService();
                Headers header = exchange.getRequestHeaders();
                if (header.containsKey("Authorization"))
                {
                    String authToken = header.getFirst("Authorization");
                    Gson gson = new Gson();
                    PersonResponse response = ps.personService(authToken, personID);
                    String jsonInString;

                    if ((personID != null) && (response.getData() != null))//
                    {
                        Person person = response.getData().get(0);
                        SinglePersonResponse singlePersonResponse = new SinglePersonResponse(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), response.getMessage(), response.getSuccess());
                        jsonInString = gson.toJson(singlePersonResponse);

                        if (singlePersonResponse.getSuccess())
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
                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpsURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }


                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonInString, respBody);
                    respBody.close();
                    //success = true;
                }
                /*if (!success)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }*/
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
