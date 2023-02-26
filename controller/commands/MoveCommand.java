package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;

public class MoveCommand implements IEventCallback, IUndoable {

    private final ListForShapes listForShapes;
      ApplicationState appState;

    private final int deltaX;
    private final int deltaY;

    public MoveCommand(ApplicationState appState, Coordinate startCoordinate, Coordinate endCoordinate, ListForShapes listForShapes){
        this.listForShapes = listForShapes;
        this.appState = appState;
        deltaX = endCoordinate.getX()- startCoordinate.getX();
        deltaY = endCoordinate.getY()- startCoordinate.getY();
    }

    @Override
    public void run() {

        if (listForShapes.getSelectedShapeList().size()>0){
            for(ShapeFrame s : listForShapes.getSelectedShapeList()){
                s.getShape().move(deltaX,deltaY);
            }
            listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
            CommandHistory.add(this);
        }
    }

    @Override
    public void undo() {
        listForShapes.getSelectedShapeList();
        for(ShapeFrame s: listForShapes.getSelectedShapeList()){
            s.getShape().undoMove();
        }
        listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
    }

    @Override
    public void redo() {
        listForShapes.getSelectedShapeList();
        for(ShapeFrame s: listForShapes.getSelectedShapeList()){
            s.getShape().redoMove();
        }
        listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
    }
}