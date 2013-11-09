package pl.symentis.sonar.quadrant;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class QuadrantAreaDetector {

	private final MetricsDataSource metricsDataSource;

  public QuadrantAreaDetector(MetricsDataSource metricsSource) {
		this.metricsDataSource = metricsSource;
	}

	public Quadrant detectAreaFor(FileID fileID) {

    ChangeRate changeRate = metricsDataSource.getChangeRateFor(fileID);
    ComplexityRange complexityRange = metricsDataSource.getComplexityFor(fileID);
    
    if (complexityRange instanceof LowComplexityRange) {
      return (changeRate instanceof RarelyChanged) ? Quadrant.tools : Quadrant.breedinggrounds;
    } else {
      return (changeRate instanceof FrequentlyChanged) ? Quadrant.designflaw : Quadrant.uglystables;
    }
	}

	public boolean canWork() {
		return (metricsDataSource != null);
	}

}
