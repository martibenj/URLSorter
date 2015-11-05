package fr.martiben.urlsorter.comparator;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoComparator implements Comparator<String>
{
  private final String  patternStr = "_saison-[0-9]*-episode-[0-9]*";
  private final Pattern pattern    = Pattern.compile(patternStr);

  @Override
  public int compare(String a, String b)
  {
    Matcher matcherA = pattern.matcher(a);
    Matcher matcherB = pattern.matcher(b);

    if (!matcherA.find() || !matcherB.find())
    {
      throw new UnsupportedOperationException("Pattern not found in URL");
    }
    return new StringComparator().compare(a.substring(matcherA.start(), matcherA.end()),
        b.substring(matcherB.start(), matcherB.end()));
  }

}
