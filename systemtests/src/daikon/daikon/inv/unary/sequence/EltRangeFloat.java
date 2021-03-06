// ***** This file is automatically generated from Range.java.jpp

package daikon.inv.unary.sequence;

import daikon.*;
import daikon.inv.*;
import daikon.inv.unary.sequence.*;
import daikon.inv.binary.sequenceScalar.*;
import daikon.derive.unary.*;
import daikon.Quantify.QuantFlags;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import utilMDE.*;

/**
 * Baseclass for unary range based invariants.  Each invariant is a
 * special stateless version of bound or oneof.  For example
 * EqualZero, BooleanVal, etc). These are never printed, but are used
 * internally as suppressors for ni-suppressions.
 *
 * Each specific invariant is implemented in a subclass (typically in
 * this file).
 */

public abstract class EltRangeFloat extends SingleFloatSequence {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20040311L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff range invariants should be considered.
   **/
  public static boolean dkconfig_enabled = true;

  protected EltRangeFloat (PptSlice ppt) {
    super (ppt);
  }

  /** returns whether or not this invariant is enabled **/
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /**
   * Check that instantiation is ok.  The type must be integral
   * (not boolean or hash code)
   */
  public boolean instantiate_ok (VarInfo[] vis) {

    if (!valid_types (vis))
      return (false);

    if (!vis[0].file_rep_type.baseIsFloat())
      return (false);

    return (true);
  }

  /**
   * Returns a string in the specified format that describes the invariant.
   *
   * The generic format string is obtained from the subclass specific
   * get_format_str().  Instances of %var1% are replaced by the variable
   * name in the specified format.  If the format is IOA, == is replaced
   * by =.
   */
  public String format_using(OutputFormat format) {

    String fmt_str = get_format_str (format);

    VarInfo var1 = ppt.var_infos[0];
    String v1 = var1.name_using(format);

      if (format == OutputFormat.ESCJAVA) {
        String[] form = VarInfo.esc_quantify (var1);
        fmt_str = form[0] + "(" + fmt_str + ")" + form[2];
        v1 = form[1];
      } else if (format == OutputFormat.SIMPLIFY) {
        String[] form = VarInfo.simplify_quantify (QuantFlags.element_wise(),
                                                   var1);
        fmt_str = form[0] + " " + fmt_str + " " + form[2];
        v1 = form[1];
      } else if (format == OutputFormat.IOA) {
        fmt_str += " ***";
      } else if (format == OutputFormat.DAIKON) {
        fmt_str += " (elementwise)";
      }

    fmt_str = UtilMDE.replaceString(fmt_str, "%var1%", v1);
    if (format == OutputFormat.IOA)
      fmt_str = fmt_str.replaceAll ("==", "="); // "interned"
    return (fmt_str);
  }

  public InvariantStatus check_modified (double[] x, int count) {
    for (int i = 0; i < x.length; i++) {
      if (!eq_check (x[i]))
        return InvariantStatus.FALSIFIED;
    }
    return InvariantStatus.NO_CHANGE;
  }

  public InvariantStatus add_modified (double[] x, int count) {
    return check_modified (x, count);
  }

  protected double computeConfidence() {
    return CONFIDENCE_JUSTIFIED;
  }

  public boolean isSameFormula (Invariant other) {
    Assert.assertTrue (other.getClass() == getClass());
    return (true);
  }
  public boolean isExclusiveFormula(Invariant other) {
    return false;
  }

  /**
   * All range invariants are obvious since they are all represented
   * by some version of OneOf or Bound.
   */
  public DiscardInfo isObviousDynamically (VarInfo[] vis) {

    return new DiscardInfo (this, DiscardCode.obvious,
                            "Implied by Oneof or Bound");
  }

  /**
   * Return a format string for the specified output format.  Each instance
   * of %varN% will be replaced by the correct name for varN.
   */
  public abstract String get_format_str (OutputFormat format);

  /**
   * Returns true if x and y don't invalidate the invariant.
   */
  public abstract boolean eq_check (double x);

  /**
   * Returns a list of all of prototypes of all of the range
   * invariants
   */
  public static List<Invariant> get_proto_all () {

    List<Invariant> result = new ArrayList<Invariant>();
    result.add (EqualZero.get_proto());
    result.add (EqualOne.get_proto());
    result.add (EqualMinusOne.get_proto());
    result.add (GreaterEqualZero.get_proto());
    result.add (GreaterEqual64.get_proto());

    return (result);
  }

