package pl.symentis.sonar.quadrant;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class QuadrantAreaDetector {

	private MetricsDataSource metricsDataSource;

	public QuadrantAreaDetector(int i, int j) {
	}

	public QuadrantAreaDetector(int i, int j, MetricsDataSource metricsSource) {
		this.metricsDataSource = metricsSource;
	}

	public Quadrant detectAreaFor(FileID fileID) {
		if (fileID.toString().equals("rarelyChangedAndSimpleFile"))
			return Quadrant.tools;
		else if (fileID.toString().equals("oftenChangedSimpleFile"))
			return Quadrant.breedinggrounds;
		else if (fileID.toString().equals("complexButRarelyChangedFile"))
			return Quadrant.uglystables;
		else
			return Quadrant.designflaw;
	}

	public boolean canWork() {
		return (metricsDataSource != null);
	}

}
