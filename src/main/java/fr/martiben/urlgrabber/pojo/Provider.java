package fr.martiben.urlgrabber.pojo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;

import org.json.JSONObject;

import fr.martiben.urlgrabber.constante.Constants;

/**
 * Provider of URL. Contains a list of method for grabbing URL following Provider rules.
 * 
 * @author B-Martinelli
 */
public enum Provider
{
  /** Youtube Provider. */
  YOUTUBE,

  /** Dailymotion Provider. */
  DAILYMOTION;

  /**
   * Resolve Provider value from URL
   * 
   * @param baseURL
   *          URL to parse
   * @return The Provider value
   */
  public static Provider resolve(final String baseURL)
  {
    Provider retour = null;
    Matcher matcherProvider = Constants.PATTERN_PROVIDER.matcher(baseURL);
    if (matcherProvider.find() == false)
    {
      throw new UnsupportedOperationException("Pattern not found in URL");
    }
    switch (matcherProvider.group())
    {
      case Constants.TERM_PROVIDER_YOUTUBE:
        retour = Provider.YOUTUBE;
        break;

      case Constants.TERM_PROVIDER_DAILYMOTION:
        retour = Provider.DAILYMOTION;
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;

  }

  /**
   * Get name of JSON element in JSON Response for the items list.
   * 
   * @return The name of JSON element in JSON Response for the items list
   */
  public String getJSONElementName()
  {
    String retour = null;
    switch (this)
    {
      case YOUTUBE:
        retour = "items";
        break;

      case DAILYMOTION:
        retour = "list";
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }

  /**
   * Build an Episode from an URL extracted from a JSON Response
   * 
   * @param urlElement
   *          A JSON UrlElement
   * @return An Episode
   * @throws MalformedURLException
   *           Error in URL Parsing
   */
  public Episode getEpisodeFromJSONURLElement(JSONObject urlElement) throws MalformedURLException
  {
    Episode retour = null;
    String url = null;
    switch (this)
    {
      case YOUTUBE:
        String videoId = (String) ((JSONObject) urlElement.get("id")).get("videoId");
        url = Constants.WATCH_YOUTUBE_TEMPLATE + videoId;
        retour = new Episode(new Integer(0), new Integer(0), new URL(url));
        break;

      case DAILYMOTION:
        url = urlElement.get("url").toString();
        retour = Episode.parseEpisodeFromURL(url);
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }

  /**
   * Get the max limit of element asked per request.
   * 
   * @return The max limit of element asked per request.
   */
  public Integer getMaxLimitPerRequest()
  {
    Integer retour = null;
    switch (this)
    {
      case YOUTUBE:
        retour = Constants.MAX_LIMIT_PER_REQUEST_YOUTUBE;
        break;

      case DAILYMOTION:
        retour = Constants.MAX_LIMIT_PER_REQUEST_DAILYMOTION;
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }

  /**
   * Check if the list has more elements to ask for and need another connection to get another page of
   * results.
   * 
   * @param pListElements
   *          The list of element to check
   * @return true if the list has more page/elements to ask ; false if not
   */
  public boolean hasMoreElements(final List<JSONObject> pListElements)
  {
    boolean retour = false;
    switch (this)
    {
      case YOUTUBE:
        retour = pListElements.get(pListElements.size() - 1).has("nextPageToken");
        break;

      case DAILYMOTION:
        retour = (Boolean) pListElements.get(pListElements.size() - 1).get("has_more");
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }

  /**
   * Initialize the page cursor following the provider rules.
   * 
   * @return empty if youtube, 1 if dailymotion
   */
  public String initNextPageCursor()
  {
    String retour = "";
    switch (this)
    {
      case YOUTUBE:
        retour = "";
        break;

      case DAILYMOTION:
        retour = "1";
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }

  /**
   * Increment the page cursor following the provider rules.
   * 
   * @param nbPage
   *          the current cursor value
   * @param pJsonObject
   *          if needed, object containing more infos
   * @return the page cursor incremented following the provider rules
   */
  public String incrementNextPageCursor(final String nbPage, JSONObject pJsonObject)
  {
    String retour = "";
    switch (this)
    {
      case YOUTUBE:
        retour = (String) pJsonObject.get("nextPageToken");
        break;

      case DAILYMOTION:
        retour = "" + (Integer.parseInt(nbPage) + 1);
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;
  }
}
