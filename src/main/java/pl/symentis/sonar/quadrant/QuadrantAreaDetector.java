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
    
    if(complexityRange instanceof LowComplexityRange && changeRate instanceof RarelyChanged){
      return Quadrant.tools;
    }

		if (fileID.toString().equals("rarelyChangedAndSimpleFile"))
			return Quadrant.tools;
		else if (fileID.toString().equals("oftenChangedSimpleFile"))
			return Quadrant.breedinggrounds;
		else if (fileID.toString().equals("complexButRarelyChangedFile"))
			return Quadrant.uglystables;
    else {
      ComplexityRange cmxRng = metricsDataSource.getComplexityFor(fileID);
      if (cmxRng != null) return null;
    }

			return Quadrant.designflaw;
	}

	public boolean canWork() {
		return (metricsDataSource != null);
	}

}
