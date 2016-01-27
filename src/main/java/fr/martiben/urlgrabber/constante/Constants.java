package fr.martiben.urlgrabber.constante;

import java.util.regex.Pattern;

/**
 * Constants for the project.
 * 
 * @author B-Martinelli
 */
public interface Constants
{
  /** Separator. */
  public static final String  SEPARATOR                     = "-";

  /** Space Separator. */
  public static final String  SEPARATOR_SPACE               = " ";

  /** Term season. */
  public static final String  TERM_SEASON                   = "saison";

  /** Term season. */
  public static final String  TERM_EPISODE                  = "episode";

  /** Season/Episode URL Pattern. */
  public static final String  PATTERN_SEASON_EPISODE_REGEXP = TERM_SEASON + SEPARATOR + "[0-9]*" + SEPARATOR
                                                                + TERM_EPISODE + SEPARATOR + "[0-9]*";

  /** Season/Episode Pattern Object from URL. */
  public static final Pattern PATTERN_SEASON_EPISODE        = Pattern.compile(PATTERN_SEASON_EPISODE_REGEXP);

  /** Term Provider Youtube. */
  public static final String  TERM_PROVIDER_YOUTUBE         = "www.googleapis.com/youtube";

  /** Term Provider Youtube. */
  public static final String  TERM_PROVIDER_DAILYMOTION     = "api.dailymotion.com";

  /** Provider URL Pattern. */
  public static final String  PATTERN_PROVIDER_REGEX        = "(" + TERM_PROVIDER_YOUTUBE + ")|("
                                                                + TERM_PROVIDER_DAILYMOTION + ")";

  /** Provider Pattern Object from URL. */
  public static final Pattern PATTERN_PROVIDER              = Pattern.compile(PATTERN_PROVIDER_REGEX);

  /** Template URL Watch Youtube. */
  public static final String  WATCH_YOUTUBE_TEMPLATE            = "http://www.youtube.com/watch?v=";

  /** Max limit per Request for Youtube . */
  public static final int     MAX_LIMIT_PER_REQUEST_YOUTUBE     = 50;

  /** Max limit per Request for Daily. */
  public static final int     MAX_LIMIT_PER_REQUEST_DAILYMOTION = 100;

  /** Token for param Limit. */
  public static final String  TOKEN_LIMIT                   = "%LIMIT%";

  /** Token for param Page. */
  public static final String  TOKEN_PAGE                    = "%PAGE%";

  /** Token for param Key on Youtube. */
  public static final String  TOKEN_YOUTUBE_KEY             = "%KEY%";

  /** Proxy Host. */
  public static final String  PROXY_HOST                    = "ecprox.bull.fr";

  /** Proxy port. */
  public static final int     PROXY_PORT                    = 80;

  /** Amount of episodes for Season 1. */
  public static final int     SEASON_1_EPISODES_AMOUNT      = 108;

  /** Amount of episodes for Season 2. */
  public static final int     SEASON_2_EPISODES_AMOUNT      = 86;

}
