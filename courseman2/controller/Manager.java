package courseman2.controller;

import java.util.Vector;

import courseman2.DomainConstraint;
import courseman2.DomainConstraint.Type;
import courseman2.NotPossibleException;

/**
 * @overview A sub-type of AppController that represents the abstract super-class of all the data manager
 *           classes.
 * 
 * @attributes
 *  objects   Vector
 * 
 * @abstract_properties 
 * <pre>
 *  P_AppController /\ 
 *  optional(objects) = false /\  
 *  objects.size > 0 -> (for all o in objects:
 *      o.data is obtained from the data panel of gui)
 *  </pre>
 *  
 * @author dmle
 */
public abstract class Manager extends AppController {
  /**keep the objects that are managed by this manager*/
  @DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
  protected Vector objects;

  // constructor
  /**
   * @effects 
   *  initialise <tt>this</tt> with a <tt>gui</tt> using the specified settings 
   *  and an empty set of objects
   */
  public Manager(String title, String titleText, int width, int height, int x, int y) {
    super(title, titleText, width, height, x, y);
    objects = new Vector();
  }
  
  /**
   * @requires gui != null
   * @modifies this
   * @effects create a (middle) data panel for displaying the <b>input labels and
   *          fields</b> appropriate for the type of <b>objects</b> that are managed by
   *          <tt>this</tt>
   */
  @Override
  protected abstract void createMiddlePanel();

  /**
   * @effects 
   *   {@link #createObject()}: create a new object
   *   
   *   <p>Throws NotPossibleException if failed to create object.
   */
  @Override
  public void doTask() throws NotPossibleException {
    // TODO: complete this code
    try
    {
        createObject();
    }
    catch(NotPossibleException ex)
    {
        System.out.println(ex.getMessage());
    }
  }
  
  /**
   * @effects 
   * <pre>
   *  create a new data object from the data in the data panel of
   *     gui and add it to objects
   *     
   *  if succeeded 
   *     return the new object
   *  else 
   *     throws NotPossibleException
   *  </pre>
   */
  public abstract Object createObject() throws NotPossibleException;

  /**
   * @requires <tt>objects != null</tt>
   * @effects save <tt>objects</tt> to file <tt>X.dat</tt>, where X is named
   *          after the object type,
   *          e.g. if the object type is Student then X is <tt>students</tt>.
   *          
   *          <pre>if succeeds in saving objects
   *            display a console message
   *          else 
   *            display a console error message</pre>
   */
  public abstract void save();

  /**
   * @requires <tt>objects != null</tt>
   * @modifies this
   * @effects load into <tt>objects</tt> the data objects in the storage file
   *          <tt>X.dat</tt> that was used by
   *          method <tt>save</tt> to store objects.
   *           
   *          <pre>if succeeds 
   *            display a console message 
   *          else 
   *            display a console error message</pre>
   */
  @Override
  public abstract void startUp();

  /**
   * @requires <tt>gui != null</tt>
   * @modifies this
   * @effects dispose <tt>gui</tt> and clear <tt>objects</tt>
   */
  @Override
  public void shutDown() {
    super.shutDown();
    objects.clear();
    objects = null;
  }
}
