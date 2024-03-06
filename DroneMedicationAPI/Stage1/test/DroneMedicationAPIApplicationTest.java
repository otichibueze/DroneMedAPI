import DroneMed.DroneMedicationAPIApplication;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.List;


class Attach {
    int input;
    String response;
    Attach(int input, String response) {
        this.response = response;
        this.input = input;
    }
}

//@SpringBootTest(classes = DroneMedicationAPIApplication.class)
public class DroneMedicationAPIApplicationTest  extends StageTest<Attach> {


    public DroneMedicationAPIApplicationTest() {
        super(DroneMedicationAPIApplication.class);
    }

    /*
    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
                new TestCase<Attach>().setAttach(
                        new Attach(1, "hello")),

                new TestCase<Attach>().setAttach(
                        new Attach(2, "hi"))
        );
    }


    public TestCase<Attach> starting = new TestCase().setAttach(new Attach(1,"hello")).setFeedback("Done");

    @Override
    public CheckResult check(String reply, Attach attach) {
        if (!reply.contains(Integer.toString(attach.input))) {
            return CheckResult.wrong(attach.response);
        }
        return CheckResult.correct();
    }

*
     */


   @DynamicTest
    CheckResult test() {
       TestedProgram pr = new TestedProgram();
       String output = pr.start();
       output = pr.execute("1");
       if(output.equals("Enter instruction for drone medication API...")) {
           return CheckResult.correct();
       }

       return CheckResult.wrong("Your initial output should be " + output);
   }




//    @Override
//    public List<TestCase<Attach>> generate() {
//        return List.of(
//                new TestCase().setAttach(new Attach(1,"1. Drones\n" +
//                        "            2. Medications\n" +
//                        "            3. UserAccounts\n" +
//                        "            4. Exit"))
//        );
//    }
//
//
//    @Override
//    public CheckResult check(String reply, Attach attach) {
//       if(reply.equals(attach.response)){
//           return CheckResult.correct();
//       }
//
//       return CheckResult.wrong("You should output");
//    }

}