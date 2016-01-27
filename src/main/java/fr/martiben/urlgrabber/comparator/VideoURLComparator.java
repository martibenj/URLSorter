package fr.martiben.urlgrabber.comparator;

import java.util.Comparator;
import java.util.regex.Matcher;

import fr.martiben.urlgrabber.constante.Constants;
import fr.martiben.urlgrabber.pojo.Episode;

/**
 * Comparator based on Video URL.
 * 
 * @author B-Martinelli
 */
public class VideoURLComparator implements Comparator<Episode>
{
  /** {@inheritDoc} */
  @Override
  public int compare(Episode a, Episode b)
  {
    String urlA = a.getUrl().toString();
    String urlB = b.getUrl().toString();

    Matcher matcherA = Constants.PATTERN_SEASON_EPISODE.matcher(urlA);
    Matcher matcherB = Constants.PATTERN_SEASON_EPISODE.matcher(urlB);

    if (!matcherA.find() || !matcherB.find())
    {
      throw new UnsupportedOperationException("Pattern not found in URL");
    }
    return new StringComparator().compare(urlA.substring(matcherA.start(), matcherA.end()),
        urlB.substring(matcherB.start(), matcherB.end()));
  }
}
