package controller.drawing;

import model.ShapeType;
import model.interfaces.ShapeFrame;

public class ShapeDesign {
    public ShapeFrame makeShape(Shape shape) {
        ShapeFrame shapeFrame;
        if (shape.shapeType== ShapeType.RECTANGLE){
            shapeFrame = new RectShape(shape);
        }
        else{
            shapeFrame = new RectShape(shape);
        }
        return shapeFrame;
    }
}
