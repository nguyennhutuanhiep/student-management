package courseman2.model;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
/**
 * @overview ElectiveModule is a sub-class of Module 
 * @attributes
 *      departmentName  String
 * @object A typical ElectiveModule is e = <c, n, s, r, d>, where code(c), name(n)
 *         semester(s), credits(r), departmentName(d).
 * @abstract_properties
 *      P_Module /\
 *      mutable(departmentName) = true /\ optional(departmentName) = false
 */

public class ElectiveModule extends Module
{
    @DomainConstraint(type = DomainConstraint.Type.String,mutable=true,optional=false)
    private String departmentName;
    
    /**
     * @effects <pre>
     *     if n, s, r, d are valid
     *        initialize this as Module:<c,n,s,r,d>
     *     else
     *        throw NotPossibleException
     *      </pre>
     * 
     */
    public ElectiveModule(String n, int s, int r, String d) throws NotPossibleException
    {
        super(n, s, r);
        if(validate(d))
        {
            this.departmentName = d;
        }
        else
        {
            throw new NotPossibleException("ElectiveModule<init>: Invalid argument");
        }
    }
    
    /**
     * @effects return <tt>this.departmentName</tt>
     */
    public String getDepartmentName()
    {
        return this.departmentName;
    }
    
    /**
     * @effects <pre>
     *      if d is valid
     *          set this.departmentName = d
     *      else
     *          throws NotPossibleException</pre>
     * @param d 
     */
    public void setDepartmentName(String d) throws NotPossibleException
    {
        if(validateDepartmentName(d))
            this.departmentName = d;
        else
            throw new NotPossibleException("ElectiveModule.setDepartmentName: "
                    + "invalid name: " + d);
    }
    
    @Override
    public boolean repOK()
    {
        return super.repOK() && validate(departmentName);
    }
    
    /**
     * @effects
     *      if d is a valid department name
     *          return true
     *      else
     *          return false
     */
    private boolean validate(String d)
    {
        return validateDepartmentName(d);
    }
    
     /**
     * @effects <pre>
     *          if d is valid
     *              return true
     *          else
     *              return false</pre>
     */
    public boolean validateDepartmentName(String d)
    {
        if (d == null || d.length() == 0)
            return false;
        else
            return true;
    }
}
