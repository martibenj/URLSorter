package fr.martiben.urlsorter.helper;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import fr.martiben.urlsorter.constante.Constants;
import fr.martiben.urlsorter.pojo.Episode;

/**
 * Helper for displaying Episodes infos.
 * 
 * @author B-Martinelli
 */
public final class DisplayEpisodeHelper
{
  /**
   * Method to display the results from a Map.
   * 
   * @param mapEpisodes
   *          The map to display
   * @param displayEpisodeId
   *          If true, show the episode id following pattern season number and episode number
   * @param displayURL
   *          If true, show Episode URL
   */
  public static void displayResults(Map<Episode, Episode> mapEpisodes, Boolean displayEpisodeId,
      Boolean displayURL)
  {
    Iterator<Episode> itEpisode = SortingHelper.SeasonSorter(mapEpisodes).iterator();
    Episode ep = null;
    while (itEpisode.hasNext())
    {
      ep = itEpisode.next();
      if (ep != null)
      {
        if (displayEpisodeId && ep.getSeason() != null && ep.getEpisode() != null)
        {
          System.out.println(Constants.TERM_SEASON + Constants.SEPARATOR_SPACE + ep.getSeason()
              + Constants.SEPARATOR_SPACE + Constants.TERM_EPISODE + Constants.SEPARATOR_SPACE
              + ep.getEpisode());
        }
        if (displayURL && ep.getUrl() != null && StringUtils.isBlank(ep.getUrl().toString()) == false)
        {
          System.out.println(ep.getUrl().toString());
        }
      }
    }
  }
}
