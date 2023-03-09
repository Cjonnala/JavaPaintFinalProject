//Jonnala Chaitanya Lakshmi
//This class is created to implement the group functionality for the selected shapes in the paint canvas application
package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.GroupForShapes;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class GroupCommand implements IEventCallback, IUndoable {

    ListForShapes listforShapes;
    GroupForShapes groupShape;
    int countSubShapes;


    public GroupCommand(ListForShapes listforShapes){
        this.listforShapes = listforShapes;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> shapesList = listforShapes.getShapesList();
        ArrayList<ShapeFrame> listofSelectedShapes = listforShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> listofGroup = listforShapes.getListofGroup();
        if(listofSelectedShapes.size()!= 0){
            groupShape = new GroupForShapes();
            listofGroup.add(groupShape);
            for(ShapeFrame shapeFrame : listofSelectedShapes){
                shapesList.remove(shapeFrame);
                if(!shapeFrame.isGroup()){
                    groupShape.addChild(shapeFrame);
                    shapeFrame.gettheShape().selectedShape =false;
                    countSubShapes++;
                }
                else if(shapeFrame.isGroup()){
                    shapeFrame.gettheGroup().selectedGroup =false;
                    groupShape.addChild(shapeFrame);
                    countSubShapes++;
                }

            }
            shapesList.add(groupShape);
            listofSelectedShapes.clear();
            listofSelectedShapes.add(groupShape);
        }
        listforShapes.drawerForShapesList(shapesList,listofSelectedShapes);

        CommandHistory.add(this);
    }

    @Override
    public void undo() {

        ArrayList<ShapeFrame> listofSelectedShapes = listforShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> shapesList = listforShapes.getShapesList();

        listofSelectedShapes.remove(groupShape);
        shapesList.remove(groupShape);

        while(groupShape.gettheSize()!=0){
            ShapeFrame shapeFrame = groupShape.removeChild(groupShape.gettheSize()-1);
            shapesList.add(shapeFrame);
            listofSelectedShapes.add(shapeFrame);
            shapeFrame.gettheShape().selectedShape =true;
        }
        listforShapes.drawerForShapesList(listforShapes.getShapesList(), listforShapes.getListofSelectedShapes());
    }

    @Override
    public void redo() {


        ArrayList<ShapeFrame> listofSelectedShapes = listforShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> shapesList = listforShapes.getShapesList();

        for (ShapeFrame shapeFrame : listofSelectedShapes) {
            shapesList.remove(shapeFrame);
            if (!shapeFrame.isGroup()) {
                groupShape.addChild(shapeFrame);
                shapeFrame.gettheShape().selectedShape = false;
            }
            else if (shapeFrame.isGroup()) {
                shapeFrame.gettheGroup().selectedGroup = false;
                for (ShapeFrame shapeFrame1 : shapeFrame.gettheGroup().getGroupedSubshapes()) {
                    groupShape.addChild(shapeFrame1);
                }
            }
        }
        shapesList.add(groupShape);
        listofSelectedShapes.clear();
        listofSelectedShapes.add(groupShape);
        listforShapes.drawerForShapesList(listforShapes.getShapesList(), listforShapes.getListofSelectedShapes());
    }

}
