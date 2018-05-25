package courseman2.model;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import java.io.Serializable;
/**
 * @overview Student is a learner or someone who attends an educational institution
 * @attributes
 *  id       String
 *  name     String
 *  dob      String
 *  address  String
 *  email    String
 * @object  A typical student is s=<i,n,d,a,e>, where id(i), name(n), dob(d), 
 *          address(a), email(e).
 * @attributes
 *  mutable(id) = false /\ optional(id) = false /\
 *  mutable(name) = true /\ optional(name) = false /\
 *  mutable(dob) = true /\ optional(dob) = false /\
 *  mutable(address) = true /\ optional(address) = false /\
 *  mutable(email) = true /\ optional(email) = false
 */
public class Student implements Serializable
{
    private static int count = 2018;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=false,optional=false)
    private String id;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true, optional = true)
    private String name;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true, optional = false)
    private String dob;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true, optional = false)
    private String address;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true, optional = false)
    private String email;
    
    /**
     * @effects <pre>
     *     if n, d, a, e are valid
     *        initialize this as Student:<i,n,d,a,e>
     *     else
     *        throw NotPossibleException
     *      </pre>
     * 
     */
    public Student(String n, String d, String a, String e) throws NotPossibleException
    {
        this.id = "S" + count;
        if(validate(n, d, a, e))
        {
            this.name = n;
            this.dob = d;
            this.address = a;
            this.email = e;
            count++;
        }
        else
        {
            throw new NotPossibleException("Student<init>: invalid arguments");
        }
    }
 
    /**
     * @effects return <tt>this.id</tt>
     */
    
    public String getId()
    {
        return id;
    }
    
    /**
     * @effects return <tt>this.name</tt>
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @effects return <tt>this.dob</tt>
     */
    public String getDate()
    {
        return dob;
    }
    
    /**
     * @effects return <tt>this.address</tt>
     */
    public String getAddress()
    {
        return address;
    }
    
    /**
     * @effects return <tt>this.email</tt>
     */
    public String getEmail()
    {
        return email;
    }  
    
    /**
     * @effects <pre>
     *      if n is valid
     *          set this.name = name
     *      else
     *          throws NotPossibleException</pre>
     */
    public void setName(String n) throws NotPossibleException
    {
        if(validateName(n))
            this.name = n;
        else
           throw new NotPossibleException("Student.setName: invalid name: " + n);
    }
    
    /**
     * @effects <pre>
     *      if d is valid
     *          set this.dob = d
     *      else
     *          throws NotPossibleException</pre>
     * @param d 
     */
    public void setDate(String d) throws NotPossibleException
    {
        if(validateDob(d))
            this.dob = d;
        else
            throw new NotPossibleException("Student.setDate: invalid date: " + d);
    }
    
    /**
     * @effects <pre>
     *      if a is valid
     *          set this.address = a
     *      else
     *          throws NotPossibleException</pre>
     * @param a 
     */
    public void setAddress(String a) throws NotPossibleException
    {
        if(validateAddress(a))
            this.address = a;
        else
            throw new NotPossibleException("Student.setAddress: invalid address: " + a);
    }
    
    /**
     * @effects <pre>
     *      if e is valid
     *          set this.email = e
     *      else
     *          throws NotPossibleException</pre>
     * @param e 
     */
    public void setEmail(String e) throws NotPossibleException
    {
        if(validateEmail(e))
            this.email = e;
        else
            throw new NotPossibleException("Student.setEmail: invalid email: " + e);
    }
    
    @Override
    public String toString()
    {
        return "Created Student(" + this.id + "," + this.name + ","
                + this.dob + "," + this.address + "," + this.email + ")";
    }
    
    public boolean repOK()
    {
        return validate(name, dob, address, email);
    }
    
    /**
     * @effects <pre>
     *      if <n, d, a, e> is a valid tuple
     *          return true
     *      else
     *          return false</pre> 
     */
    private boolean validate(String n, String d, String a, String e)
    {
        return validateName(n) && validateDob(d) &&
               validateAddress(a) && validateEmail(e);
    }
    
    /**
     * @effects <pre>
     *      if n is a valid name
     *          return true
     *      else
     *          return false
     */
    public boolean validateName(String n)
    {
        if (n == null || n.length() == 0)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if d is a valid dob
     *          return true
     *      else
     *          return false
     */
    public boolean validateDob(String d)
    {
        if (d == null || d.length() == 0)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if a is a valid address
     *          return true
     *      else
     *          return false
     */
    public boolean validateAddress(String a)
    {
        if (a == null || a.length() == 0)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if e is a valid email
     *          return true
     *      else
     *          return false
     */
    public boolean validateEmail(String e)
    {
        if (e == null || e.length() == 0)
            return false;
        else
            return true;
    }
}
