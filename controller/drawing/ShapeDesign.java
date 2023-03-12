package controller.drawing;

import controller.factorymethod.*;
import model.interfaces.ShapeFrame;

public class ShapeDesign {
    public ShapeFrame makeShape(Shape shape) {
        ShapeFactory factory = switch (shape.shapeType) {
            case RECTANGLE -> new RectShapeFactory();
            case ELLIPSE -> new EllipseShapeFactory();
            case TRIANGLE -> new TriangleShapeFactory();
            case SEMI_CIRCLE -> new SemiCircleShapeFactory();
            case PENTAGON -> new PentagonShapeFactory();
            case RHOMBUS -> new RhombusShapeFactory();
            case DROPLET -> new DropletShapeFactory();
            case SEPTAGON -> new SeptagonShapeFactory();
            case OCTAGON -> new OctagonShapeFactory();
            case TRAPEZOID -> new TrapezoidShapeFactory();
            case SQUARE -> new SquareShapeFactory();


        };
        return factory.createShapeFrame(shape);
    }

}



