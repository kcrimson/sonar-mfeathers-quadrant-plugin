package pl.symentis.sonar.quadrant;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class QuadrantAreaDetectorTest {

  @InjectMocks
	private QuadrantAreaDetector detector;

  @Mock
  private MetricsDataSource metricsDataSource;

  @Before
  public void injectMocks() {
    MockitoAnnotations.initMocks(this);
  }

	@Test
	public void should_get_tools_area_for_low_complexity_and_low_change_rate() {
		// given
		FileID rarelyChangedAndSimpleFile = new FileID("rarelyChangedAndSimpleFile");

		// when
		Quadrant area = detector.detectAreaFor(rarelyChangedAndSimpleFile);

		// then
		assertEquals(Quadrant.tools, area);
	}

	@Test
	public void should_get_designFlaw_area_for_high_complexity_and_high_change_rate() {
		// given
		FileID highComplexityAndHighChangeRateFile = new FileID("highComplexityAndHighChangeRateFile");

		// when
		Quadrant area = detector.detectAreaFor(highComplexityAndHighChangeRateFile);

		// then
		assertEquals(Quadrant.designflaw, area);
	}

	@Test
	public void should_detect_breedingGrounds_when_low_complexity_but_lotsa_changes() {
		// given
		FileID oftenChangedSimpleFile = new FileID("oftenChangedSimpleFile");

		// when
		Quadrant zoneDetected = detector.detectAreaFor(oftenChangedSimpleFile);
		// then
		assertEquals(Quadrant.breedinggrounds, zoneDetected);
	}

	@Test
	public void should_detect_uglyStables_when_high_complexity_but_little_changes() {
		// given
		FileID complexButRarelyChangedFile = new FileID("complexButRarelyChangedFile");
		// when
		Quadrant zoneDetected = detector.detectAreaFor(complexButRarelyChangedFile);
		// then
		assertEquals(Quadrant.uglystables, zoneDetected);
	}

	@Test
	public void shouldBeAbleToGetData() {
    assertTrue("Surprisingly, MetricsDataSource is down, can't detect", detector.canWork());
	}

	@Test
	public void shouldNotBeAbleToGetDataWhenMetricsDataSourceCantWork() {
		// given
		MetricsDataSource noMetricsSource = null;
    detector = new QuadrantAreaDetector(noMetricsSource);
		// when - then
		assertFalse("MetricsDataSource is up, but should be down", detector.canWork());
	}

	@Test
	public void shouldAcceptFileIdToDetectArea(){
    // given
		FileID fileId = new FileID("complexButRarelyChangedFile"); // values or exception
		
    // when
		Quadrant quadrant = detector.detectAreaFor(fileId);

    // then
		assertNotNull(quadrant);
		
	}
	
  @Test
  public void randomFileIDShouldNotBeADesignFlaw() {
    // given
    // 5 has no meaning here
    FileID randomFileId = new FileID(RandomStringUtils.random(5));

    when(metricsDataSource.getComplexityFor(randomFileId)).thenReturn(new ComplexityRange());

    // when
    Quadrant quadrant = detector.detectAreaFor(randomFileId);

    // then
    assertThat(quadrant).isNotEqualTo(Quadrant.designflaw);
  }


	/*
	 * maxy - połowa i niżej to strefy ok, połowa < wyżej to czerwone karmienie
	 * danymi po nazwie dostajemy dane, po danych określamy miejsce/kwadrant
	 * 
	 * 
	 * plik X dostaje te same dane -> inny komponent
	 */
}