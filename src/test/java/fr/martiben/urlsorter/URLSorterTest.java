package fr.martiben.urlsorter;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import fr.martiben.urlsorter.constante.Constants;
import fr.martiben.urlsorter.helper.DisplayEpisodeHelper;
import fr.martiben.urlsorter.pojo.Episode;

/**
 * URL Sorter Test class.
 * 
 * @author B-Martinelli
 */
public class URLSorterTest
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
  public void PalmaShowSorterTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constants.TOKEN_LIMIT + "&page=" + Constants.TOKEN_PAGE;
    Map<Episode, Episode> mapURL = new URLSorter().urlSortingMachine(baseUrl);

    Assert.assertNotNull("The list is null !", mapURL);
    Assert.assertTrue("The list is empty !", mapURL.size() != 0);

    DisplayEpisodeHelper.displayResults(mapURL, false, true);
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
    Map<Episode, Episode> mapEpisodes = URLSorter.missingEpisodeChecker(new URLSorter().urlSortingMachine(baseUrl),
        Constants.SEASON_1_EPISODES_AMOUNT, Constants.SEASON_2_EPISODES_AMOUNT);
    DisplayEpisodeHelper.displayResults(mapEpisodes, true, false);

    Assert.assertEquals("Objective is 0 delta, but is actually " + mapEpisodes.size(), 0, mapEpisodes.size());
  }
}
