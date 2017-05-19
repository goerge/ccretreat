package ccretreat;

import org.junit.Test;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static ccretreat.WordWrap.umbrechen;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WordWrapTest {

  @Test
  public void textWithSingleLetterLinesWrapsAtLengthOne() {
    assertThat(umbrechen("a\nb\nc", 1), is("a\nb\nc"));
  }

  @Test
  public void challenge() {
		assertThat(umbrechen("line1 l ine2", 7), is("line1 l\nine2"));
	}

	@Test
	public void zeilenAusWortenBauen() {
    List<String> worte = List.of("a", "b", "c");
    int maxLength = 3;

    List<String> expectedZeilen = List.of("a b", "c");
    assertThat(WordWrap.zeilenBauen(worte, maxLength), is(expectedZeilen));
  }

  @Test
  public void emptyWordlist() {
    int maxLength = 3;

    List<String> expectedWortgruppe = List.empty();
    List<String> expectedRemainingWortliste = List.empty();

    final Tuple2<Seq<String>, Seq<String>> tuple = WordWrap.worteDerZeileBestimmen(List.empty(), maxLength);
    Seq<String> wortgruppe = tuple._1();

    assertThat(tuple._2(), is(expectedRemainingWortliste));
    assertThat(wortgruppe, is(expectedWortgruppe));
  }

  @Test
  public void lastWordConstitutesLine() {
    final List<String> wortliste = List.of("c");
    int maxLength = 3;

    List<String> expectedWortgruppe = List.of("c");
    List<String> expectedRemainingWortliste = List.empty();

    final Tuple2<Seq<String>, Seq<String>> tuple = WordWrap.worteDerZeileBestimmen(wortliste, maxLength);
    Seq<String> wortgruppe = tuple._1();

    assertThat(tuple._2(), is(expectedRemainingWortliste));
    assertThat(wortgruppe, is(expectedWortgruppe));
  }

  @Test
  public void wordgroupFitsExactlyInLine() {
    final List<String> wortliste = List.of("a", "b", "c");
    int maxLength = 3;

    List<String> expectedWortgruppe = List.of("a", "b");
    List<String> expectedRemainingWortliste = List.of("c");

    final Tuple2<Seq<String>, Seq<String>> tuple = WordWrap.worteDerZeileBestimmen(wortliste, maxLength);
    Seq<String> wortgruppe = tuple._1();

    assertThat(tuple._2(), is(expectedRemainingWortliste));
    assertThat(wortgruppe, is(expectedWortgruppe));
  }

  @Test
  public void wordgroupShorterThanLine() {
    final List<String> wortliste = List.of("a", "b", "c");
    int maxLength = 2;

    List<String> expectedWortgruppe = List.of("a");
    List<String> expectedRemainingWortliste = List.of("b", "c");

    final Tuple2<Seq<String>, Seq<String>> tuple = WordWrap.worteDerZeileBestimmen(wortliste, maxLength);
    Seq<String> wortgruppe = tuple._1();

    assertThat(tuple._2(), is(expectedRemainingWortliste));
    assertThat(wortgruppe, is(expectedWortgruppe));
  }

  @Test
  public void oneWordPerLine() {
    List<String> wortliste = List.of("a", "b", "c");
    int maxLength = 1;

    Seq<Seq<String>> lines = WordWrap.worteZusammenfassenProZeile(wortliste, maxLength);

    Seq<Seq<String>> expectedLines = List.of(List.of("a"), List.of("b"), List.of("c"));
    assertThat(lines, is(expectedLines));
  }

  @Test
  public void multipleWordsPerLine() {
    List<String> wortliste = List.of("a", "b", "c");
    int maxLength = 3;

    Seq<Seq<String>> lines = WordWrap.worteZusammenfassenProZeile(wortliste, maxLength);

    Seq<Seq<String>> expectedLines = List.of(List.of("a", "b"), List.of("c"));
    assertThat(lines, is(expectedLines));
  }

  @Test
  public void zeilenAusWortgruppenBauen() {
    Seq<Seq<String>> wortgruppen = List.of(List.of("a", "b"), List.of("c", "d"));

    List<String> expectedZeilen = List.of("a b", "c d");
    assertThat(WordWrap.alleZeilenAusWortgruppenBauen(wortgruppen), is(expectedZeilen));
  }

  @Test
  public void langeWörterUmbrechen() {
    String text = "Es blaut die Nacht,\n" +
      "die Sternlein blinken,\n" +
      "Schneefl�cklein leis hernieder sinken.";

    String actualText = WordWrap.umbrechen(text, 14);

    String expectedText = "Es blaut die\n" +
      "Nacht, die\n" +
      "Sternlein\n" +
      "blinken,\n" +
      "Schneefl�cklei\n" +
      "n leis\n" +
      "hernieder\n" +
      "sinken.";

    assertThat(actualText, is(expectedText));
  }

  @Test
  public void zuLangeWorteTrennen() {
    List<String> worte = List.of("a", "bcd", "efghi");
    int maximaleZeilenlänge = 2;

    List<String> actualSilben = WordWrap.zuLangeWorteTrennen(worte, maximaleZeilenlänge);

    List<String> expectedSilben = List.of("a", "bc", "d", "ef", "gh", "i");
    assertThat(actualSilben, is(expectedSilben));
  }

  @Test
  public void kurzeWorteNichtTrennen() {
    List<String> worte = List.of("a", "b", "c");
    int maximaleZeilenlänge = 1;

    List<String> actualSilben = WordWrap.zuLangeWorteTrennen(worte, maximaleZeilenlänge);

    List<String> expectedSilben = List.of("a", "b", "c");
    assertThat(actualSilben, is(expectedSilben));
  }

  @Test
  public void split() {
    assertThat(WordWrap.split("a", 1), is(List.of("a")));
  }
}
