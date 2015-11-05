package fr.martiben.urlsorter.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public final class NetworkHelper
{
  private static String readAll(Reader rd) throws IOException
  {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1)
    {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
  {
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ecprox.bull.fr", 80));
    URLConnection urlC = new URL(url).openConnection(proxy);
    InputStream is = urlC.getInputStream();

    try
    {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      return new JSONObject(jsonText);
    }
    finally
    {
      is.close();
    }
  }
}
