package courseman2.model;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @overview Module is a is a unit of teaching that typically 
 *           lasts one academic term 
 * @attributes
 *  code        String
 *  name        String
 *  semester    Integer     int
 *  credits     Integer     int
 * @object  A typical student is m=<c,n,s,r>, where code(c), name(n), semester(s), 
 *          credits(r).
 * @attributes
 *  mutable(code) = false /\ optional(code) = false /\
 *  mutable(name) = true /\ optional(name) = false /\
 *  mutable(semester) = true /\ optional(semester) = false /\
 *  mutable(credits) = true /\ optional(credits) = false /\
 */
public class Module implements Serializable
{
    private static final ConcurrentHashMap<Integer, Integer> MAP_CODE = new ConcurrentHashMap<>();
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=false,optional=false)
    private String code;
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true,optional=false)
    private String name;
    @DomainConstraint(type = DomainConstraint.Type.Integer,mutable=true,optional=false)
    private int semester;
    @DomainConstraint(type = DomainConstraint.Type.Integer,mutable=true,optional=false)
    private int credits;
    
    /**
     * @effects <pre>
     *     if n, s, r are valid
     *        initialize this as Module:<c,n,s,r>
     *     else
     *        throw NotPossibleException
     *      </pre>
     * 
     */
    public Module(String n, int s, int r) throws NotPossibleException
    {
        if(validate(n, s, r))
        {
            if (!MAP_CODE.containsKey(s)) 
            {
		MAP_CODE.put(s, 1);
		this.code = "M" + (s * 100 + 1);
		} 
            else 
            {
                int index = MAP_CODE.get(s);
		MAP_CODE.replace(s, index, index + 1);
		this.code = "M" + (s * 100 + index + 1);
            }
            this.name = n;
            this.semester = s;
            this.credits = r;
        }
        else
        {
           throw new NotPossibleException("Module<init>: Invalid arguments.");
        }
    }
    
    /**
     * @effects return <tt>this.code</tt>
     */
    public String getCode()
    {
        return this.code;
    }
    
    /**
     * @effects return <tt>this.name</tt>
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * @effects return <tt>this.credits</tt>
     */
    public int getCredits()
    {
        return this.credits;
    }
    
    /**
     * @effects return <tt>this.semester</tt>
     */
    public int getSemester()
    {
        return this.semester;
    }
    
    /**
     * @effects <pre>
     *      if n is valid
     *          set this.name = name
     *      else
     *          throws NotPossibleException</pre>
     * @param n 
     */
    public void setName(String n) throws NotPossibleException
    {
        if(validateName(n))
            this.name = n;
        else
            throw new NotPossibleException("Module.setName: invalid name: " + n);
    }
    
    /**
     * @effects <pre>
     *      if s is valid
     *          set this.semester = s
     *      else
     *          throws NotPossibleException</pre>
     * @param s 
     */
    public void setSemester(int s) throws NotPossibleException
    {
        if(validateSemester(s))
            this.semester = s;
        else
            throw new NotPossibleException("Module.setSemester: invalid semester: " + s);
    }
    
    /**
     * @effects <pre>
     *      if r is valid
     *          set this.credits = r
     *      else
     *          throws NotPossibleException</pre>
     * @param r 
     */
    public void setCredits(int r) throws NotPossibleException
    {
        if(validateCredits(r))
            this.credits = r;
        else
            throw new NotPossibleException("Module.setCredits: invalid credits: " + r);
    }
    
    @Override
    public String toString()
    {
        return "Created Module(" + this.code + "," + this.name + 
                "," + this.semester + "," + this.credits + ")"; 
    }
    
    public boolean repOK()
    {
        return validate(name, semester, credits);
    }
    
    /**
     * @effects <pre>
     *      if <n, d, a, e> is a valid tuple
     *          return true
     *      else
     *          return false</pre> 
     */
    private boolean validate(String n, int s, int r)
    {
        return validateName(n) && validateSemester(s) &&
               validateCredits(r);
    }
    
    /**
     * @effects <pre>
     *      if n is a valid name
     *          return true
     *      else
     *          return false
     */
    protected boolean validateName(String n)
    {
        if (n.isEmpty() || n.length() == 0)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if s is a valid semester
     *          return true
     *      else
     *          return false
     */
    protected boolean validateSemester(int s)
    {
        if (s <= 0)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if r is a valid credits
     *          return true
     *      else
     *          return false
     */
    protected boolean validateCredits(int r)
    {
        if (r <= 0)
            return false;
        else
            return true;
    }
}
