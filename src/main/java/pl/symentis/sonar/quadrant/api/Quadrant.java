package pl.symentis.sonar.quadrant.api;

/**
 * Concept of source-code quadrants we found through Michael Feather's blog
 * post. In a nutshell, based on McCabe's cyclomatic complexity and frequency of
 * changes done to a file, one can assign the file to one of four quadrants.
 * 
 * 
 * @author Jaroslaw Palka <jaroslaw.palka@symentis.pl>
 * @author Tomasz Borek <tomasz.borek@gmail.com>
 * @since 1.0
 * @TODO lafkblogs.wordpress.com - artykuł opisujący kwadrant
 * @see http://www.stickyminds.com/s.asp?F=S16679_COL_2
 * @see http://www.stickyminds.com/sitewide.asp?Function=edetail&ObjectType=COL&ObjectId=16679
 * @see http://eliassn.wordpress.com/2011/06/09/discovering-startling-things-from-your-version-control-system/
 * @see http://www.youtube.com/watch?v=0eAhzJ_KM-Q - 17:00
 */
public enum Quadrant {

  /**
   * low complexity, little changes - simple utilities.
   */
  tools,

  /**
   * simple yet frequently touched area of your code. Core of it, yet done well
   * and expanding. New features grow, new classes are extracted, complexity is
   * kept at bay. Keep it that way!
   */
  breedinggrounds,

  /**
   * Ugly files but stable - written once for specific purpose, which they
   * fulfill well enough. High complexity makes them risky to touch, but is
   * there any need to?
   * 
   * Ugly Stables also because of Hercules and Augias's stables... ;-)
   */
  uglystables,

  /**
   * Code fitting here is fraught with complexity and changed often. Meaning,
   * you either didn't got the client's needs right and need to make lotsa
   * changes now, or you mis-designed and now have to work around it. Or there's
   * simply a number of bugs...
   */
  designflaw

}
