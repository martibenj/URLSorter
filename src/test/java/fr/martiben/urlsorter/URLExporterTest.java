package fr.martiben.urlsorter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import fr.martiben.urlsorter.constante.Constantes;
import fr.martiben.urlsorter.pojo.Episode;

public class URLExporterTest
{
  @Test
  public void PalmaShowExportTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constantes.TOKEN_LIMIT + "&page=" + Constantes.TOKEN_PAGE;
    List<Episode> listURL = new URLExporter().URLSorter(baseUrl);

    // Showing Results
    Iterator<Episode> itURL = listURL.iterator();
    while (itURL.hasNext())
    {
      System.out.println(itURL.next());
    }
  }

  @Test
  public void PalmaShowCheckerTest() throws JSONException, IOException
  {
    String baseUrl = "https://api.dailymotion.com/playlist/x41df1/videos?fields=url,&limit="
        + Constantes.TOKEN_LIMIT + "&page=" + Constantes.TOKEN_PAGE;
    Assert.assertTrue(URLExporter.listChecker(new URLExporter().URLSorter(baseUrl), 108, 86));
  }

}
