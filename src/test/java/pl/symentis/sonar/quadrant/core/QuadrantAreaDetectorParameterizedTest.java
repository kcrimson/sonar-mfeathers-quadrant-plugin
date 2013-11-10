package pl.symentis.sonar.quadrant.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.symentis.sonar.quadrant.api.Quadrant.breedinggrounds;
import static pl.symentis.sonar.quadrant.api.Quadrant.designflaw;
import static pl.symentis.sonar.quadrant.api.Quadrant.tools;
import static pl.symentis.sonar.quadrant.api.Quadrant.uglystables;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import pl.symentis.sonar.quadrant.api.FileID;
import pl.symentis.sonar.quadrant.api.Quadrant;
import pl.symentis.sonar.quadrant.api.criteria.ChangeRate;
import pl.symentis.sonar.quadrant.api.criteria.ComplexityRange;
import pl.symentis.sonar.quadrant.api.criteria.FrequentlyChanged;
import pl.symentis.sonar.quadrant.api.criteria.HighComplexityRange;
import pl.symentis.sonar.quadrant.api.criteria.LowComplexityRange;
import pl.symentis.sonar.quadrant.api.criteria.RarelyChanged;
import pl.symentis.sonar.quadrant.core.MetricsDataSource;

@RunWith(Parameterized.class)
public class QuadrantAreaDetectorParameterizedTest {

  private final FileID accused;
  private final ComplexityRange complexity;
  private final ChangeRate changeRate;
  private final Quadrant expectedQuadrant;

  @Parameters(name = "Misleading name {0}, complex: {1}, changed: {2} => quadrant: {3}. Set # {index}")
  public static Collection<Object[]> inputs() {
    Object[][] data = {
        { new FileID("fileWithDesignFlaw"), new LowComplexityRange(), new RarelyChanged(), tools },
        { new FileID("oftenChangedComplexFile"), new LowComplexityRange(), new FrequentlyChanged(), breedinggrounds },
        { new FileID("rarelyChangedAndSimpleFile"), new HighComplexityRange(), new FrequentlyChanged(), designflaw },
        { new FileID("oftenChangedSimpleFile"), new HighComplexityRange(), new RarelyChanged(), uglystables }
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
    OldQuadrantAreaDetector detector = new OldQuadrantAreaDetector(metricsDataSource);

    // when
    when(metricsDataSource.getComplexityFor(accused)).thenReturn(complexity);
    when(metricsDataSource.getChangeRateFor(accused)).thenReturn(changeRate);
    
    Quadrant chosenQuadrant = detector.detect(accused);
    
    // then
    assertThat(chosenQuadrant).isEqualTo(expectedQuadrant);
  }

}
