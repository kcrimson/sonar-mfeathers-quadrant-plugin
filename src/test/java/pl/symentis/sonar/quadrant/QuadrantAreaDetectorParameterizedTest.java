package pl.symentis.sonar.quadrant;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.symentis.sonar.quadrant.Quadrant.breedinggrounds;
import static pl.symentis.sonar.quadrant.Quadrant.designflaw;
import static pl.symentis.sonar.quadrant.Quadrant.tools;
import static pl.symentis.sonar.quadrant.Quadrant.uglystables;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class QuadrantAreaDetectorParameterizedTest {

  private final FileID accused;
  private final ComplexityRange complexity;
  private final ChangeRate changeRate;
  private final Quadrant expectedQuadrant;

  @Parameters(name = "File {0}, complex: {1}, changed: {2} => quadrant: {3}. Set # {index}")
  public static Collection<Object[]> inputs() {
    Object[][] data = {
        { new FileID("rarelyChangedAndSimpleFile"), new LowComplexityRange(), new RarelyChanged(), tools },
        { new FileID("oftenChangedSimpleFile"), new LowComplexityRange(), new FrequentlyChanged(), breedinggrounds },
        { new FileID("fileWithDesignFlaw"), new HighComplexityRange(), new FrequentlyChanged(), designflaw },
        { new FileID("oftenChangedComplexFile"), new HighComplexityRange(), new RarelyChanged(), uglystables }
 };
    return Arrays.asList(data);
  }

  public QuadrantAreaDetectorParameterizedTest(FileID file, ComplexityRange complexity, ChangeRate changeRate,
      Quadrant quadrant) {
    this.accused = file;
    this.complexity = complexity;
    this.changeRate = changeRate;
    this.expectedQuadrant = quadrant;
  }

  @Test
  public void shouldJudgeByComplexityRatherThanByName() {
    // given
    MetricsDataSource metricsDataSource = Mockito.mock(MetricsDataSource.class);
    QuadrantAreaDetector detector = new QuadrantAreaDetector(metricsDataSource);

    // when
    when(metricsDataSource.getComplexityFor(accused)).thenReturn(complexity);
    when(metricsDataSource.getChangeRateFor(accused)).thenReturn(changeRate);
    
    Quadrant chosenQuadrant = detector.detectAreaFor(accused);
    
    // then
    assertThat(chosenQuadrant).isEqualTo(expectedQuadrant);
  }

}
