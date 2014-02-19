package pl.symentis.sonar.quadrant.scm;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class GitSCMWorkspace {

	private final Git git;

	public GitSCMWorkspace(File file) throws IOException {

		git = Git.open(file);
	}

	public Changesets queryForChangesets() throws RevisionSyntaxException, AmbiguousObjectException,
	        IncorrectObjectTypeException, IOException, NoHeadException, GitAPIException {

		Repository repository = git.getRepository();

		ObjectId head = repository.resolve(Constants.HEAD);

		Iterable<RevCommit> commits = git.log().add(head).call();

		final ImmutableList<Changeset> list = FluentIterable.from(commits)
		        .transform(new Function<RevCommit, Changeset>() {

			        @Override
			        public Changeset apply(final RevCommit revCommit) {
				        return new Changeset() {

					        @Override
					        public String getDate() {
						        PersonIdent authorIdent = revCommit.getAuthorIdent();
						        return authorIdent.getWhen().toString();
					        }

					        @Override
					        public String getAuthor() {
						        PersonIdent authorIdent = revCommit.getAuthorIdent();
						        return Joiner.on(' ').join(authorIdent.getName(),
						                format("<%s>", authorIdent.getEmailAddress()));
					        }
				        };
			        }
		        }).toList();

		return new Changesets() {

			@Override
			public Changeset get(int i) {
				return list.get(i);
			}

			@Override
			public Changeset first() {
				return list.get(list.size() - 1);
			}
		};
	}
}
