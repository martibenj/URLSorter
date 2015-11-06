package fr.martiben.urlsorter.comparator;

import java.util.Comparator;

/**
 * Comparator for number in string to avoid the 69->7->70 problem.
 * 
 * @author B-Martinelli
 */
public class StringComparator implements Comparator<Object>
{
  /** {@inheritDoc} */
  @Override
  public int compare(final Object object1, final Object object2)
  {
    if (object1 == object2)
    {
      return 0;
    }
    if (object1 == null)
    {
      return -1;
    }
    if (object2 == null)
    {
      return 1;
    }
    String stringA = object1.toString().toLowerCase();
    String stringB = object2.toString().toLowerCase();

    int indiceA = 0;
    int indiceB = 0;
    int nza = 0;
    int nzb = 0;
    char characterA;
    char characterB;
    int result;

    while (true)
    {
      nza = nzb = 0;

      characterA = charAt(stringA, indiceA);
      characterB = charAt(stringB, indiceB);

      while (Character.isSpaceChar(characterA) || (characterA == '0'))
      {
        if (characterA == '0')
        {
          nza++;
        }
        else
        {
          nza = 0;
        }

        characterA = charAt(stringA, ++indiceA);
      }

      while (Character.isSpaceChar(characterB) || (characterB == '0'))
      {
        if (characterB == '0')
        {
          nzb++;
        }
        else
        {
          nzb = 0;
        }

        characterB = charAt(stringB, ++indiceB);
      }

      if (Character.isDigit(characterA) && Character.isDigit(characterB))
      {
        result = compareRight(stringA.substring(indiceA), stringB.substring(indiceB));
        if (result != 0)
        {
          return result;
        }
      }

      if ((characterA == 0) && (characterB == 0))
      {
        return nza - nzb;
      }

      if (characterA < characterB)
      {
        return -1;
      }
      else if (characterA > characterB)
      {
        return +1;
      }

      ++indiceA;
      ++indiceB;
    }
  }

  /**
   * Redefine the charAt.
   * 
   * @param pString
   *          the string to search
   * @param pInt
   *          the indice
   * @return Eventually returns the char at for the String
   */
  private static char charAt(final String pString, final int pInt)
  {
    if (pInt >= pString.length())
    {
      return 0;
    }
    else
    {
      return pString.charAt(pInt);
    }
  }

  /**
   * Compare from the right.
   * 
   * @param numberA
   *          the first
   * @param numberB
   *          the second
   * @return the result of the comparison
   */
  private int compareRight(final String numberA, final String numberB)
  {
    int bias = 0;
    int indiceA = 0;
    int indiceB = 0;

    for (;; indiceA++, indiceB++)
    {
      char characterA = charAt(numberA, indiceA);
      char characterB = charAt(numberB, indiceB);

      if (!Character.isDigit(characterA) && !Character.isDigit(characterB))
      {
        return bias;
      }
      else if (!Character.isDigit(characterA))
      {
        return -1;
      }
      else if (!Character.isDigit(characterB))
      {
        return +1;
      }
      else if (characterA < characterB)
      {
        if (bias == 0)
        {
          bias = -1;
        }
      }
      else if (characterA > characterB)
      {
        if (bias == 0)
        {
          bias = +1;
        }
      }
      else if ((characterA == 0) && (characterB == 0))
      {
        return bias;
      }
    }
  }
}
