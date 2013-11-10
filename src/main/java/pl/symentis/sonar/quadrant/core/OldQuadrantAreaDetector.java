package pl.symentis.sonar.quadrant.core;

import pl.symentis.sonar.quadrant.api.FileID;
import pl.symentis.sonar.quadrant.api.Quadrant;
import pl.symentis.sonar.quadrant.api.QuadrantDetector;
import pl.symentis.sonar.quadrant.api.criteria.ChangeRate;
import pl.symentis.sonar.quadrant.api.criteria.ComplexityRange;
import pl.symentis.sonar.quadrant.api.criteria.FrequentlyChanged;
import pl.symentis.sonar.quadrant.api.criteria.LowComplexityRange;
import pl.symentis.sonar.quadrant.api.criteria.RarelyChanged;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class OldQuadrantAreaDetector implements QuadrantDetector {

	private final MetricsDataSource metricsDataSource;

  public OldQuadrantAreaDetector(MetricsDataSource metricsSource) {
    this.metricsDataSource = metricsSource;
	}

  @Override
	public Quadrant detect(FileID fileID) {

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
