package pl.symentis.sonar.quadrant.scm;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class GitSCMWorkspaceTest {

	@Test
	public void should_get_all_changesets() throws Exception {

		GitSCMWorkspace workspace = new GitSCMWorkspace(new File("."));

		Changesets changesets = workspace.queryForChangesets();

		Changeset changeset = changesets.first();
		assertThat(changeset.getAuthor()).isEqualTo("Jaroslaw Palka <jpalka@gmail.com>");
		assertThat(changeset.getDate()).isEqualTo("Wed Apr 24 20:10:02 CEST 2013");

	}
}
