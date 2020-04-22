package Model1;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Locations
{
    private ArrayList<Location> data = null;

    public static Locations readFile() throws FileNotFoundException
    {
        FileReader file = new FileReader("locations.json");
        Scanner scanner = new Scanner(file);
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext())
        {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        return gson.fromJson(sb.toString(), Locations.class);
    }

    public Location getLocation()
    {
        Random num = new Random();
        int rnum = num.nextInt(data.size());
        return data.get(rnum);
    }
}
