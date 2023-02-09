package controller.drawing;

import model.interfaces.ShapeFrame;

public class ShapeDesign {

    ShapeFrame shapeFrame;

    public ShapeFrame makeShape(Shape shape) {

        switch (shape.shapeType) {
            case RECTANGLE -> shapeFrame = new RectShape(shape);
            case ELLIPSE -> shapeFrame = new EllipseShape(shape);
            case TRIANGLE -> shapeFrame = new TriangleShape(shape);
        }
        return shapeFrame;
    }
}



