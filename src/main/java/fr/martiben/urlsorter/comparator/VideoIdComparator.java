package fr.martiben.urlsorter.comparator;

import java.util.Comparator;

import fr.martiben.urlsorter.pojo.Episode;

/**
 * Comparator based on Video id (Season number and Episode number).
 * 
 * @author B-Martinelli
 */
public class VideoIdComparator implements Comparator<Episode>
{
  /** {@inheritDoc} */
  @Override
  public int compare(Episode a, Episode b)
  {
    Integer retour = null;
    if (a == null || b == null || a.getSeason() == null || b.getSeason() == null || a.getEpisode() == null
        || b.getEpisode() == null)
    {
      throw new UnsupportedOperationException("An Episode has null infos");
    }

    if (a == b)
    {
      retour = 0;
    }
    else if (a.getSeason() < (b.getSeason()))
    {
      retour = -1;
    }
    else if (a.getSeason() > (b.getSeason()))
    {
      retour = 1;
    }
    else if (a.getSeason() == (b.getSeason()))
    {
      if (a.getEpisode() < b.getEpisode())
      {
        retour = -1;
      }
      else if (a.getEpisode() > b.getEpisode())
      {
        retour = 1;
      }
      else if (a.getEpisode() == b.getEpisode())
      {
        retour = 0;
      }
    }
    return retour;
  }
}
