package ccretreat;

import org.junit.Test;

import io.vavr.collection.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AusgabeTextAufbereitenTest {
	@Test
	public void wordsAreYankedWithNewline() {
		assertThat(WordWrap.ausgabeTextAufbereiten(List.of("x", "y", "z")), is("x\ny\nz"));
	}
}