  /**
   * Internal invariant representing double scalars that are equal
   * to zero.  Used for non-instantiating suppressions.  Will never
   * print since OneOf accomplishes the same thing.
   */
  public static class EqualZero extends EltRangeFloat {

    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected EqualZero (PptSlice ppt) {
      super (ppt);
    }

    private static EqualZero proto;

    /** returns the prototype invariant **/
    public static Invariant get_proto() {
      if (proto == null)
        proto = new EqualZero (null);
      return proto;
    }

    /** instantiates the invariant on the specified slice **/
    public Invariant instantiate_dyn (PptSlice slice) {
      return new EqualZero (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ 0 %var1%)");
      else
        return ("%var1% == 0");
    }

    public boolean eq_check (double x) {
      return (x == 0);
    }
  }

  /**
   * Internal invariant representing double scalars that are equal
   * to one.  Used for non-instantiating suppressions.  Will never
   * print since OneOf accomplishes the same thing
   */
  public static class EqualOne extends EltRangeFloat {

    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected EqualOne (PptSlice ppt) {
      super (ppt);
    }

    private static EqualOne proto;

    /** returns the prototype invariant **/
    public static Invariant get_proto() {
      if (proto == null)
        proto = new EqualOne (null);
      return proto;
    }

    /** instantiates the invariant on the specified slice **/
    public Invariant instantiate_dyn (PptSlice slice) {
      return new EqualOne (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ 1 %var1%)");
      else
        return ("%var1% == 1");
    }

    public boolean eq_check (double x) {
      return (x == 1);
    }
  }

  /**
   * Internal invariant representing double scalars that are equal
   * to minus one.  Used for non-instantiating suppressions.  Will never
   * print since OneOf accomplishes the same thing
   */
  public static class EqualMinusOne extends EltRangeFloat {

    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040824L;

    protected EqualMinusOne (PptSlice ppt) {
      super (ppt);
    }

    private static EqualMinusOne proto;

    /** returns the prototype invariant **/
    public static Invariant get_proto() {
      if (proto == null)
        proto = new EqualMinusOne (null);
      return proto;
    }

    /** instantiates the invariant on the specified slice **/
    public Invariant instantiate_dyn (PptSlice slice) {
      return new EqualMinusOne (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ -1 %var1%)");
      else
        return ("%var1% == -1");
    }

    public boolean eq_check (double x) {
      return (x == -1);
    }
  }

  /**
   * Internal invariant representing double scalars that are greater
   * than or equal to 0.  Used for non-instantiating suppressions.  Will never
   * print since Bound accomplishes the same thing
   */
  public static class GreaterEqualZero extends EltRangeFloat {

    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected GreaterEqualZero (PptSlice ppt) {
      super (ppt);
    }

    private static GreaterEqualZero proto;

    /** returns the prototype invariant **/
    public static Invariant get_proto() {
      if (proto == null)
        proto = new GreaterEqualZero (null);
      return proto;
    }

    /** instantiates the invariant on the specified slice **/
    public Invariant instantiate_dyn (PptSlice slice) {
      return new GreaterEqualZero (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(>= %var1% 0)");
      else
        return ("%var1% >= 0");
    }

    public boolean eq_check (double x) {
      return (x >= 0);
    }
  }

  /**
   * Internal invariant representing double scalars that are greater
   * than or equal to 64.  Used for non-instantiating suppressions.  Will never
   * print since Bound accomplishes the same thing
   */
  public static class GreaterEqual64 extends EltRangeFloat {

    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected GreaterEqual64 (PptSlice ppt) {
      super (ppt);
    }

    private static GreaterEqual64 proto;

    /** returns the prototype invariant **/
    public static Invariant get_proto() {
      if (proto == null)
        proto = new GreaterEqual64 (null);
      return proto;
    }

    /** instantiates the invariant on the specified slice **/
    public Invariant instantiate_dyn (PptSlice slice) {
      return new GreaterEqual64 (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(>= 64 %var1%)");
      else
        return ("%var1% >= 64");
    }

    public boolean eq_check (double x) {
      return (x >= 64);
    }
  }

}
