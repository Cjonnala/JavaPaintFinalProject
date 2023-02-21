package controller.drawing;

import controller.factorymethod.*;
import model.interfaces.ShapeFrame;

public class ShapeDesign {
    public ShapeFrame makeShape(Shape shape) {
        ShapeFactory factory = switch (shape.shapeType) {
            case RECTANGLE -> new RectShapeFactory();
            case ELLIPSE -> new EllipseShapeFactory();
            case TRIANGLE -> new TriangleShapeFactory();
           
        };
        return factory.createShapeFrame(shape);
    }

}



