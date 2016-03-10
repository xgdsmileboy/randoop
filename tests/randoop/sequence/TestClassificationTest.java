package randoop.sequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import randoop.Check;
import randoop.ComponentManager;
import randoop.DummyVisitor;
import randoop.EmptyExceptionCheck;
import randoop.ExceptionCheck;
import randoop.ExpectedExceptionCheck;
import randoop.NoExceptionCheck;
import randoop.RandoopListenerManager;
import randoop.SeedSequences;
import randoop.main.GenInputsAbstract;
import randoop.main.GenInputsAbstract.BehaviorType;
import randoop.main.GenTests;
import randoop.operation.Operation;
import randoop.reflection.DefaultReflectionPredicate;
import randoop.reflection.OperationExtractor;
import randoop.reflection.PublicVisibilityPredicate;
import randoop.reflection.ReflectionPredicate;
import randoop.reflection.VisibilityPredicate;
import randoop.test.TestCheckGenerator;
import randoop.test.TestChecks;
import randoop.util.predicate.AlwaysTrue;
import randoop.util.predicate.Predicate;

/**
 * Tests the classification of tests based on exception behavior assignments.
 * So, question is where exceptions are placed.
 */
public class TestClassificationTest {

  /**
   * Tests the classification of tests when all exceptions are invalid.
   * Because of class will have no error tests, and regression tests
   * should have no exceptions.
   */
  @Test
  public void allInvalidTest() {
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.INVALID;
    GenInputsAbstract.unchecked_exception = BehaviorType.INVALID;
    GenInputsAbstract.npe_on_null_input = BehaviorType.INVALID;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.INVALID;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);

    for (ExecutableSequence s : rTests) {
      TestChecks cks = s.getChecks();
      assertTrue("if sequence here should have checks", cks.hasChecks());
      assertFalse("these are not error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      ExceptionCheck eck = cks.getExceptionCheck();
      if (eck != null) {
        String msg = "all exceptions are invalid, regression checks should be null;\n have ";
        fail(msg + eck.getClass().getName() + " with " + eck.getExceptionName());
      }
    }

    assertEquals("when all exceptions invalid, have no error tests", 0, eTests.size());

  }

  /**
   * Tests the classification of tests when all exceptions are errors.
   * All exceptions should show as NoExceptionCheck, and should be no
   * expected exceptions in regression tests.
   */
  @Test
  public void allErrorTest() {
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.ERROR;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.ERROR;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);

