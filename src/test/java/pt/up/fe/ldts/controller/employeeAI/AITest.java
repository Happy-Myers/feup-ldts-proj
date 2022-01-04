package pt.up.fe.ldts.controller.employeeAI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.model.Employee;
import pt.up.fe.ldts.model.Jorge;
import pt.up.fe.ldts.model.Point;
import pt.up.fe.ldts.model.Vector;

public class AITest {

    Point pos;

    @BeforeEach
    public void setup(){
        pos = new Point(5,6);
        Jorge.singleton.setDirection(Vector.UP);
        Jorge.singleton.changePos(4, 5);
    }

    @Test
    public void toniTest(){
        EmployeeAI toni = new ToniAI();

        Assertions.assertEquals(ToniAI.SCATTER_TARGET, toni.chooseTargetPosition(Employee.EmployeeState.SCATTER, pos));

        Assertions.assertEquals(ToniAI.SCATTER_TARGET, toni.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

        pos = pos.addVector(new Vector(10,10));

        Assertions.assertEquals(Jorge.singleton.getPosition(), toni.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

        Assertions.assertEquals(ToniAI.DEAD_TARGET, toni.chooseTargetPosition(Employee.EmployeeState.DEAD, pos));

        //falta testar frightened
    }

    @Test
    public void baltaTest(){
        EmployeeAI baltazar = new BaltaAI();

        Assertions.assertEquals(BaltaAI.SCATTER_TARGET, baltazar.chooseTargetPosition(Employee.EmployeeState.SCATTER, pos));

        Assertions.assertEquals(Jorge.singleton.getPosition(), baltazar.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

        Assertions.assertEquals(BaltaAI.DEAD_TARGET, baltazar.chooseTargetPosition(Employee.EmployeeState.DEAD, pos));

    }

    @Test
    public void mariTest(){
        EmployeeAI mariana = new MariAI();
        Point target = Jorge.singleton.getPosition().addVector(new Vector(-4,-4));

        Assertions.assertEquals(MariAI.SCATTER_TARGET, mariana.chooseTargetPosition(Employee.EmployeeState.SCATTER, pos));

        Assertions.assertEquals(target, mariana.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

        Jorge.singleton.setDirection(Vector.LEFT);

        target = Jorge.singleton.getPosition().addVector(Vector.LEFT.multiply(4));

        Assertions.assertEquals(target, mariana.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

        Assertions.assertEquals(MariAI.DEAD_TARGET, mariana.chooseTargetPosition(Employee.EmployeeState.DEAD, pos));

    }

    @Test
    public void zeCastroTest(){

        Employee balta = new Employee(10,10,null);

        EmployeeAI zeCastroAI = new ZeCastroAI(balta);

        Point target;

        {
            Assertions.assertEquals(Vector.UP, Jorge.singleton.getDirection());

            target = Jorge.singleton.getPosition().addVector(new Vector(-2, -2));

            target = target.addVector(Vector.from(balta.getPosition(), target));

            Assertions.assertEquals(ZeCastroAI.SCATTER_TARGET, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.SCATTER, pos));

            Assertions.assertEquals(target, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));
        }

        Jorge.singleton.setDirection(Vector.LEFT);

        {
            target = Jorge.singleton.getPosition().addVector(Vector.LEFT.multiply(2));

            target = target.addVector(Vector.from(balta.getPosition(), target));

            Assertions.assertEquals(target, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

            Assertions.assertEquals(ZeCastroAI.DEAD_TARGET, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.DEAD, pos));
        }

        Jorge.singleton.setDirection(Vector.UP);
        balta.changePos(4, 3);

        {
            target = Jorge.singleton.getPosition().addVector(new Vector(-2, -2));

            target = target.addVector(Vector.from(balta.getPosition(), target));

            Assertions.assertEquals(target, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.CHASING, pos));

            Assertions.assertEquals(ZeCastroAI.DEAD_TARGET, zeCastroAI.chooseTargetPosition(Employee.EmployeeState.DEAD, pos));
        }
    }
}