package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;
import java.util.ArrayList;
public class MoveCommand implements IEventCallback, IUndoable {
    private final ListForShapes listForShapes;
      ApplicationState appState;
    private final int newCoordinateX, newCoordinateY;
    public MoveCommand(ApplicationState appState, Coordinate startCoordinate, Coordinate endCoordinate, ListForShapes listForShapes){
        this.listForShapes = listForShapes;
        this.appState = appState;
        newCoordinateX = endCoordinate.getX()- startCoordinate.getX();
        newCoordinateY = endCoordinate.getY()- startCoordinate.getY();
    }
    @Override
    public void run() {
        if (listForShapes.getListOfSelectedShapes().size()>0){
            ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
            for (int i = 0; i < listOfSelectedShapes.size(); i++) {
                ShapeFrame shapeFrame = listOfSelectedShapes.get(i);
                if (shapeFrame.gettheSize() > 0)
                    shapeFrame.gettheGroup().moveGroupedSubShapes(newCoordinateX, newCoordinateY);
                else shapeFrame.gettheShape().move(newCoordinateX, newCoordinateY);
            }
            listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListOfSelectedShapes());
            CommandHistory.add(this);
        }
    }
    @Override
    public void undo() {
        listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        for (int i = 0; i < listOfSelectedShapes.size(); i++) {
            ShapeFrame shapeFrame = listOfSelectedShapes.get(i);
            if (shapeFrame.gettheSize() > 0) shapeFrame.gettheGroup().undoMovedGroupShapes();
            else shapeFrame.gettheShape().move(-newCoordinateX, -newCoordinateY);
        }
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListOfSelectedShapes());
    }
    @Override
    public void redo() {
        listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        for (int i = 0; i < listOfSelectedShapes.size(); i++) {
            ShapeFrame shapeFrame1 = listOfSelectedShapes.get(i);
            if (shapeFrame1.gettheSize() > 0) shapeFrame1.gettheGroup().redoMovedGroupShapes();
            else shapeFrame1.gettheShape().move(newCoordinateX, newCoordinateY);
        }
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListOfSelectedShapes());
    }
}