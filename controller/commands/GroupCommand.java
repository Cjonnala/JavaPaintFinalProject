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
        ArrayList<ShapeFrame> listOfSelectedShapes = listforShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfGroup = listforShapes.getListOfGroup();
        if(listOfSelectedShapes.size()!= 0){
            groupShape = new GroupForShapes();
            listOfGroup.add(groupShape);
            for (int i = 0; i < listOfSelectedShapes.size(); i++) {
                ShapeFrame shapeFrame = listOfSelectedShapes.get(i);
                shapesList.remove(shapeFrame);
                if (!shapeFrame.isGroup()) {
                    groupShape.addSubShape(shapeFrame);
                    shapeFrame.gettheShape().selectedShape = false;
                    countSubShapes++;
                } else if (shapeFrame.isGroup()) {
                    shapeFrame.gettheGroup().selectedGroup = false;
                    groupShape.addSubShape(shapeFrame);
                    countSubShapes++;
                }
            }
            shapesList.add(groupShape);
            listOfSelectedShapes.clear();
            listOfSelectedShapes.add(groupShape);
        }
        listforShapes.drawerForShapesList(shapesList,listOfSelectedShapes);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> listOfSelectedShapes = listforShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> shapesList = listforShapes.getShapesList();
        listOfSelectedShapes.remove(groupShape);
        shapesList.remove(groupShape);
        if (groupShape.gettheSize() != 0) {
            do {
                ShapeFrame shapeFrame = groupShape.removeSubShape(groupShape.gettheSize() - 1);
                shapesList.add(shapeFrame);
                listOfSelectedShapes.add(shapeFrame);
                shapeFrame.gettheShape().selectedShape = true;
            } while (groupShape.gettheSize() != 0);
        }
        listforShapes.drawerForShapesList(listforShapes.getShapesList(), listforShapes.getListOfSelectedShapes());
    }
    @Override
    public void redo() {
        ArrayList<ShapeFrame> listofSelectedShapes = listforShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> shapesList = listforShapes.getShapesList();
        for (int i = 0; i < listofSelectedShapes.size(); i++) {
            ShapeFrame shapeFrame = listofSelectedShapes.get(i);
            shapesList.remove(shapeFrame);
            if (!shapeFrame.isGroup()) {
                groupShape.addSubShape(shapeFrame);
                shapeFrame.gettheShape().selectedShape = false;
            } else if (shapeFrame.isGroup()) {
                shapeFrame.gettheGroup().selectedGroup = false;
                ArrayList<ShapeFrame> groupedSubShapes = shapeFrame.gettheGroup().getGroupedSubShapes();
                for (int j = 0; j < groupedSubShapes.size(); j++) {
                    ShapeFrame shapeFrame1 = groupedSubShapes.get(j);
                    groupShape.addSubShape(shapeFrame1);
                }
            }
        }
        shapesList.add(groupShape);
        listofSelectedShapes.clear();
        listofSelectedShapes.add(groupShape);
        listforShapes.drawerForShapesList(listforShapes.getShapesList(), listforShapes.getListOfSelectedShapes());
    }
}
