package courseman2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import courseman2.controller.EnrolmentManager;
import courseman2.controller.ModuleManager;
import courseman2.controller.StudentManager;

/**
 * @overview Represents the main class of the CourseMan program. 
 *   
 * @attributes
 *  initial       String
 *  sman          StudentManager
 *  mman          ModuleManager
 *  emain         EnrolmentManager
 *  lhelper       CourseManDemo.LogoHelper
 *  mainGUI       JFrame
 *    
 * @abstract_properties
 * <pre>
 *   optional(initial) = false /\ 
 *   optional(sman) = false /\
 *   optional(mman) = false /\
 *   optional(eman) = false /\
 *   optional(lhelper) = false /\  
 *   optional(mainGUI) = false /\ 
 * </pre>  
 * 
 * @author dmle
 */
public class CourseManDemo implements ActionListener {
  /** the initial used as logo */
  @DomainConstraint(type=DomainConstraint.Type.String,optional=false)
  private String initial;
  
  /**the student manager*/
  @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
  private StudentManager sman;
  
  /**the module manager*/
  @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
  private ModuleManager mman;
  
  /**the enrolment manager*/
  @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
  private EnrolmentManager eman;
  
  /**the logo helper used for creating a visual effect on the logo */
  @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
  private LogoHelper lhelper;
  
  /**the main GUI window*/
  @DomainConstraint(type=DomainConstraint.Type.Object,optional=false)
  private JFrame mainGUI;
  
  private JLabel logo;

  // constructor method
  /**
   * @effects 
   *  initialise this with initial
   *  {@link #createGUI()}: create mainGUI
   *  initialise sman, mman, eman such that their (x,y) locations are each 50 pixels higher
   *    those of mainGUI
   *  initialise lhelper  
   */
  public CourseManDemo(String initial)
  {
      this.initial = initial;
      createGUI();
      sman = new StudentManager("Manage students", "Enter student details", 400, 300, 350, 250);
      mman = new ModuleManager("Manage modules", "Enter module details", 400, 300, 350, 250);
      eman = new EnrolmentManager("Manage enrolments", "Enter enrolment details", 400, 300, 350, 250, sman, mman);
      lhelper = new LogoHelper();
  }

  /**
   * @modifies this
   * @effects create mainGUI that has a menu bar with:
   *   
   *   File menu has two items: Save and Exit
   *   Tools has three menu items for the three data management functions
   *   Reports: has the two reporting functions
   *   {@link #createLogoPanel(JMenuBar, String)}: a logo panel containing a 
   *          logo label at the far end of the menu bar
   *   
   *     
   *   The action listener of the menu items is this.
   */
  protected void createGUI()
  {
      mainGUI = new JFrame("Program CourseManDemo");
      mainGUI.setLocation(300, 200);
      mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JMenuBar menuBar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenuItem save = new JMenuItem("Save");
      save.addActionListener(this);
      JMenuItem exit = new JMenuItem("Exit");
      exit.addActionListener(this);
      file.add(save);
      file.add(exit);
      
      JMenu tools = new JMenu("Tools");
      JMenuItem manageStudent = new JMenuItem("Manage student");
      manageStudent.addActionListener(this);
      JMenuItem manageModule = new JMenuItem("Manage module");
      manageModule.addActionListener(this);
      JMenuItem manageEnrolment = new JMenuItem("Manage enrolment");
      manageEnrolment.addActionListener(this);
      tools.add(manageStudent);
      tools.add(manageModule);
      tools.add(manageEnrolment);
      
      JMenu reports = new JMenu("Reports");
      JMenuItem initialReport = new JMenuItem("Initial report");
      initialReport.addActionListener(this);
      JMenuItem assessedReport = new JMenuItem("Assessed report");
      assessedReport.addActionListener(this);
      reports.add(initialReport);
      reports.add(assessedReport);
      
      menuBar.setPreferredSize(new Dimension(100, 35));
      menuBar.add(file);
      menuBar.add(tools);
      menuBar.add(reports);
      mainGUI.setJMenuBar(menuBar);
      
      createLogoPanel(menuBar, initial);
  }

