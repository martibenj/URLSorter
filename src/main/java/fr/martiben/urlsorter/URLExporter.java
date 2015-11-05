package fr.martiben.urlsorter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.martiben.urlsorter.comparator.VideoComparator;
import fr.martiben.urlsorter.network.NetworkHelper;

/**
 * URL Exporter from Playlist to URL collections sorted
 * 
 * @author B-Martinelli
 */
public class URLExporter
{
  /** Max limit per Request. */
  private int                MAX_LIMIT_PER_REQUEST = 100;

  /** Token for param Limit. */
  public static final String TOKEN_LIMIT           = "%LIMIT%";

  /** Token for param Page. */
  public static final String TOKEN_PAGE            = "%PAGE%";

  /**
   * URL sorter from Playlist
   * 
   * @param baseUrl
   *          Base Url with tokens for playlist
   * @throws IOException
   *           Connection exception when connecting to playlist
   * @throws JSONException
   *           Json parsing exception
   */
  public void URLSorter(final String baseUrl) throws IOException, JSONException
  {
    // Declarations
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    Iterator<String> itURL = null;
    final List<JSONObject> listJson = new ArrayList<JSONObject>();
    final List<String> listURL = new ArrayList<String>();

    // Searching in playlist
    int nbPage = 1;
    listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, MAX_LIMIT_PER_REQUEST, nbPage)));
    while ((Boolean) listJson.get(listJson.size() - 1).get("has_more"))
    {
      nbPage++;
      listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, MAX_LIMIT_PER_REQUEST, nbPage)));
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

  /**
   * URL builder used to replace token in url
   * 
   * @param baseUrl
   *          base URL with tokens
   * @param limit
   *          limit for the search
   * @param page
   *          page number to fetch
   * @return The built URL with params
   */
  private String baseUrlBuilder(final String baseUrl, final Integer limit, final Integer page)
  {
    String baseUrlReturn = baseUrl.toString();
    baseUrlReturn = baseUrlReturn.replace(URLExporter.TOKEN_LIMIT, limit.toString());
    return baseUrlReturn.replace(URLExporter.TOKEN_PAGE, page.toString());
  }
}
