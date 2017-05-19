package ccretreat;

import java.util.function.Function;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public class WordWrap {

  public static String umbrechen(String text, int maxZeilenlänge) {
    List<String> wörter = wörterUmbrechen(text);
    List<String> silben = zuLangeWorteTrennen(wörter, maxZeilenlänge);
    Seq<String> zeilen = zeilenBauen(silben, maxZeilenlänge);
    return ausgabeTextAufbereiten(zeilen);
  }

  static List<String> wörterUmbrechen(String text) {
    return List.of(text.split("\\s"))
      .filter(s -> !s.isEmpty());
  }

  static List<String> zuLangeWorteTrennen(List<String> worte, int maximaleZeilenlänge) {
    return worte
      .flatMap(wort -> split(wort, maximaleZeilenlänge));
  }

  static Seq<String> zeilenBauen(List<String> worte, int maxLength) {
    Seq<Seq<String>> zeilen = worteZusammenfassenProZeile(worte, maxLength);
    return alleZeilenAusWortgruppenBauen(zeilen);
  }

  static String ausgabeTextAufbereiten(Seq<String> zeilen) {
    return String.join("\n", zeilen);
  }

  static Seq<String> split(String wort, int maximaleZeilenlänge) {
    if (wort.length() <= maximaleZeilenlänge) {
      return List.of(wort);
    }
    return List.ofAll(wort.toCharArray())
      .splitAt(maximaleZeilenlänge)
      .map(charArrayToString(), charArrayToString())
      .toSeq()
      .map(String.class::cast)
      .flatMap(s -> split(s, maximaleZeilenlänge));
  }

  private static Function<io.vavr.collection.List<Character>, String> charArrayToString() {
    return characters -> characters.foldLeft("", (s, c) -> s + c);
  }

  static Seq<Seq<String>> worteZusammenfassenProZeile(List<String> worte, int maxLength) {
    return worteZusammenfassenProZeile(maxLength, List.empty(), worte);
  }

  private static Seq<Seq<String>> worteZusammenfassenProZeile(int maxLength, Seq<Seq<String>> wortgruppen, Seq<String> wortliste) {
    if (wortliste.isEmpty()) {
      return wortgruppen;
    }
    final Tuple2<Seq<String>, Seq<String>> tuple = worteDerZeileBestimmen(wortliste, maxLength);
    final Seq<String> wortgruppe = tuple._1();
    return worteZusammenfassenProZeile(maxLength, wortgruppen.append(wortgruppe), tuple._2());
  }

  static Seq<String> alleZeilenAusWortgruppenBauen(Seq<Seq<String>> wortgruppen) {
    final String wortTrenner = " ";
    return wortgruppen
      .map(wortgruppe -> String.join(wortTrenner, wortgruppe));
  }

  static Tuple2<Seq<String>, Seq<String>> worteDerZeileBestimmen(Seq<String> wortliste, int maxLength) {
    final List<String> wortgruppe = List.empty();
    final int currentLineLength = 0;
    return worteDerZeileBestimmen(wortliste, maxLength, wortgruppe, currentLineLength);
  }

  private static Tuple2<Seq<String>, Seq<String>> worteDerZeileBestimmen(Seq<String> wortliste, int maxLength, List<String> wortgruppe,
                                                                         int currentLineLength) {
    if (wortliste.isEmpty()) {
      return Tuple.of(wortgruppe, wortliste);
    }
    final String currentWord = wortliste.head();
    final int newLineLength = newLineLength(currentLineLength, currentWord);
    boolean lineCompleted = newLineLength > maxLength;
    if (lineCompleted) {
      return Tuple.of(wortgruppe, wortliste);
    }
    return worteDerZeileBestimmen(wortliste.tail(), maxLength, wortgruppe.append(currentWord), newLineLength);
  }

  private static int newLineLength(int currentLineLength, String currentWord) {
    final int wordSeparatorLength = 1;
    final int newLineLength = currentLineLength + currentWord.length();
    final boolean inTheMiddleOfTheLine = currentLineLength > 0;
    if (inTheMiddleOfTheLine) {
      return newLineLength + wordSeparatorLength;
    }
    return newLineLength;
  }
}
