package fr.martiben.urlgrabber.comparator;

import java.util.Comparator;

import fr.martiben.urlgrabber.pojo.Episode;

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
    if (a == null || b == null || a.getSeasonNumber() == null || b.getSeasonNumber() == null || a.getEpisodeNumber() == null
        || b.getEpisodeNumber() == null)
    {
      throw new UnsupportedOperationException("An Episode has null infos");
    }

    if (a == b)
    {
      retour = 0;
    }
    else if (a.getSeasonNumber() < (b.getSeasonNumber()))
    {
      retour = -1;
    }
    else if (a.getSeasonNumber() > (b.getSeasonNumber()))
    {
      retour = 1;
    }
    else if (a.getSeasonNumber().equals(b.getSeasonNumber()))
    {
      if (a.getEpisodeNumber() < b.getEpisodeNumber())
      {
        retour = -1;
      }
      else if (a.getEpisodeNumber() > b.getEpisodeNumber())
      {
        retour = 1;
      }
      else if (a.getEpisodeNumber().equals(b.getEpisodeNumber()))
      {
        retour = 0;
      }
    }
    if (retour == null)
    {
      throw new UnsupportedOperationException("No result found in comparison between " + a.toString()
          + " and " + b.toString());
    }
    return retour;
  }
}
