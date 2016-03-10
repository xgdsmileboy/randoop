package randoop.sequence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

import randoop.ComponentManager;
import randoop.DummyVisitor;
import randoop.RandoopListenerManager;
import randoop.SeedSequences;
import randoop.main.GenInputsAbstract;
import randoop.main.GenInputsAbstract.BehaviorType;
import randoop.main.GenTests;
import randoop.operation.ConstructorCall;
import randoop.operation.Operation;
import randoop.reflection.DefaultReflectionPredicate;
import randoop.reflection.OperationExtractor;
import randoop.reflection.PublicVisibilityPredicate;
import randoop.reflection.ReflectionPredicate;
import randoop.reflection.VisibilityPredicate;
import randoop.test.TestCheckGenerator;
import randoop.util.predicate.Predicate;

public class TestFilteringTest {

  /**
   * Make sure that we are getting both regression and error tests with
   * default filtering settings.
   */
  @Test
  public void nonemptyOutputTest() {
    GenInputsAbstract.dont_output_tests = false;
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_error_revealing_tests = false;
    GenInputsAbstract.no_regression_tests = false;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
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
    assertTrue("should have some error tests", eTests.size() > 0);
  }

  /**
   * Make sure there is no output when dont-output-tests is set.
   * Need to set an input limit here.
   */
  @Test
  public void noOutputTest() {
    GenInputsAbstract.dont_output_tests = true;
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_error_revealing_tests = false;
    GenInputsAbstract.no_regression_tests = false;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.inputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have no regression tests", rTests.size() == 0);
    assertTrue("should have no error tests", eTests.size() == 0);
  }

  /**
   * Make sure get no error test output when no-error-revealing-tests is set.
   */
  @Test
  public void noErrorOutputTest() {
    GenInputsAbstract.dont_output_tests = false;
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_error_revealing_tests = true;
    GenInputsAbstract.no_regression_tests = false;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
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
    assertTrue("should have no error tests", eTests.size() == 0);
  }

  /**
   * Make sure that no regression tests are output when no-regression-tests is set.
   * Better to set inputlimit here since most tests are regression tests.
   */
  @Test
  public void noRegressionOutputTest() {
    GenInputsAbstract.dont_output_tests = false;
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_error_revealing_tests = false;
    GenInputsAbstract.no_regression_tests = true;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.inputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have no regression tests", rTests.size() == 0);
    assertTrue("should have some error tests", eTests.size() > 0);
  }

  /**
   * Having both Error and Regression tests turned off should give nothing.
   * Set inputlimit
   */
  @Test
  public void noErrorOrRegressionOutputTest() {
    GenInputsAbstract.dont_output_tests = false;
    GenInputsAbstract.include_if_classname_appears = null;
    GenInputsAbstract.no_error_revealing_tests = true;
    GenInputsAbstract.no_regression_tests = true;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.inputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have no regression tests", rTests.size() == 0);
    assertTrue("should have no error tests", eTests.size() == 0);
  }

  /**
   * Filtering tests matching CUT should produce output tests.
   */
  @Test
  public void matchOutputTest() {
    GenInputsAbstract.dont_output_tests = false;
    GenInputsAbstract.include_if_classname_appears = Pattern.compile("randoop\\.sequence\\.Flaky");
    GenInputsAbstract.no_error_revealing_tests = false;
    GenInputsAbstract.no_regression_tests = false;
    // arguments below ensure we get both kinds of tests
    GenInputsAbstract.no_regression_assertions = false;
    GenInputsAbstract.checked_exception = BehaviorType.EXPECTED;
    GenInputsAbstract.unchecked_exception = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_null_input = BehaviorType.ERROR;
    GenInputsAbstract.npe_on_non_null_input = BehaviorType.ERROR;
    GenInputsAbstract.oom_exception = BehaviorType.INVALID;
    GenInputsAbstract.outputlimit = 1000;
    GenInputsAbstract.inputlimit = 1000;
    GenInputsAbstract.forbid_null = false;

    Class<?> c = Flaky.class;
    ForwardGenerator gen = buildGenerator(c);
    gen.explore();
    List<ExecutableSequence> rTests = gen.getRegressionSequences();
    List<ExecutableSequence> eTests = gen.getErrorTestSequences();

    assertTrue("should have some regression tests", rTests.size() > 0);
    assertTrue("should have some error tests", eTests.size() > 0);
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
    ForwardGenerator testGenerator = new ForwardGenerator(
        model,
        GenInputsAbstract.timelimit * 1000,
        GenInputsAbstract.inputlimit,
        GenInputsAbstract.outputlimit,
        componentMgr,
        null,
        listenerMgr);
    GenTests genTests = new GenTests();
    ConstructorCall objectConstructor = null;
    try {
      objectConstructor = ConstructorCall.createConstructorCall(Object.class.getConstructor());
      if (!model.contains(objectConstructor))
        model.add(objectConstructor);
    } catch (Exception e) {
      fail("couldn't get object constructor");
    }
    Predicate<ExecutableSequence> isOutputTest = genTests.createTestOutputPredicate(objectConstructor, new HashSet<Class<?>>());
    testGenerator.addTestPredicate(isOutputTest);
    TestCheckGenerator checkGenerator = genTests.createTestCheckGenerator(visibility, classes);
    testGenerator.addTestCheckGenerator(checkGenerator);
    testGenerator.addExecutionVisitor(new DummyVisitor());
    return testGenerator;
  }
}
