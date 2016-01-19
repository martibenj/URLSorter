package fr.martiben.urlsorter.pojo;

import java.util.regex.Matcher;

import fr.martiben.urlsorter.constante.Constants;

public enum Provider
{
  YOUTUBE, DAILYMOTION;

  /**
   * Resolve Provider value from URL
   * 
   * @param baseURL
   *          URL to parse
   * @return The Provider value
   */
  public static Provider resolver(final String baseURL)
  {
    Provider retour = null;
    Matcher matcherProvider = Constants.PATTERN_PROVIDER.matcher(baseURL);
    if (matcherProvider.find() == false)
    {
      throw new UnsupportedOperationException("Pattern not found in URL");
    }
    switch (matcherProvider.group())
    {
      case Constants.TERM_PROVIDER_YOUTUBE:
        retour = Provider.YOUTUBE;
        break;

      case Constants.TERM_PROVIDER_DAILYMOTION:
        retour = Provider.DAILYMOTION;
        break;

      default:
        throw new UnsupportedOperationException("Provider not found in existing values");
    }
    return retour;

  }
}
