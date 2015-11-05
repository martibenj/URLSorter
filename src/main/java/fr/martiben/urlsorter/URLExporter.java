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
import fr.martiben.urlsorter.constante.Constantes;
import fr.martiben.urlsorter.network.NetworkHelper;
import fr.martiben.urlsorter.pojo.Episode;

/**
 * URL Exporter from Playlist to URL collections sorted
 * 
 * @author B-Martinelli
 */
public class URLExporter
{
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
  public List<Episode> URLSorter(final String baseUrl) throws IOException, JSONException
  {
    // Declarations
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    final List<JSONObject> listJson = new ArrayList<JSONObject>();
    final List<Episode> listEpisodeReturned = new ArrayList<Episode>();

    // Searching in playlist
    int nbPage = 1;
    listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, Constantes.MAX_LIMIT_PER_REQUEST,
        nbPage)));
    while ((Boolean) listJson.get(listJson.size() - 1).get("has_more"))
    {
      nbPage++;
      listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, Constantes.MAX_LIMIT_PER_REQUEST,
          nbPage)));
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
        listEpisodeReturned.add(Episode.parseEpisodeFromURL(urlElement.get("url").toString()));
      }
    }

    // Sorting results
    Collections.sort(listEpisodeReturned, new VideoComparator());
    return listEpisodeReturned;
  }

  public static Boolean listChecker(List<Episode> listUrl, int... limitSeason)
  {
    Boolean retour = Boolean.FALSE;

    return retour;
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
    baseUrlReturn = baseUrlReturn.replace(Constantes.TOKEN_LIMIT, limit.toString());
    return baseUrlReturn.replace(Constantes.TOKEN_PAGE, page.toString());
  }
}
