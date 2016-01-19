package fr.martiben.urlsorter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import fr.martiben.urlsorter.pojo.Provider;

/**
 * URL Sorter from Playlist to URL collections sorted
 * 
 * @author B-Martinelli
 */
public class URLSorter
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
  public Map<Episode, Episode> urlSortingMachine(final String baseUrl) throws IOException, JSONException
  {
    // Declarations
    List<JSONObject> listJson = null;
    Provider provider = Provider.resolver(baseUrl);

    // Search in playlist
    listJson = videoRequesterFromProvider(baseUrl, provider);

    // Reading results
    return extractData(listJson, provider);
  }

  /**
   * Search Video info from a Provider.
   * 
   * @param baseUrl
   *          Base Url with tokens for playlist
   * @param provider
   *          Provider of URL
   * @return a list of JSON Response Objects
   * @throws IOException
   *           IO in connection to provider
   */
  private List<JSONObject> videoRequesterFromProvider(String baseUrl, Provider provider) throws IOException
  {
    List<JSONObject> retour = new ArrayList<JSONObject>();
    String urlToCall = null;
    if (Provider.YOUTUBE.equals(provider))
    {
      String idPage = "";
      urlToCall = baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST_YOUTUBE, idPage);
      retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
      while (retour.get(retour.size() - 1).has("nextPageToken"))
      {
        idPage = (String) retour.get(retour.size() - 1).get("nextPageToken");
        urlToCall = baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST_YOUTUBE, idPage);
        retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
      }
    }
    else if (Provider.DAILYMOTION.equals(provider))
    {
      int nbPage = 1;
      urlToCall = baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST_DAILYMOTION, "" + nbPage);
      retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
      while ((Boolean) retour.get(retour.size() - 1).get("has_more"))
      {
        nbPage++;
        urlToCall = baseUrlBuilder(baseUrl, Constants.MAX_LIMIT_PER_REQUEST_DAILYMOTION, "" + nbPage);
        retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
      }
    }
    return retour;
  }

  /**
   * Extract Data from JSON Response Objects.
   * 
   * @param listJson
   *          list of Json Responses
   * @param provider
   *          Provider of URL
   * @return Map of Episodes
   * @throws MalformedURLException
   *           Error in URL Parsing
   */
  private Map<Episode, Episode> extractData(final List<JSONObject> listJson, Provider provider)
      throws MalformedURLException
  {
    final Map<Episode, Episode> mapEpisodeReturned = new HashMap<Episode, Episode>();
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Episode episodeFromURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    String url = null;

    itPages = listJson.iterator();
    while (itPages.hasNext())
    {
      resultSearchElement = itPages.next();
      switch (provider)
      {
        case YOUTUBE:
          jsonArrayURL = resultSearchElement.getJSONArray("items");
          break;

        case DAILYMOTION:
          jsonArrayURL = resultSearchElement.getJSONArray("list");
          break;

        default:
          throw new UnsupportedOperationException("Provider not found in existing values");
      }

      itObjects = jsonArrayURL.iterator();
      while (itObjects.hasNext())
      {
        urlElement = (JSONObject) itObjects.next();
        switch (provider)
        {
          case YOUTUBE:
            String videoId = (String) ((JSONObject) urlElement.get("id")).get("videoId");
            url = Constants.WATCH_YOUTUBE_TEMPLATE + videoId;
            episodeFromURL = new Episode(new Integer(0), new Integer(0), new URL(url));
            break;

          case DAILYMOTION:
            url = urlElement.get("url").toString();
            episodeFromURL = Episode.parseEpisodeFromURL(url);
            break;

          default:
            throw new UnsupportedOperationException("Provider not found in existing values");
        }
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
  private String baseUrlBuilder(final String baseUrl, final Integer limit, final String page)
  {
    String baseUrlReturn = baseUrl.toString();
    baseUrlReturn = baseUrlReturn.replace(Constants.TOKEN_LIMIT, limit.toString());
    return baseUrlReturn.replace(Constants.TOKEN_PAGE, page.toString());
  }

}
