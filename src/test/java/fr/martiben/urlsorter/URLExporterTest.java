package fr.martiben.urlsorter;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

public class URLExporterTest
{
  @Test
  public void PalmaShowTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + URLExporter.TOKEN_LIMIT + "&page=" + URLExporter.TOKEN_PAGE;
    new URLExporter().URLSorter(baseUrl);
  }
}
