package pl.symentis.sonar.quadrant.api;


/**
 * Abstraction for code that pulls in information that allow it to determine
 * which quadrant the file belongs to.
 * 
 * @author Tomasz.Borek (Tomasz.Borek@gmail.com)
 * 
 */
public interface QuadrantDetector {

  /**
   * Crux of whole plug-in.
   * 
   * @param fileID that will be matched against quadrants
   * @return quadrant where file belongs to, per chosen criteria
   */
  public Quadrant detect(FileID fileID);
}
