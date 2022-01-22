package pt.up.fe.ldts.controller.employeeAI;

import com.googlecode.lanterna.TextColor;
import pt.up.fe.ldts.model.Point;
import pt.up.fe.ldts.model.Vector;
import pt.up.fe.ldts.model.game.Jorge;
import pt.up.fe.ldts.model.map.MapConfiguration;

public class ToniAI extends EmployeeAI { //clyde

    public ToniAI() {
        SCATTER_TARGET = new Point(0, MapConfiguration.getMapHeight()-1);
    }


    @Override
    public Point chasingTarget(Point position) {
        return Vector.from(Jorge.singleton.getPosition(), position).magnitude() > 8 ? Jorge.singleton.getPosition() : SCATTER_TARGET;
    }

    @Override
    public TextColor getEmployeeColor() {
        return TextColor.Factory.fromString("#e5b362");
    }

}
