package fr.martiben.urlsorter.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fr.martiben.urlsorter.comparator.VideoIdComparator;
import fr.martiben.urlsorter.pojo.Episode;

/**
 * Helper for Sorting elements.
 * 
 * @author B-Martinelli
 */
public class SortingHelper
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
