package pl.symentis.sonar.quadrant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

	/*
	 * maxy - połowa i niżej to strefy ok, połowa < wyżej to czerwone karmienie
	 * danymi po nazwie dostajemy dane, po danych określamy miejsce/kwadrant
	 * plik X dostaje te same dane -> inny komponent
	 */
}