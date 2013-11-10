package pl.symentis.sonar.quadrant.core;

import pl.symentis.sonar.quadrant.api.FileID;
import pl.symentis.sonar.quadrant.api.criteria.ChangeRate;
import pl.symentis.sonar.quadrant.api.criteria.ComplexityRange;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class MetricsDataSource {

  public ComplexityRange getComplexityFor(FileID fileID) {
    return null;
  }

  public ChangeRate getChangeRateFor(FileID accused) {
    return null;
  }

}
