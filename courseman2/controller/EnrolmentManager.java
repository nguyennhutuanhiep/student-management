package courseman2.controller;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import courseman2.model.Enrolment;
import courseman2.model.Module;
import courseman2.model.Student;
import courseman2.view.EasyTable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @overview The GUI to manage enrollments. 
 *   
 * @attributes
 *  studentManager  StudentManager
 *  moduleManager   ModuleManager
 * @abstract_properties
 * <pre>
 *   optional(studentManager) = false /\
 *   optional(moduleManager) = false
 * </pre>  
 */

public class EnrolmentManager extends Manager
{ 
    @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
    private StudentManager studentManager;
    @DomainConstraint(type=DomainConstraint.Type.UserDefined,optional=false)
    private ModuleManager moduleManager;
    
    private JPanel panelEnrolment;
    private JLabel lblStudent, lblModule, lblInternMark, lblExamMark;
    private JTextField tfStudent, tfModule, tfInternMark, tfExamMark;
    
   /**
    * @effects 
    * initialise <tt>this</tt> with a <tt>gui</tt> using the specified settings 
    */
    public EnrolmentManager(String title, String titleText, int width, int height, int x, int y, StudentManager sm, ModuleManager mm)
    {
        super(title, titleText, width, height, x, y);
        this.studentManager = sm;
        this.moduleManager = mm;
    }
    
   /**
    *@requires gui != null
    *@effects create a (middle) data panel for displaying the <b>input labels and
    *          fields</b> appropriate for the type of <b>objects</b> enrolments    
    */
    @Override
    protected void createMiddlePanel() {
        panelEnrolment = new JPanel(new GridLayout(0, 2));
        
        lblStudent = new JLabel("student");
        lblModule = new JLabel("module");
        lblInternMark = new JLabel("internalMark");
        lblExamMark = new JLabel("examMark");
        
        tfStudent = new JTextField();
        tfModule = new JTextField();
        tfInternMark = new JTextField();
        tfExamMark = new JTextField();
        
        lblStudent.setLabelFor(tfStudent);
        lblModule.setLabelFor(tfModule);
        lblInternMark.setLabelFor(tfInternMark);
        lblExamMark.setLabelFor(tfExamMark);
        
        gui.add(panelEnrolment, BorderLayout.CENTER);
        panelEnrolment.add(lblStudent);
        panelEnrolment.add(tfStudent);
        panelEnrolment.add(lblModule);
        panelEnrolment.add(tfModule);
        panelEnrolment.add(lblInternMark);
        panelEnrolment.add(tfInternMark);
        panelEnrolment.add(lblExamMark);
        panelEnrolment.add(tfExamMark);
    }

   /**
    * @effects 
    * <pre>
    *  create a new enrolment object from the data in the data panel of
    *     gui and add it to objects
    *     
    *  if succeeded 
    *     return the new object
    *  else 
    *     throws NotPossibleException
    *  </pre>
    */
    @Override
    public Object createObject() throws NotPossibleException 
    {
        Vector studentList = studentManager.objects;
        Vector moduleList = moduleManager.objects;
        Student student = null;
        Module module = null;
        String i = tfInternMark.getText();
        String e = tfExamMark.getText();
        Float internMark = -1f;
        Float examMark = -1f;
        String errorMessage = "";
        
        Iterator iteratorStudent = studentList.iterator();
        while(iteratorStudent.hasNext())
        {
            Student s  = (Student) iteratorStudent.next();
            if(s.getId().equalsIgnoreCase(tfStudent.getText()))
            {
                student = s;
            }
        }
        
        Iterator iteratorModule = moduleList.iterator();
        while(iteratorModule.hasNext())
        {
            Module m  = (Module) iteratorModule.next();
            if(m.getCode().equalsIgnoreCase(tfModule.getText()))
            {
                module = m;
            }
        }

        try
        {
            internMark = Float.parseFloat(i);
            examMark = Float.parseFloat(e);
        }
        catch(NumberFormatException ex)
        {
            
        }
        if(student == null || module == null || internMark < 0f || examMark < 0f || internMark > 10f || examMark > 10f)
        {
            if(student == null)
            {
                errorMessage += "Wrong input value: student = " + tfStudent.getText();
            }
            if(module == null)
            {
                errorMessage += "\nWrong input value: module = " + tfModule.getText();
            }
            if(internMark < 0f || internMark > 10f)
            {
                errorMessage += "\nWrong input value: internalMark = " + tfInternMark.getText();
            }
            if(examMark < 0f || internMark > 10f)
            {
                errorMessage += "\nWrong input value: examMark = " + tfExamMark.getText();
            }
            displayErrorMessage(errorMessage, "Create an enrolment");
            throw new NotPossibleException("EnrolmentManager.createObject(): Cannot create object");
        }
        else
        {
            Enrolment en = new Enrolment(student, module, internMark, examMark);
            objects.add(en);
            displayMessage(en.toString(), "Create an enrolment");
            return en;
        }
    }

