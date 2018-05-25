package courseman2.controller;

import courseman2.NotPossibleException;
import courseman2.model.Student;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Iterator;

/**
 * @overview The GUI to manage students. 
 */

public class StudentManager extends Manager
{   
    private JLabel lblName, lblDob, lblAddress, lblEmail;
    private JTextField tfName, tfDob, tfAddress, tfEmail;
    private JPanel panelStudent;
    
   /**
    *@effects 
    * initialise <tt>this</tt> with a <tt>gui</tt> using the specified settings 
    */
    public StudentManager(String title, String titleText, int width, int height, int x, int y)
    {
        super(title, titleText, width, height, x, y);
    }
    
  /**
   * @requires gui != null
   * @effects create a (middle) data panel for displaying the <b>input labels and
   *          fields</b> appropriate for the type of <b>objects</b> students    
   */
    @Override
    protected void createMiddlePanel() 
    {
        panelStudent = new JPanel(new GridLayout(0, 2));
        
        lblName = new JLabel("name");
        lblDob = new JLabel("dob");
        lblAddress = new JLabel("address");
        lblEmail = new JLabel("email");
        
        tfName = new JTextField();
        tfDob = new JTextField();
        tfAddress = new JTextField();
        tfEmail = new JTextField();
        
        lblName.setLabelFor(tfName);
        lblDob.setLabelFor(tfDob);
        lblAddress.setLabelFor(tfAddress);
        lblEmail.setLabelFor(tfEmail);
        
        panelStudent.add(lblName);
        panelStudent.add(tfName);
        panelStudent.add(lblDob);
        panelStudent.add(tfDob);
        panelStudent.add(lblAddress);
        panelStudent.add(tfAddress);
        panelStudent.add(lblEmail);
        panelStudent.add(tfEmail);
     
        gui.add(panelStudent, BorderLayout.CENTER);
    }

   /**
    * @effects 
    * <pre>
    *  create a new student object from the data in the data panel of
    *     gui and add it to objects
    *     
    *  if succeeded 
    *     return the new object
    *  else 
    *     throws NotPossibleException
    *  </pre>
    */
    @Override
    public Object createObject() throws NotPossibleException {
        String name = tfName.getText();
        String dob = tfDob.getText();
        String address = tfAddress.getText();
        String email = tfEmail.getText();
        String errorMessage = "";
        
        if(name.isEmpty() || dob.isEmpty() || address.isEmpty() || email.isEmpty())
        {
            if(name.isEmpty())
            {
                errorMessage += "Wrong input value: name = " + tfName.getText();
            }
            if(dob.isEmpty())
            {
                errorMessage += "\nWrong input value: dob = " + tfDob.getText();
            }
            if(address.isEmpty())
            {
                errorMessage += "\nWrong input value: address = " + tfAddress.getText();
            }
            if(email.isEmpty())
            {
                errorMessage += "\nWrong input value: email = " + tfEmail.getText();
            }
            displayErrorMessage(errorMessage , "Create a module");
            throw new NotPossibleException("ModuleManager.createObject(): Cannot create object"); 
        }
        else
        {
            Student s = new Student(name, dob, address, email);
            objects.add(s);
            displayMessage(s.toString(), "Create a student");
            return s;
        }    
    }

   /**
    * @requires <tt>objects != null</tt>
    * @effects save <tt>objects</tt> to file <tt>students.dat</tt>
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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"));
            for (Iterator it = objects.iterator(); it.hasNext();)
            {
               Student s = (Student) it.next();
               oos.writeObject(s);
            } 
            oos.close();
            System.out.println("Student object saved.");
        }
        catch(IOException ex)
        {
            System.out.println("StudentManager.save(): Cannot save student object.");
        }
    }

   /**
    * @requires <tt>objects != null</tt>
    * @modifies this
    * @effects load into <tt>objects</tt> the student objects in the storage file
    *          <tt>students.dat</tt>
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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
            try
            {
                while(true)
                {
                    Student s = (Student) ois.readObject();
                    objects.add(s);
                }
            }
            catch(EOFException e)
            {
            }
            ois.close();
            System.out.println("Student object loaded.");
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("StudentManager.startUp(): Cannot load student object.");         
        }
    }

   /**
    * @effects clear the GUI components on <tt>gui</tt> 
    */
    @Override
    public void clearGUI() 
    {
        super.clearGUI(panelStudent);
    } 
}
