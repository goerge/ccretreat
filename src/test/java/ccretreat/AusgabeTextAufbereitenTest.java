package ccretreat;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AusgabeTextAufbereitenTest {
	@Test
	public void wordsAreYankedWithNewline() {
		assertThat(WordWrap.ausgabeTextAufbereiten(new String[]{"x", "y", "z"}), is("x\ny\nz"));
	}
}
