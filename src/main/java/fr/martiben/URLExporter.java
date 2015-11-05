package fr.martiben;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.martiben.comparator.VideoComparator;
import fr.martiben.network.NetworkHelper;

public class URLExporter
{
  private static int          MAX_LIMIT_PER_REQUEST = 100;

  private static final String baseUrl               = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
                                                        + MAX_LIMIT_PER_REQUEST + "&page=";

  public static void main(String[] args) throws IOException, JSONException
  {
    // Declarations
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    Iterator<String> itURL = null;
    Boolean hasMore = Boolean.FALSE;
    final List<JSONObject> listJson = new ArrayList<JSONObject>();
    final List<String> listURL = new ArrayList<String>();

    // Searching in playlist
    int nbPage = 1;
    listJson.add(NetworkHelper.readJsonFromUrl(baseUrl + nbPage));
    hasMore = (Boolean) listJson.get(0).get("has_more");
    while (hasMore)
    {
      nbPage++;
      listJson.add(NetworkHelper.readJsonFromUrl(baseUrl + nbPage));
      hasMore = (Boolean) listJson.get(listJson.size() - 1).get("has_more");
    }

    // Reading results
    itPages = listJson.iterator();
    while (itPages.hasNext())
    {
      resultSearchElement = itPages.next();
      jsonArrayURL = resultSearchElement.getJSONArray("list");
      itObjects = jsonArrayURL.iterator();
      while (itObjects.hasNext())
      {
        urlElement = (JSONObject) itObjects.next();
        listURL.add(urlElement.get("url").toString());
      }
    }

    // Sorting results
    Collections.sort(listURL, new VideoComparator());

    // Showing Results
    itURL = listURL.iterator();
    while (itURL.hasNext())
    {
      System.out.println(itURL.next());
    }
  }
}
