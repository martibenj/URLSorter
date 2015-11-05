package fr.martiben.urlsorter.constante;

import java.util.regex.Pattern;

public interface Constantes
{

  /** Separator. */
  public static final String  SEPARATOR             = "-";

  /** Term season. */
  public static final String  TERM_SEASON           = "saison";

  /** Term season. */
  public static final String  TERM_EPISODE          = "episode";

  /** URL Pattern. */
  public static final String  PATTERN_URL           = TERM_SEASON + SEPARATOR + "[0-9]*" + SEPARATOR
                                                        + TERM_EPISODE + SEPARATOR + "[0-9]*";

  /** Pattern Object from URL. */
  public static final Pattern PATTERN               = Pattern.compile(PATTERN_URL);

  /** Max limit per Request. */
  public static final int     MAX_LIMIT_PER_REQUEST = 100;

  /** Token for param Limit. */
  public static final String  TOKEN_LIMIT           = "%LIMIT%";

  /** Token for param Page. */
  public static final String  TOKEN_PAGE            = "%PAGE%";

}
