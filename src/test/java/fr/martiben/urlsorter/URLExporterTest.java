package fr.martiben.urlsorter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import fr.martiben.urlsorter.constante.Constants;
import fr.martiben.urlsorter.helper.SortingHelper;
import fr.martiben.urlsorter.pojo.Episode;

/**
 * URL Exporter Test class.
 * 
 * @author B-Martinelli
 */
public class URLExporterTest
{
  /**
   * Test the fetch of url from a playlist.
   * 
   * @throws JSONException
   *           Error on parsing
   * @throws IOException
   *           Error on communication with daily
   */
  @Test
  public void PalmaShowExportTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constants.TOKEN_LIMIT + "&page=" + Constants.TOKEN_PAGE;
    Map<Episode, Episode> mapURL = new URLExporter().URLSorter(baseUrl);

    displayResults(mapURL, false, true);
  }

  /**
   * Test the fetch of url from a playlist and check if episodes are missing.
   * 
   * @throws JSONException
   *           Error on parsing
   * @throws IOException
   *           Error on communication with daily
   */
  @Test
  public void PalmaShowMissingEpisodeCheckerTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constants.TOKEN_LIMIT + "&page=" + Constants.TOKEN_PAGE;
    Map<Episode, Episode> mapEpisodes = URLExporter.missingEpisodeChecker(
        new URLExporter().URLSorter(baseUrl), 108, 86);
    displayResults(mapEpisodes, true, false);
    Assert.assertTrue("Objective is 0 delta, but is " + mapEpisodes.size(), mapEpisodes.size() == 0);
  }

  /**
   * Method to display the results from a Map
   * 
   * @param mapEpisodes
   *          The map to display
   */
  private void displayResults(Map<Episode, Episode> mapEpisodes, Boolean displayEpisodeId, Boolean displayURL)
  {

    // Showing Results
    Iterator<Episode> itEpisode = SortingHelper.SeasonSorter(mapEpisodes).iterator();
    Episode ep = null;
    while (itEpisode.hasNext())
    {
      ep = itEpisode.next();
      if (ep != null)
      {
        if (displayEpisodeId && ep.getSeason() != null && ep.getEpisode() != null)
        {
          System.out.println("Saison " + ep.getSeason() + " Episode " + ep.getEpisode());
        }
        if (displayURL && ep.getUrl() != null && StringUtils.isBlank(ep.getUrl().toString()) == false)
        {
          System.out.println(ep.getUrl().toString());
        }
      }
    }
  }
}
