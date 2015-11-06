package fr.martiben.urlsorter.pojo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import fr.martiben.urlsorter.constante.Constants;

/**
 * POJO for an Episode.
 * 
 * @author B-Martinelli
 */
public class Episode
{
  /** Season. */
  private Integer season;

  /** Episode. */
  private Integer episode;

  /** URL. */
  private URL     url;

  /**
   * Constructor.
   * 
   * @param season
   *          Season number
   * @param episode
   *          episode number
   * @param url
   *          the url to the episode
   */
  public Episode(Integer season, Integer episode, URL url)
  {
    this.season = season;
    this.episode = episode;
    this.url = url;
  }

  /**
   * Create an Episode from an URL.
   * 
   * @param url
   *          the URL source
   * @return An episode created from the URL
   * @throws NumberFormatException
   *           Error in parsing Season or Episode
   * @throws MalformedURLException
   *           Error in URL
   */
  public static Episode parseEpisodeFromURL(String url) throws NumberFormatException, MalformedURLException
  {
    Episode ep = null;
    Matcher matcher = Constants.PATTERN.matcher(url);
    if (matcher.find())
    {
      String[] arrayInfos = url.substring(matcher.start(), matcher.end()).split(Constants.SEPARATOR);
      ep = new Episode(Integer.parseInt(arrayInfos[1]), Integer.parseInt(arrayInfos[3]), new URL(url));
    }
    return ep;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((episode == null) ? 0 : episode.hashCode());
    result = prime * result + ((season == null) ? 0 : season.hashCode());
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    Episode other = (Episode) obj;
    if (episode == null)
    {
      if (other.episode != null)
      {
        return false;
      }
    }
    else if (!episode.equals(other.episode))
    {
      return false;
    }
    if (season == null)
    {
      if (other.season != null)
      {
        return false;
      }
    }
    else if (!season.equals(other.season))
    {
      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return "Season " + season + " Episode " + episode + "\n" + url.toString();
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

  /**
   * @return the season
   */
  public Integer getSeason()
  {
    return season;
  }

  /**
   * @param season
   *          the season to set
   */
  public void setSeason(Integer season)
  {
    this.season = season;
  }

  /**
   * @return the episode
   */
  public Integer getEpisode()
  {
    return episode;
  }

  /**
   * @param episode
   *          the episode to set
   */
  public void setEpisode(Integer episode)
  {
    this.episode = episode;
  }

}