   /**
    * @requires <tt>objects != null</tt>
    * @effects save <tt>objects</tt> to file <tt>enrolments.dat</tt>
    *          
    *          <pre>if succeeds in saving objects
    *            display a console message
    *          else 
    *            display a console error message</pre>
    */
    @Override
    public void save() 
    {
       try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("enrolments.dat"));
            for (Iterator it = objects.iterator(); it.hasNext();)
            {
                Enrolment e = (Enrolment) it.next();
                oos.writeObject(e);
            }
            oos.close();
            System.out.println("Enrolment object saved");
        }
        catch(IOException ex)
        {
            System.out.println("EnrolmentManager.save(): Cannot save enrolment object.");
        }
    }
    
   /**
    * @requires <tt>objects != null</tt>
    * @modifies this
    * @effects load into <tt>objects</tt> the enrolment objects in the storage file
    *          <tt>enrolments.dat</tt>
    *           
    *          <pre>if succeeds 
    *            display a console message 
    *          else 
    *            display a console error message</pre>
    */ 
    @Override
    public void startUp() 
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("enrolments.dat"));
            try
            {
                while(true)
                {
                    Enrolment e;
                    e = (Enrolment) ois.readObject();
                    objects.add(e);
                }
            }
            catch(EOFException e)
            {
            }
            ois.close();
            System.out.println("Enrolment object loaded.");
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("EnrolmentManager.startUp(): Cannot load enrolment object.");         
        }
    }

   /**
    * @effects clear the GUI components on <tt>gui</tt> 
    */
    @Override
    public void clearGUI() {
        super.clearGUI(panelEnrolment);
    }
       
   /**
    * @requires object != null
    * @effects display the initial report on a report GUI
    */
    public void report()
    {
        String[] header = {"No", "Student ID", "Student name", "Module code", "Module name"};
        int index = 0;
        
        EasyTable easyTable = new EasyTable(header);
        for(Object obj: objects)
        {
            int newRowIndex = easyTable.addRow();
            Enrolment en = (Enrolment) obj;
            index++;
            easyTable.setValueAt(index, newRowIndex, 0);
            easyTable.setValueAt(en.getStudent().getId(), newRowIndex, 1);
            easyTable.setValueAt(en.getStudent().getName(), newRowIndex, 2);
            easyTable.setValueAt(en.getModule().getCode(), newRowIndex, 3);
            easyTable.setValueAt(en.getModule().getName(), newRowIndex, 4);
        }
        
        JFrame reportForm = new JFrame("List of the initial enrollments");
        reportForm.setSize(600, 300);
        reportForm.add(new JScrollPane(easyTable), BorderLayout.CENTER);
        reportForm.setVisible(true);
    }
    
   /**
    * @requires object != null
    * @effects display the assessed report on a report GUI
    */
    public void reportAssessment()
    {
        String[] header = {"No", "Student ID", "Module code", "Internal mark", 
                        "Exam mark", "Final grade"};
        
        EasyTable easyTable = new EasyTable(header);
        int index = 0;
        
        for(Object obj: objects)
        {
            int newRowIndex = easyTable.addRow();
            Enrolment en = (Enrolment) obj;
            index++;
            easyTable.setValueAt(index, newRowIndex, 0);
            easyTable.setValueAt(en.getStudent().getId(), newRowIndex, 1);
            easyTable.setValueAt(en.getModule().getCode(), newRowIndex, 2);
            easyTable.setValueAt(en.getInternalMark(), newRowIndex, 3);
            easyTable.setValueAt(en.getExamMark(), newRowIndex, 4);
            easyTable.setValueAt(en.getFinalGrade(), newRowIndex, 5);
        }
        
        JFrame reportForm = new JFrame("List of the assessed enrollments");
        reportForm.setSize(700, 300);
        reportForm.add(new JScrollPane(easyTable), BorderLayout.CENTER);
        reportForm.setVisible(true);
    }
}
