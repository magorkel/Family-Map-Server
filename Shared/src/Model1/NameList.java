package Model1;

import java.util.ArrayList;

public class NameList
{
    private ArrayList<String> data;

    public NameList(ArrayList<String> name)
    {
        this.data = name;
    }

    public String getNameAt(int i)
    {
        return data.get(i);
    }

    public int getSize()
    {
        return data.size();
    }
}
