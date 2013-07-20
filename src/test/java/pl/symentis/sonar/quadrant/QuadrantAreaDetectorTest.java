package pl.symentis.sonar.quadrant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 */
public class QuadrantAreaDetectorTest {

	private QuadrantAreaDetector detector;

	@Test
	public void should_get_tools_area_for_low_complexity_and_low_change_rate() {
		// given
		QuadrantAreaDetector detector = new QuadrantAreaDetector(100, 100);
		FileID rarelyChangedAndSimpleFile = new FileID("rarelyChangedAndSimpleFile");

		// when
		Quadrant area = detector.detectAreaFor(rarelyChangedAndSimpleFile);

		// then
		assertEquals(Quadrant.tools, area);
	}

	@Test
	public void should_get_designFlaw_area_for_high_complexity_and_high_change_rate() {
		// given
		QuadrantAreaDetector detector = new QuadrantAreaDetector(100, 100);
		FileID highComplexityAndHighChangeRateFile = new FileID("highComplexityAndHighChangeRateFile");

		// when
		Quadrant area = detector.detectAreaFor(highComplexityAndHighChangeRateFile);

		// then
		assertEquals(Quadrant.designflaw, area);
	}

	@Test
	public void should_detect_breedingGrounds_when_low_complexity_but_lotsa_changes() {
		// given
		QuadrantAreaDetector detector = new QuadrantAreaDetector(100, 100);
		FileID oftenChangedSimpleFile = new FileID("oftenChangedSimpleFile");

		// when
		Quadrant zoneDetected = detector.detectAreaFor(oftenChangedSimpleFile);
		// then
		assertEquals(Quadrant.breedinggrounds, zoneDetected);
	}

	@Test
	public void should_detect_uglyStables_when_high_complexity_but_little_changes() {
		// given
		QuadrantAreaDetector detector = new QuadrantAreaDetector(100, 100);
		FileID complexButRarelyChangedFile = new FileID("complexButRarelyChangedFile");
		// when
		Quadrant zoneDetected = detector.detectAreaFor(complexButRarelyChangedFile);
		// then
		assertEquals(Quadrant.uglystables, zoneDetected);
	}

	@Test
	public void shouldBeAbleToGetData() {
		// given
		MetricsDataSource metricsSource = new MetricsDataSource();
		detector = new QuadrantAreaDetector(100, 100, metricsSource);
		// when - then
		assertTrue("MetricsDataSource is down, can't detect", detector.canWork());
	}

	@Test
	public void shouldNotBeAbleToGetDataWhenMetricsDataSourceCantWork() {
		// given
		MetricsDataSource noMetricsSource = null;
		detector = new QuadrantAreaDetector(100, 100, noMetricsSource);
		// when - then
		assertFalse("MetricsDataSource is up, but should be down", detector.canWork());
	}

	@Test
	public void shouldAcceptFileIdToDetectArea(){
		detector = new QuadrantAreaDetector(100, 100);

		FileID fileId = new FileID("complexButRarelyChangedFile"); // values or exception
		
		Quadrant quadrant = detector.detectAreaFor(fileId);

		assertNotNull(quadrant);
		
	}
	
	/*
	 * maxy - połowa i niżej to strefy ok, połowa < wyżej to czerwone karmienie
	 * danymi po nazwie dostajemy dane, po danych określamy miejsce/kwadrant
	 * 
	 * 
	 * plik X dostaje te same dane -> inny komponent
	 */
}