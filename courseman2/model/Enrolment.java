package courseman2.model;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import java.io.Serializable;
/**
 * @overview Enrolment records a fact that a student has registered interest 
 *           to study a specific module in a given semester
 * @attributes
 *  student         Student
 *  module          Module
 *  internMark      Float       float
 *  examMark        Float       float
 *  finalGrade      Character   char
 * @object  A typical enrolment is e=<s,m>, where student(s), module(m)
 * @attributes
 *  mutable(student) = false /\ optional(student) = false /\
 *  mutable(module) = false /\ optional(module) = false /\
 *  mutable(internMark) = true /\ optional(internMark) = true /\
 *  mutable(examMark) = true /\ optional(examMark) = true /\
 *  mutable(finalGrade) = false /\ optional(finalGrade) = false
 */
public class Enrolment implements Serializable, Comparable<Enrolment>
{
    @DomainConstraint(type = DomainConstraint.Type.UserDefined,mutable=false,optional=false)
    private Student student;
    @DomainConstraint(type = DomainConstraint.Type.UserDefined,mutable=false,optional=false)
    private Module module;
    @DomainConstraint(type = DomainConstraint.Type.Float,mutable=true,optional=false)
    private float internMark;
    @DomainConstraint(type = DomainConstraint.Type.Float,mutable=true,optional=false)
    private float examMark;
    @DomainConstraint(type = DomainConstraint.Type.Char,mutable=false,optional=true)
    private char finalGrade;
    
    /**
     * @effects <pre>
     *     if i, e are valid
     *        initialize this as Enrolment:<s,m>
     *     else
     *        throw NotPossibleException
     *      </pre>
     * 
     */
    
    public Enrolment(Student s, Module m, Float i, Float e)
    { 
        this.student = s;
        this.module = m;
        this.internMark = i;
        this.examMark = e;
    }
    
    /**
     * @effects return <tt>this.student</tt>
     */
    public Student getStudent()
    {
        return this.student;
    }
    
    /**
     * @effects return <tt>this.module</tt>
     */
    public Module getModule()
    {
        return this.module;
    }
    
    /**
     * @effects return <tt>this.internMark</tt>
     */
    public float getInternalMark() {
        return internMark;
    }

    /**
     * @effects return <tt>this.examMark</tt>
     */
    public float getExamMark() {
        return examMark;
    }
    
    /**
     * @effects return <tt>this.finalGrade</tt>
     */
    public char getFinalGrade() {
       float a = getAggreatedMark();
       if(a < 5f)
           finalGrade = 'F';
       else if(a>=5f && a < 7f)
           finalGrade = 'P';
       else if(a>=7f && a < 9f)
           finalGrade = 'G';
       else
           finalGrade = 'E';
       return finalGrade;
    }
    
    /**
     * @effects calculate the aggregate mark to decide the final grade
     */
    public float getAggreatedMark()
    {
        return internMark * 0.4f + examMark * 0.6f;
    }
    
    /**
     * @effects <pre>
     *      if i is valid
     *          set this.internMark = i
     *      else
     *          throws NotPossibleException</pre>
     * @param i 
     */
    public void setInternalMark(float i) throws NotPossibleException 
    {
        if(validateInternalMark(i))
            this.internMark = i;
        else
            throw new NotPossibleException("Enrolment.setInternalMark: "
                    + "invalid internal mark: " + i);
    }

    /**
     * @effects <pre>
     *      if e is valid
     *          set this.examMark = e
     *      else
     *          throws NotPossibleException</pre>
     * @param e 
     */
    public void setExamMark(float e) throws NotPossibleException
    {
        if(validateExamMark(e))
            this.examMark = e;
        else
            throw new NotPossibleException("Enrolment.setExamMark: "
                    + "invalid exam mark: " + e);
    }
    
    @Override
    public String toString()
    {
        return "Created Enrolment(" + this.getStudent().getId() + "," + this.getModule().getName()
                +"," + this.getInternalMark() + "," + this.getExamMark()+ ")";
    }
    
    public boolean repOK()
    {
        return validate(internMark, examMark);
    }
    
    /**
     * @effects <pre>
     *      if <i, e> is a valid tuple
     *          return true
     *      else
     *          return false</pre> 
     */
    
    private boolean validate(float i, float e)
    {
        return validateInternalMark(i) && validateExamMark(e);
    }
    
    /**
     * @effects <pre>
     *      if i is a valid internal mark
     *          return true
     *      else
     *          return false
     */
    private boolean validateInternalMark(float i)
    {
        if(i < 0f || i > 10f)
            return false;
        else
            return true;
    }
    
    /**
     * @effects <pre>
     *      if e is a valid exam mark
     *          return true
     *      else
     *          return false
     */
    private boolean validateExamMark(float e)
    {
        if(e < 0f || e > 10f)
            return false;
        else
            return true;
    }

    @Override
    public int compareTo(Enrolment e) 
    {
        return compareByName(e);
    }
    
    protected int compareByName(Enrolment e)
    {
        return this.student.getName().compareTo(e.student.getName());
    }
}
