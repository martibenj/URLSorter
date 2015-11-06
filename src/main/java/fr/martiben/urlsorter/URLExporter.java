package fr.martiben.urlsorter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.martiben.urlsorter.constante.Constants;
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
  public Map<Episode, Episode> URLSorter(final String baseUrl) throws IOException, JSONException
  {
    // Declarations
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Episode episodeFromURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    final List<JSONObject> listJson = new ArrayList<JSONObject>();
    final Map<Episode, Episode> mapEpisodeReturned = new HashMap<Episode, Episode>();

    // Searching in playlist
    int nbPage = 1;
    listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST,
        nbPage)));
    while ((Boolean) listJson.get(listJson.size() - 1).get("has_more"))
    {
      nbPage++;
      listJson.add(NetworkHelper.readJsonFromUrl(baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST,
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
        episodeFromURL = Episode.parseEpisodeFromURL(urlElement.get("url").toString());
        mapEpisodeReturned.put(episodeFromURL, episodeFromURL);
      }
    }
    return mapEpisodeReturned;
  }

  /**
   * Check if episode are missing in a season.
   * 
   * @param collectionEpisodes
   *          the initial collection of episodes to check
   * @param limitsForSeasons
   *          a list of limit for seasons in order. First param is for season 1, second param is for season 2
   *          etc...
   * @return The collection of episode that are missing in seasons
   */
  public static Map<Episode, Episode> missingEpisodeChecker(Map<Episode, Episode> collectionEpisodes,
      int... limitsForSeasons)
  {
    Map<Episode, Episode> retourMissingEp = new HashMap<Episode, Episode>();
    Episode epFromMap = null;
    Episode episodeToCheck = null;
    int episodeTested = 1;
    int seasonToCheck = 1;

    for (int limitOfASeason : limitsForSeasons)
    {
      seasonToCheck = 1;
      episodeTested = 1;
      while (episodeTested <= limitOfASeason)
      {
        episodeToCheck = new Episode(seasonToCheck, episodeTested, null);
        epFromMap = collectionEpisodes.get(episodeToCheck);
        if (epFromMap == null)
        {
          retourMissingEp.put(episodeToCheck, episodeToCheck);
        }
        episodeTested++;
      }
      seasonToCheck++;
    }
    return retourMissingEp;
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
    baseUrlReturn = baseUrlReturn.replace(Constants.TOKEN_LIMIT, limit.toString());
    return baseUrlReturn.replace(Constants.TOKEN_PAGE, page.toString());
  }
}
