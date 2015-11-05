package fr.martiben.urlsorter.pojo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import fr.martiben.urlsorter.constante.Constantes;

public class Episode
{
  /** Season. */
  private int saison;

  /** Episode. */
  private int episode;

  /** URL. */
  private URL url;

  /**
   * Constructor
   * 
   * @param saison
   *          Season number
   * @param episode
   *          episode number
   */
  public Episode(int saison, int episode, URL url)
  {
    this.saison = saison;
    this.episode = episode;
    this.url = url;
  }

  public static Episode parseEpisodeFromURL(String url) throws NumberFormatException, MalformedURLException
  {
    Episode ep = null;
    Matcher matcher = Constantes.PATTERN.matcher(url);
    if (matcher.find())
    {
      String[] arrayInfos = url.substring(matcher.start(), matcher.end()).split(Constantes.SEPARATOR);
      ep = new Episode(Integer.parseInt(arrayInfos[1]), Integer.parseInt(arrayInfos[3]), new URL(url));
    }
    return ep;
  }

  @Override
  public String toString()
  {
    return url.toString();
  }

  /**
   * @return the saison
   */
  public int getSaison()
  {
    return saison;
  }

  /**
   * @param saison
   *          the saison to set
   */
  public void setSaison(int saison)
  {
    this.saison = saison;
  }

  /**
   * @return the episode
   */
  public int getEpisode()
  {
    return episode;
  }

  /**
   * @param episode
   *          the episode to set
   */
  public void setEpisode(int episode)
  {
    this.episode = episode;
  }

  /**
   * @return the url
   */
  public URL getUrl()
  {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(URL url)
  {
    this.url = url;
  }

}
