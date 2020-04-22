package Model1;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Names
{
    private NameList mnames = null;
    private NameList fnames = null;
    private NameList lnames = null;

    public void readNames() throws FileNotFoundException
    {
        FileReader MNames = new FileReader("fnames.json");
        FileReader FNames = new FileReader("mnames.json");
        FileReader LNames = new FileReader("snames.json");

        Scanner mscanner = new Scanner(MNames);
        mnames = read(mscanner);
        Scanner fscanner = new Scanner(FNames);
        fnames = read(fscanner);
        Scanner lscanner = new Scanner(LNames);
        lnames = read(lscanner);
    }
    private NameList read(Scanner scanner)
    {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext())
        {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        return gson.fromJson(sb.toString(), NameList.class);
    }
    public String getMomName()
    {
        Random num = new Random();
        int rnum = num.nextInt(mnames.getSize());
        return mnames.getNameAt(rnum);
    }
    public String getDadName()
    {
        Random num = new Random();
        int rnum = num.nextInt(fnames.getSize());
        return fnames.getNameAt(rnum);
    }
    public String getLastName()
    {
        Random num = new Random();
        int rnum = num.nextInt(lnames.getSize());
        return lnames.getNameAt(rnum);
    }
}
