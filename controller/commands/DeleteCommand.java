package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class DeleteCommand implements IEventCallback, IUndoable {

    private final ListForShapes listForShapes;
    int deleteNum;


    public DeleteCommand(ListForShapes listForShapes){

        this.listForShapes = listForShapes;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> myShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getDeletedShapeList();

        if (mySelectedShapeList.isEmpty()) {
            System.out.println("The SelectShapeList is empty! Nothing to delete");
            return;
        }

        for (int i = mySelectedShapeList.size() - 1; i >= 0; i--) {
            ShapeFrame s = mySelectedShapeList.get(i);

            myShapeList.remove(s);
            deletedShapeList.add(s);

            if (s.getSize() == 0) {
                s.getShape().shapeSelected = false;
            }

            deleteNum++;
        }

        mySelectedShapeList.clear();
        listForShapes.shapeListDrawer(myShapeList, mySelectedShapeList);
        CommandHistory.add(this);
    }


    @Override
    public void undo() {
        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getDeletedShapeList();

        if (deleteNum == 0) {
            deleteNum = deletedShapeList.size();
        }

        for (int i = 0; i < deleteNum && !deletedShapeList.isEmpty(); i++) {
            ShapeFrame lastShape = deletedShapeList.remove(deletedShapeList.size() - 1);
            lastShape.getShape().shapeSelected = true;
            mainShapeList.add(lastShape);
            mySelectedShapeList.add(lastShape);
        }

        listForShapes.shapeListDrawer(mainShapeList, mySelectedShapeList);
    }



    @Override
    public void redo() {
        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getDeletedShapeList();

        for (int i = 0; i < deleteNum && !deletedShapeList.isEmpty(); i++) {
            ShapeFrame lastShape = deletedShapeList.remove(deletedShapeList.size() - 1);
            lastShape.getShape().shapeSelected = false;
            mainShapeList.add(lastShape);
        }

        mySelectedShapeList.clear();
        listForShapes.shapeListDrawer(mainShapeList, mySelectedShapeList);
        deleteNum--;
    }


}