  /**
   * @effects 
   *  create a label panel containing a decorated JLabel whose text is 
   *  initial. The decoration must use the following settings:
   *  
   *    background colour: orange
   *    foreground colour: blue
   *    font: Serif, bold, 18 points
   *    size: height=20, wide enough to fit the text
   *    alignment: center
   *    focusable: false 
   *  
   *  
   *  add the label panel to the menu bar mb so that it appears at the far end.
   *
   *  The logo text must have the "appearing" effect.
   */
  private void createLogoPanel(JMenuBar mb, String initial)
  { 
    JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    logo = new JLabel(initial);
    logo.setOpaque(true);
    logo.setBackground(Color.ORANGE);
    logo.setForeground(Color.BLUE);
    logo.setFont(new Font("Serif", Font.BOLD, 18));
    logo.setSize(80, 20);
    logo.setHorizontalAlignment(JLabel.CENTER);
    logo.setFocusable(false);
    
    logoPanel.add(logo);
    mb.add(logoPanel);
  }
  
  /**
   * @effects save data objects managed by sman, mman and eman to files 
   */
  public void save()
  {
      sman.save();
      mman.save();
      eman.save();
  }

  /**
   * @effects 
   *  start up sman, mman, eman 
   *  start lhelper
   */
  public void startUp()
  {
      sman.startUp();
      mman.startUp();
      eman.startUp();
      lhelper.start();
  }

  /**
   * @effects shut down sman, mman, eman 
   *          dispose mainGUI and end the program. 
   */
  public void shutDown()
  {
      sman.shutDown();
      mman.shutDown();
      eman.shutDown();
      mainGUI.dispose();
      mainGUI.addWindowListener(new WindowAdapter() {
        public void WindowClosing(WindowEvent WindowEvent) 
        {
            super.windowClosing(WindowEvent);
             System.exit(0);
        }
      });
  }

  /**
   * @effects show mainGUI 
   */
  public void display()
  {
    mainGUI.setSize(420, 110);
    mainGUI.setVisible(true);
  }
  /**
   * @effects handles user actions on the menu items
   *          <pre>
   *          if menu item is Tools/Manage students
   *            {@link #sman}.display()}
   *          else if menu item is Tools/Manage modules  
   *            {@link #mman}.display()
   *          else if menu item is Tools/Manage enrolments
   *            {@link #eman}.display()
   *          else if menu item is Reports/Initial enrolment report
   *            {@link #eman}.report()
   *          else if menu item is Reports/Assessment report
   *            {@link #eman}.reportAssessment()
   *          else if menu item is File/Save
   *            {@link #save()}
   *          else if menu item is File/Exit 
   *            {@link #shutDown()}
   *          </pre>
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
      String str = e.getActionCommand();
      switch (str) {
          case "Exit":
              shutDown();
              break;
          case "Manage student":
              sman.display();
              break;
          case "Manage module":
              mman.display();
              break;
          case "Manage enrolment":
              eman.display();
              break;
          case "Initial report":
              eman.report();
              break;
          case "Assessed report":
              eman.reportAssessment();
              break;
          case "Save":
              save();
              break;
          default:
              break;
      }
  }

  
  /**
   * @effects: handle the logo blinking effect
   */
  private class LogoHelper implements Runnable
  {
      private boolean status;
      
      public LogoHelper()
      {
        status = false;
      }
      
      @Override
      public void run()
      {
        while (true) 
        {
            try 
            {
                Thread.sleep(1200);
            } 
            catch (InterruptedException e) 
            {
               System.out.println("Error: " + e.getMessage());
            }
            logo.setText(initial);
              
            if (!status) 
            {
                try 
                {
                    Thread.sleep(1200);
                } 
                catch (InterruptedException e) 
                {
                    System.out.println("Error: " + e.getMessage());
                }
                logo.setText(null);
            }
        }
      }
      public void start() 
      {
        Thread t = new Thread(lhelper);
        t.start();
      }
  }
  
  /**
   * The run method
   * @effects 
   *  initialise an initial 
   *  create an instance of CourseManDemo from the initial 
   *  {@link #startUp()}: start up the CourseManDemo instance
   *  {@link #display()}: display the main gui of CourseManDemo instance 
   */
  public static void main(String[] args) {
    final String initial = "NNTH-NTTH";
    CourseManDemo app = new CourseManDemo(initial);

    app.startUp();
    app.display();
  }
}