    for (ExecutableSequence s : rTests) {
      TestChecks cks = s.getChecks();
      assertFalse("these are not error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      ExceptionCheck eck = cks.getExceptionCheck();
      if (eck != null) {
        String msg = "all exceptions error, should have no expected;\n have ";
        fail(msg + eck.getClass().getName() + " with " + eck.getExceptionName());
      }
    }

    assertTrue("should have some error tests", eTests.size() > 0);

    for (ExecutableSequence s : eTests) {
      TestChecks cks = s.getChecks();
      assertTrue("if sequence here should have checks", cks.hasChecks());
      assertTrue("these are error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      int exceptionCount = 0;
      for (Check ck : cks.get().keySet()) {
        if (ck instanceof NoExceptionCheck) {
          exceptionCount++;
        }
      }
      assertTrue("exception count should be one, have " + exceptionCount, exceptionCount == 1);

    }

  }

  /**
   * Tests classification of tests when all exceptions are expected.
   * All exceptions should show as expected exception checks, and
   * there should be no error tests.
   */
  @Test
  public void allExpectedTest() {
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_null_input = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.EXPECTED;
    GenInputsAbstract.oom_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);

    for (ExecutableSequence s : rTests) {
      TestChecks cks = s.getChecks();
      assertFalse("these are not error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      ExceptionCheck eck = cks.getExceptionCheck();
      if (eck != null) {
        assertTrue("if there is an exception check, should be checks", cks.hasChecks());
        assertTrue("should be expected exception, was" + eck.getClass().getName(), eck instanceof ExpectedExceptionCheck);
      }
    }

    assertEquals("all exceptions expected, should be no error tests", 0, eTests.size());

  }

  /**
   * Tests classification of tests when behavior type defaults are set
   * (checked and unchecked exceptions are expected, and both NPE-on-null
   * and OOM are invalid).
   * Because class throws NPE without input, should see NPE as expected when no
   * null inputs. Otherwise, should not see NPE.
   */
  @Test
  public void defaultsTest() {
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_null_input = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);

    for (ExecutableSequence s : rTests) {
      TestChecks cks = s.getChecks();
      assertFalse("these are not error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      ExceptionCheck eck = cks.getExceptionCheck();
      if (eck != null) {
        assertTrue("if there is an exception check, should be checks", cks.hasChecks());
        assertTrue("should be expected exception, was" + eck.getClass().getName(), eck instanceof ExpectedExceptionCheck);
      }
    }

    assertTrue("should have error tests", eTests.size() > 0);

    for (ExecutableSequence s : eTests) {
      TestChecks cks = s.getChecks();
      assertTrue("if sequence here should have checks", cks.hasChecks());
      assertTrue("these are error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      int exceptionCount = 0;
      for (Check ck : cks.get().keySet()) {
        if (ck instanceof NoExceptionCheck) {
          exceptionCount++;
        }
      }
      assertTrue("exception count should be one, have " + exceptionCount, exceptionCount == 1);

    }
  }

  /**
   * Tests default behaviors with regression assertions turned off.
   * Means that because class throws NPE without input, should see NPE as
   * empty exception when there are no null inputs.
   * Otherwise, should not see NPE, or any other checks.
   */
  @Test
  public void defaultsWithNoRegressionAssertions() {
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_regression_assertions = true;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_null_input = BehaviorType.EXPECTED;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);

    for (ExecutableSequence s : rTests) {
      TestChecks cks = s.getChecks();
      assertFalse("these are not error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      ExceptionCheck eck = cks.getExceptionCheck();
      if (eck != null) {
        assertTrue("if there is an exception check, should be checks", cks.hasChecks());
        assertTrue("should be expected exception, was" + eck.getClass().getName(), eck instanceof EmptyExceptionCheck);
      } else {
        assertFalse("if there is no exception check, should be no checks", cks.hasChecks());
      }
    }

    assertTrue("should have error tests", eTests.size() > 0);

    for (ExecutableSequence s : eTests) {
      TestChecks cks = s.getChecks();
      assertTrue("if sequence here should have checks", cks.hasChecks());
      assertTrue("these are error checks", cks.hasErrorBehavior());
      assertFalse("these are not invalid checks", cks.hasInvalidBehavior());

      int exceptionCount = 0;
      for (Check ck : cks.get().keySet()) {
        if (ck instanceof NoExceptionCheck) {
          exceptionCount++;
        }
      }
      assertTrue("exception count should be one, have " + exceptionCount, exceptionCount == 1);

    }
  }

  private ForwardGenerator buildGenerator(Class<?> c) {
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(c);
    Set<String> omitfields = new HashSet<>();
    VisibilityPredicate visibility = new PublicVisibilityPredicate();
    ReflectionPredicate predicate = new DefaultReflectionPredicate(GenInputsAbstract.omitmethods, omitfields, visibility);
    List<Operation> model = OperationExtractor.getOperations(classes, predicate);
    Collection<Sequence> components = new LinkedHashSet<Sequence>();
    components.addAll(SeedSequences.objectsToSeeds(SeedSequences.primitiveSeeds));
    ComponentManager componentMgr = new ComponentManager(components );
    RandoopListenerManager listenerMgr = new RandoopListenerManager();
    ForwardGenerator gen = new ForwardGenerator(
        model,
        GenInputsAbstract.timelimit * 1000,
        GenInputsAbstract.inputlimit,
        GenInputsAbstract.outputlimit,
        componentMgr,
        null,
        listenerMgr);
    Predicate<ExecutableSequence> isOutputTest = new AlwaysTrue<>();
    gen.addTestPredicate(isOutputTest);
    TestCheckGenerator checkGenerator = (new GenTests()).createTestCheckGenerator(visibility, classes);
    gen.addTestCheckGenerator(checkGenerator);
    gen.addExecutionVisitor(new DummyVisitor());
    return gen;
  }

}
