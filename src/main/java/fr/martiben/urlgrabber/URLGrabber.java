package fr.martiben.urlgrabber;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.martiben.urlgrabber.constante.Constants;
import fr.martiben.urlgrabber.network.NetworkHelper;
import fr.martiben.urlgrabber.pojo.Episode;
import fr.martiben.urlgrabber.pojo.Provider;

/**
 * URL Grabber from online video playlists to URL collections.
 * 
 * @author B-Martinelli
 */
public class URLGrabber
{
  /**
   * URL Grabber from online video playlists to URL collections.
   * 
   * @param pBaseUrl
   *          Base Url with tokens for playlist
   * @throws IOException
   *           Connection exception when connecting to the online video playlist
   * @throws JSONException
   *           JSON parsing exception
   */
  public Map<Episode, Episode> urlGrabber(final String pBaseUrl) throws IOException, JSONException
  {
    // Declarations
    List<JSONObject> listJsonResponses = null;
    Provider provider = Provider.resolve(pBaseUrl);

    // Search in playlist
    listJsonResponses = playlistGrabberForProvider(provider, pBaseUrl);

    // Reading results
    return extractVideosFromJSONResponses(provider, listJsonResponses);
  }

  /**
   * Search Video info from a Provider's playlist.
   * 
   * @param pProvider
   *          Provider of the online playlist
   * @param pBaseUrl
   *          Base Url with tokens for playlist
   * @return a list of JSON Response Objects
   * @throws IOException
   *           IO in connection to provider
   */
  private List<JSONObject> playlistGrabberForProvider(Provider pProvider, String pBaseUrl) throws IOException
  {
    List<JSONObject> retour = new ArrayList<JSONObject>();
    String urlToCall = null;
    Integer maxLimitPerRequest = pProvider.getMaxLimitPerRequest();

    String nbPage = pProvider.initNextPageCursor();
    urlToCall = baseUrlBuilder(pBaseUrl, maxLimitPerRequest, nbPage);
    retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
    while (pProvider.hasMoreElements(retour))
    {
      nbPage = pProvider.incrementNextPageCursor(nbPage, retour.get(retour.size() - 1));
      urlToCall = baseUrlBuilder(pBaseUrl, maxLimitPerRequest, nbPage);
      retour.add(NetworkHelper.readJsonFromUrl(urlToCall));
    }
    return retour;
  }

  /**
   * Extract Videos data from JSON Response Objects.
   * 
   * @param pListResponses
   *          Collection of Json Responses
   * @param pProvider
   *          Provider of URL
   * @return Map of Episodes
   * @throws MalformedURLException
   *           Error in URL Parsing
   */
  private Map<Episode, Episode> extractVideosFromJSONResponses(Provider pProvider,
      final List<JSONObject> pListResponses) throws MalformedURLException
  {
    final Map<Episode, Episode> mapEpisodeReturned = new HashMap<Episode, Episode>();
    JSONObject resultSearchElement = null;
    JSONObject urlElement = null;
    JSONArray jsonArrayURL = null;
    Iterator<?> itObjects = null;
    Iterator<JSONObject> itPages = null;
    Episode episode = null;

    itPages = pListResponses.iterator();
    while (itPages.hasNext())
    {
      resultSearchElement = itPages.next();
      jsonArrayURL = resultSearchElement.getJSONArray(pProvider.getJSONElementName());

      itObjects = jsonArrayURL.iterator();
      while (itObjects.hasNext())
      {
        urlElement = (JSONObject) itObjects.next();
        episode = pProvider.getEpisodeFromJSONURLElement(urlElement);
        mapEpisodeReturned.put(episode, episode);
      }
    }
    return mapEpisodeReturned;
  }

  /**
   * Check if any episodes are missing in a season.
   * 
   * @param collectionEpisodes
   *          the initial collection of episodes to check
   * @param limitsForSeasons
   *          a list of limit for seasons in order. First param is for season 1, second param is for season 2,
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
