package fr.martiben.urlgrabber;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import fr.martiben.urlgrabber.constante.Constants;
import fr.martiben.urlgrabber.helper.DisplayEpisodeHelper;
import fr.martiben.urlgrabber.pojo.Episode;

/**
 * URL Grabber Test class.
 * 
 * @author B-Martinelli
 */
public class URLGrabberTest
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
  public void PalmaShowGrabberTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constants.TOKEN_LIMIT + "&page=" + Constants.TOKEN_PAGE;
    Map<Episode, Episode> mapURL = new URLGrabber().urlGrabber(baseUrl);

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
    Map<Episode, Episode> mapEpisodes = URLGrabber.missingEpisodeChecker(
        new URLGrabber().urlGrabber(baseUrl), Constants.SEASON_1_EPISODES_AMOUNT,
        Constants.SEASON_2_EPISODES_AMOUNT);
    DisplayEpisodeHelper.displayResults(mapEpisodes, true, false);

    Assert.assertEquals("Objective is 0 delta, but is actually " + mapEpisodes.size(), 0, mapEpisodes.size());
  }

  /**
   * Test the fetch of url from a Youtube playlist.
   * 
   * @throws JSONException
   *           Error on parsing
   * @throws IOException
   *           Error on communication with daily
   */
  @Test
  public void PalmaShowYoutubeGrabberTest() throws JSONException, IOException
  {
    String baseUrl = "https://www.googleapis.com/youtube/v3/search?channelId=UCoZoRz4-y6r87ptDp4Jk74g"
        + "&fields=nextPageToken,items(id/videoId)&maxResults=" + Constants.TOKEN_LIMIT
        + "&part=id,snippet&order=date&pageToken=" + Constants.TOKEN_PAGE
        + "&key=AIzaSyDiTCDLOAlCBfwpjrMG1rbi1JgjoUewlTo";

    Map<Episode, Episode> mapURL = new URLGrabber().urlGrabber(baseUrl);

    Assert.assertNotNull("The list is null !", mapURL);
    Assert.assertTrue("The list is empty !", mapURL.size() != 0);

    DisplayEpisodeHelper.displayResults(mapURL, false, true);
  }
  //
}
