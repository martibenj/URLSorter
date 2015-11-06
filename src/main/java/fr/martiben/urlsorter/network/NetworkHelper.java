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

import fr.martiben.urlsorter.constante.Constants;

/**
 * Helper for Connections
 * 
 * @author B-Martinelli
 */
public final class NetworkHelper
{
  /**
   * Connection Reader.
   * 
   * @param reader
   *          The Reader
   * @return the message red
   * @throws IOException
   *           Exception in reading
   */
  private static String readAll(Reader reader) throws IOException

  {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = reader.read()) != -1)
    {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  /**
   * Read JSON message from an URL
   * 
   * @param url
   *          the target URL
   * @return the JSON object red
   * @throws JSONException
   *           Error on parsing
   * @throws IOException
   *           Error on communication
   */
  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
  {
    Proxy proxy = new Proxy(Proxy.Type.HTTP,
        new InetSocketAddress(Constants.PROXY_HOST, Constants.PROXY_PORT));
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
