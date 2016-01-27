package fr.martiben.urlgrabber.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fr.martiben.urlgrabber.comparator.VideoIdComparator;
import fr.martiben.urlgrabber.pojo.Episode;

/**
 * Helper for Sorting elements.
 * 
 * @author B-Martinelli
 */
public final class SortingHelper
{
  /**
   * Sort a Map of episodes
   * 
   * @param mapInput
   *          Collection of episodes
   * @return A list
   */
  public static List<Episode> SeasonSorter(Map<Episode, Episode> mapInput)
  {
    List<Episode> retour = new ArrayList<Episode>(mapInput.values());
    Collections.sort(retour, new VideoIdComparator());
    return retour;
  }
}
