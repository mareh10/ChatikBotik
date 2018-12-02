package iomanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RequestToURL
{
    public static ArrayList<String> openUrl(String pageUrl)
    {
        try {
            var list = new ArrayList<String>();
            var url = new URL(pageUrl);
            var urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
//                System.out.println(inputLine);
                list.add(inputLine);
            }
            br.close();

            return list;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
