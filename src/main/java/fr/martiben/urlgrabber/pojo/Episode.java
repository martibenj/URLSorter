package fr.martiben.urlgrabber.pojo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import fr.martiben.urlgrabber.constante.Constants;

/**
 * POJO for an Episode.
 * 
 * @author B-Martinelli
 */
public class Episode
{
  /** Season. */
  private Integer seasonNumber;

  /** Episode. */
  private Integer episodeNumber;

  /** URL. */
  private URL     url;

  /**
   * Constructor.
   * 
   * @param seasonNumber
   *          Season number
   * @param episodeNumber
   *          episode number
   * @param url
   *          the url to the episode
   */
  public Episode(Integer seasonNumber, Integer episodeNumber, URL url)
  {
    this.seasonNumber = seasonNumber;
    this.episodeNumber = episodeNumber;
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
    Matcher matcher = Constants.PATTERN_SEASON_EPISODE.matcher(url);
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
    result = prime * result + ((episodeNumber == null) ? 0 : episodeNumber.hashCode());
    result = prime * result + ((seasonNumber == null) ? 0 : seasonNumber.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
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
    if (episodeNumber == null)
    {
      if (other.episodeNumber != null)
      {
        return false;
      }
    }
    else if (!episodeNumber.equals(other.episodeNumber))
    {
      return false;
    }
    if (seasonNumber == null)
    {
      if (other.seasonNumber != null)
      {
        return false;
      }
    }
    else if (!seasonNumber.equals(other.seasonNumber))
    {
      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return "Season " + seasonNumber + " Episode " + episodeNumber + "\n" + url.toString();
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
   * @return the seasonNumber
   */
  public Integer getSeasonNumber()
  {
    return seasonNumber;
  }

  /**
   * @param seasonNumber
   *          the season to set
   */
  public void setSeasonNumber(Integer seasonNumber)
  {
    this.seasonNumber = seasonNumber;
  }

  /**
   * @return the episodeNumber
   */
  public Integer getEpisodeNumber()
  {
    return episodeNumber;
  }

  /**
   * @param episodeNumber
   *          the episode to set
   */
  public void setEpisodeNumber(Integer episodeNumber)
  {
    this.episodeNumber = episodeNumber;
  }
}
