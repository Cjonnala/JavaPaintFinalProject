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

        if(mySelectedShapeList.size()==0) {
            System.out.println("The SelectShapeList is empty! Nothing to delete");
        }
        else {
            for(ShapeFrame s: mySelectedShapeList){
                if(s.getSize()==0){

                    myShapeList.remove(s);
                    deletedShapeList.add(s);
                    s.getShape().shapeSelected=false;
                    deleteNum++;
                }
                else{

                    myShapeList.remove(s);
                    deletedShapeList.add(s);
                    deleteNum++;
                }
            }
            mySelectedShapeList.clear();
            listForShapes.shapeListDrawer(myShapeList,mySelectedShapeList);
            CommandHistory.add(this);
        }

    }

    @Override
    public void undo() {

        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getDeletedShapeList();


        if(deleteNum==0){
            deleteNum=deletedShapeList.size();
        }

        while(deletedShapeList.size()!=0){
            ShapeFrame lastShape = deletedShapeList.get(deletedShapeList.size()-1);
            deletedShapeList.remove(lastShape);
            mainShapeList.add(lastShape);
            mySelectedShapeList.add(lastShape);
            lastShape.getShape().shapeSelected=true;
        }
        listForShapes.shapeListDrawer(mainShapeList, listForShapes.getSelectedShapeList());

    }


    @Override
    public void redo() {

        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getDeletedShapeList();

        while(deleteNum!=0){
            ShapeFrame lastShape = mainShapeList.get(mainShapeList.size()-1);
            mainShapeList.remove(lastShape);
            deletedShapeList.add(lastShape);
            mySelectedShapeList.clear();

            lastShape.getShape().shapeSelected=false;
        }
        listForShapes.shapeListDrawer(mainShapeList, listForShapes.getSelectedShapeList());
        deleteNum--;

    }

}


