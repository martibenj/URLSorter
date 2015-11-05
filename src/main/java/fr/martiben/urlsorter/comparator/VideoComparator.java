package fr.martiben.urlsorter.comparator;

import java.util.Comparator;
import java.util.regex.Matcher;

import fr.martiben.urlsorter.constante.Constantes;
import fr.martiben.urlsorter.pojo.Episode;

public class VideoComparator implements Comparator<Episode>
{

  @Override
  public int compare(Episode a, Episode b)
  {
    String urlA = a.getUrl().toString();
    String urlB = b.getUrl().toString();

    Matcher matcherA = Constantes.PATTERN.matcher(urlA);
    Matcher matcherB = Constantes.PATTERN.matcher(urlB);

    if (!matcherA.find() || !matcherB.find())
    {
      throw new UnsupportedOperationException("Pattern not found in URL");
    }
    return new StringComparator().compare(urlA.substring(matcherA.start(), matcherA.end()),
        urlB.substring(matcherB.start(), matcherB.end()));
  }

}
