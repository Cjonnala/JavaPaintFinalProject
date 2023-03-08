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
    public int pasteCount;



    public PasteCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }

    @Override
    public void run() {
        pasteCount =0;

        ArrayList<ShapeFrame> copiedShapeList = listForShapes.getCopiedShapeList();
        ArrayList<ShapeFrame> pasteShapeList = listForShapes.getPasteShapeList();

        for(ShapeFrame shapeFrame: pasteShapeList){
            shapeFrame.getShape().shapePasted=false;
        }

        for(ShapeFrame shapeFrame: copiedShapeList) {
            if (!shapeFrame.isGroup()) {
                int x1 = shapeFrame.getShape().getStartCoordinate().x - 100;
                int y1 = shapeFrame.getShape().getStartCoordinate().y - 100;
                int x2 = shapeFrame.getShape().getEndCoordinate().x - 100;
                int y2 = shapeFrame.getShape().getEndCoordinate().y - 100;
                Coordinate coordinateX = new Coordinate(x1, y1);
                Coordinate coordinateY = new Coordinate(x2, y2);
                IEventCallback createShapeCommand = new ShapeCreating(shapeFrame.getShape().appState, coordinateX, coordinateY, shapeFrame.getShape().getpColor(), shapeFrame.getShape().getsColor(), listForShapes, shapeFrame.getShape().getShadingType(), shapeFrame.getShape().getShapeType());
                createShapeCommand.run();
                pasteCount++;
            } else {
                for (ShapeFrame shapeFrame1 : shapeFrame.getGroup().groupedSubshapes) {
                    int i1 = shapeFrame1.getShape().getStartCoordinate().x - 100;
                    int j1 = shapeFrame1.getShape().getStartCoordinate().y - 100;
                    int i2 = shapeFrame1.getShape().getEndCoordinate().x - 100;
                    int j2 = shapeFrame1.getShape().getEndCoordinate().y - 100;
                    Coordinate coordinateX1 = new Coordinate(i1, j1);
                    Coordinate coordinateX2 = new Coordinate(i2, j2);
                    IEventCallback createShapeCommand = new ShapeCreating(shapeFrame1.getShape().appState, coordinateX1, coordinateX2, shapeFrame1.getShape().getpColor(), shapeFrame1.getShape().getsColor(), listForShapes, shapeFrame1.getShape().getShadingType(), shapeFrame1.getShape().getShapeType());
                    createShapeCommand.run();
                    pasteCount++;
                }
            }
        }

        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(masterShapeList.size() == 0) {
            return;
        }

        while(pasteCount !=0){
            ShapeFrame lastShape = masterShapeList.get(masterShapeList.size()-1);
            masterShapeList.remove(lastShape);
            undoRedoList.add(lastShape);
            listForShapes.shapeListDrawer(masterShapeList, listForShapes.getSelectedShapeList());
            pasteCount--;
        }
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(masterShapeList.size() == 0) {
            return;
        }
        else{

            while(undoRedoList.size()!=0) {
                ShapeFrame lastShape = undoRedoList.get(undoRedoList.size() - 1);
                undoRedoList.remove(lastShape);
                masterShapeList.add(lastShape);
                listForShapes.shapeListDrawer(masterShapeList, listForShapes.getSelectedShapeList());
                pasteCount++;
            }
        }
    }
}

