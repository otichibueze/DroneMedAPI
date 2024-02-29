import DroneMed.DroneMedicationAPIApplication;
import org.hibernate.criterion.AggregateProjection;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.SimpleTestCase;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;

class Attach {
    int input;
    String response;
    Attach(int input, String response) {
        this.response = response;
        this.input = input;
    }
}

public class DroneMedicationTest  extends StageTest<Attach> {


    public DroneMedicationTest() {
        //super(DroneMedicationAPIApplication.class, 28852, "../droneMed_db.mv.db");
        super(DroneMedicationAPIApplication.class);
    }


    //TestCase CheckCorrect = new TestCase().setInput(1)

    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
                new TestCase().setAttach(new Attach(1,"1. Drones\n" +
                        "            2. Medications\n" +
                        "            3. UserAccounts\n" +
                        "            4. Exit"))
        );
    }


    @Override
    public CheckResult check(String reply, Attach attach) {
       if(reply.equals(attach.response)){
           return CheckResult.correct();
       }

       return CheckResult.wrong("You should output");
    }

}