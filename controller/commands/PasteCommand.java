package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import controller.drawing.ShapeCreating;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class PasteCommand implements IEventCallback, IUndoable {

    private final ListForShapes listForShapes;
    public int pasteNum;



    public PasteCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }

    @Override
    public void run() {
        pasteNum=0;

        ArrayList<ShapeFrame> copiedShapeList = listForShapes.getCopiedShapeList();
        ArrayList<ShapeFrame> pasteShapeList = listForShapes.getPasteShapeList();

        for(ShapeFrame s: pasteShapeList){
            s.getShape().shapePasted=false;
        }

        for(ShapeFrame s: copiedShapeList){
            int x1 = s.getShape().getStartPoint().x - 100;
            int y1 = s.getShape().getStartPoint().y - 100;
            int x2 = s.getShape().getEndPoint().x -100;
            int y2 = s.getShape().getEndPoint().y - 100;
            Coordinate s2SCoordinate = new Coordinate(x1,y1);
            Coordinate s2ECoordinate = new Coordinate(x2,y2);
            IEventCallback createShapeCommand = new ShapeCreating(s.getShape().appState, s2SCoordinate, s2ECoordinate,s.getShape().getpColor(), s.getShape().getsColor(),listForShapes, s.getShape().getShadingType(), s.getShape().getShapeType());
            createShapeCommand.run();
            pasteNum++;
        }

        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(mainShapeList.size() == 0) { return; }

        while(pasteNum!=0){
            ShapeFrame lastShape = mainShapeList.get(mainShapeList.size()-1);
            mainShapeList.remove(lastShape);
            undoRedoList.add(lastShape);
            listForShapes.shapeListDrawer(mainShapeList, listForShapes.getSelectedShapeList());
            pasteNum--;
        }
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(mainShapeList.size() == 0) {
        }
        else{

            while(undoRedoList.size()!=0) {
                ShapeFrame lastShape = undoRedoList.get(undoRedoList.size() - 1);
                undoRedoList.remove(lastShape);
                mainShapeList.add(lastShape);
                listForShapes.shapeListDrawer(mainShapeList, listForShapes.getSelectedShapeList());
                pasteNum++;
            }
        }
    }
}

