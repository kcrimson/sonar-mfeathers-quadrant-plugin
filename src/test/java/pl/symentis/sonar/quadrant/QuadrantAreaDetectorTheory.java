package pl.symentis.sonar.quadrant;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(Theories.class)
public class QuadrantAreaDetectorTheory {

  @DataPoint
  public static FileID simpleFileRarelyChangedWronglyNamed = new FileID("complexButRarelyChangedFile");

//  @DataPoint
//  public static FileID flawedFileSimplyNamed = new FileID("rarelyChangedAndSimpleFile");

  @DataPoint
  public static Quadrant quadrant = Quadrant.tools;

  @Theory
  public void shouldJudgeByComplexityRatherThanByName(FileID accused, Quadrant expectedQuadrant) {
    // given
    MetricsDataSource metricsDataSource = Mockito.mock(MetricsDataSource.class);

    ComplexityRange lowComplexityRange = new LowComplexityRange();
    when(metricsDataSource.getComplexityFor(accused)).thenReturn(lowComplexityRange);

    ChangeRate rarelyChanged = new RarelyChanged();
    when(metricsDataSource.getChangeRateFor(accused)).thenReturn(rarelyChanged);

    QuadrantAreaDetector detector = new QuadrantAreaDetector(metricsDataSource);
    
    // when
    Quadrant chosenQuadrant = detector.detectAreaFor(accused);
    
    // then
    assertThat(chosenQuadrant).isEqualTo(expectedQuadrant);
  }

}
