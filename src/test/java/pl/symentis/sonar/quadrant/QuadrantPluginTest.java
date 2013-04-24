package pl.symentis.sonar.quadrant;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;


public class QuadrantPluginTest {

	@Test
	public void should_return_empty_extension_list() throws Exception {
		QuadrantPlugin plugin = new QuadrantPlugin();
		
		List<?> extensions = plugin.getExtensions();
		
		assertNull(extensions);
		
	}
}
