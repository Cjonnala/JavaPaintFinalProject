package controller.drawing;

import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;

import java.awt.*;

public class ShapeCreating implements IEventCallback,IUndoable {
    private final ShapeType shapeType;
    ApplicationState appState;
    private final Coordinate startCoordinate;
    private final Coordinate endCoordinate;
    private final ListForShapes listForShapes;
    Color pColor;
    Color sColor;
    private final ShapeShadingType shapeShadingType;
    public ShapeFrame shapeFrame;
    public ShapeCreating(ApplicationState appState, Coordinate startCoordinate, Coordinate endCoordinate, Color pColor,Color sColor, ListForShapes listForShapes, ShapeShadingType shapeShadingType, ShapeType shapeType){
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.listForShapes = listForShapes;
        this.appState = appState;
        this.pColor = pColor;
        this.sColor = sColor;
        this.shapeShadingType = shapeShadingType;
        this.shapeType = shapeType;
    }
    public void run() {
        Shape shape = new Shape(startCoordinate, endCoordinate,appState,pColor,sColor,shapeShadingType,shapeType);
        ShapeDesign shapeDesign = new ShapeDesign();
        shapeFrame = shapeDesign.makeShape(shape);
        listForShapes.addShape(shapeFrame);
        CommandHistory.add(this);
    }
    @Override
    public void undo() {
        listForShapes.removeShape();
    }
    @Override
    public void redo() {
        listForShapes.redoShape();
    }
}
