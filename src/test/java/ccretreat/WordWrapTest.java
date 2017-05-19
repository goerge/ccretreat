package ccretreat;

import org.junit.Test;

import static ccretreat.WordWrap.umbrechen;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WordWrapTest {
	
	@Test
	public void textWithSingleLetterLinesWrapsAtLengthOne() {
		assertThat(umbrechen("a\nb\nc", 1), is("a\nb\nc"));
	}
	
}
