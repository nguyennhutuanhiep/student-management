package courseman2.controller;

import courseman2.NotPossibleException;
import courseman2.model.CompulsoryModule;
import courseman2.model.ElectiveModule;
import courseman2.model.Module;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Iterator;

/**
 * @overview The GUI to manage modules. 
 */

public class ModuleManager extends Manager
{   
    private JPanel panelModule;
    private JLabel lblModule, lblName, lblSemester, lblCredits, lblDeptName;
    private JTextField tfName, tfSemester, tfCredits, tfDeptName;
    private JComboBox cbModuleType;
    
   /**
    * @effects 
    * initialise <tt>this</tt> with a <tt>gui</tt> using the specified settings 
    */
    public ModuleManager(String title, String titleText, int width, int height, int x, int y)
    {
        super(title, titleText, width, height, x, y);
    }
    
   /**
    *@requires gui != null
    *@effects create a (middle) data panel for displaying the <b>input labels and
    *          fields</b> appropriate for the type of <b>objects</b> modules    
    */
    @Override
    protected void createMiddlePanel() {
        panelModule = new JPanel(new GridLayout(0, 2));
        lblModule = new JLabel("Module type:");
        lblName = new JLabel("name");
        lblSemester = new JLabel("semester");
        lblCredits = new JLabel("credits");
        lblDeptName = new JLabel("deptName");
        
        tfName = new JTextField();
        tfSemester = new JTextField();
        tfCredits = new JTextField();
        tfDeptName = new JTextField();
        
        lblModule.setLabelFor(cbModuleType);
        lblName.setLabelFor(tfName);
        lblSemester.setLabelFor(tfSemester);
        lblCredits.setLabelFor(tfCredits);
        lblDeptName.setLabelFor(tfDeptName);
        
        String[] types = {"Compulsory", "Elective"};
        cbModuleType = new JComboBox(types);
        cbModuleType.setSelectedIndex(0);
        cbModuleType.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int i = cbModuleType.getSelectedIndex();
                if(i == 1)
                {
                    panelModule.add(lblDeptName);
                    panelModule.add(tfDeptName);
                    gui.validate();
                }
                else
                {
                    panelModule.remove(lblDeptName);
                    panelModule.remove(tfDeptName);
                    gui.validate();
                }
            }
        });
        
        gui.add(panelModule, BorderLayout.CENTER);
        panelModule.add(lblModule);
        panelModule.add(cbModuleType);
        panelModule.add(lblName);
        panelModule.add(tfName);
        panelModule.add(lblSemester);
        panelModule.add(tfSemester);
        panelModule.add(lblCredits);
        panelModule.add(tfCredits);
    }

   /**
    * @effects 
    * <pre>
    *  create a new module object from the data in the data panel of
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
        String name = tfName.getText();
        String errorMessage = "";
        int semester = 0;
        int credits = 0;
        try
        {
            semester = Integer.parseInt(tfSemester.getText());
            credits = Integer.parseInt(tfCredits.getText());
        }
        catch(NumberFormatException e)
        {
        }
        
        if(name.isEmpty() || semester <= 0 || credits <= 0)
        {
            if(name.isEmpty())
            {
                errorMessage += "Wrong input value: name = " + tfName.getText();
            }
            if(semester <= 0)
            {
                errorMessage += "\nWrong input value: semester = " + tfSemester.getText();
            }
            if(credits <= 0)
            {
                errorMessage += "\nWrong input value: credits = " + tfCredits.getText();
            }
            displayErrorMessage(errorMessage , "Create a module");
            throw new NotPossibleException("ModuleManager.createObject(): Cannot create object"); 
        }
        else
        {
            if(cbModuleType.getSelectedIndex() == 1)
            {
                String deptName = tfDeptName.getText();
                if(deptName.isEmpty())
                {
                    errorMessage += "Wrong input value: deptName = ";
                    displayErrorMessage(errorMessage , "Create a module");
                    throw new NotPossibleException("ModuleManager.createObject(): Cannot create object"); 
                }
                else
                {
                    Module m = new ElectiveModule(name, semester, credits, deptName);
                    objects.add(m);
                    displayMessage(m.toString(), "Create a module");
                    return m;
                }
            }
            else
            {
                Module m = new CompulsoryModule(name, semester, credits);
                objects.add(m);
                displayMessage(m.toString(), "Create a module");
                return m;
            } 
        }
    }

   /**
    * @requires <tt>objects != null</tt>
    * @effects save <tt>objects</tt> to file <tt>modules.dat</tt>
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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("modules.dat"));
            for (Iterator it = objects.iterator(); it.hasNext();)
            {
                Module m = (Module) it.next();
                oos.writeObject(m);
            }
            oos.close();
            System.out.println("Module object saved.");
        }
        catch(IOException ex)
        {
            System.out.println("ModuleManager.save(): Module object cannot save.");
        }
    }

   /**
    * @requires <tt>objects != null</tt>
    * @modifies this
    * @effects load into <tt>objects</tt> the module objects in the storage file
    *          <tt>modules.dat</tt>
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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("modules.dat"));
            try
            {
                while(true)
                {
                    Module m = (Module) ois.readObject();
                    objects.add(m);
                }
            }
            catch(EOFException e)
            {
            }
            ois.close();
            System.out.println("Module object loaded.");
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("ModuleManager.startUp(): Cannot load module object.");         
        }
    }

   /**
    * @effects clear the GUI components on <tt>gui</tt> 
    */
    @Override
    public void clearGUI() {
        super.clearGUI(panelModule);
    }
}
