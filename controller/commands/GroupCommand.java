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
    GroupForShapes shapeGroup;
    int countSubShapes;


    public GroupCommand(ListForShapes listforShapes){
        this.listforShapes = listforShapes;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> masterShapeList = listforShapes.getShapeList();
        ArrayList<ShapeFrame> selectedShapes = listforShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> shapesGroupList = listforShapes.getGroupList();
        if(selectedShapes.size()!= 0){
            shapeGroup = new GroupForShapes();
            shapesGroupList.add(shapeGroup);
            for(ShapeFrame shapeFrame : selectedShapes){
                masterShapeList.remove(shapeFrame);
                if(!shapeFrame.isGroup()){
                    shapeGroup.addChild(shapeFrame);
                    shapeFrame.getShape().shapeSelected=false;
                    countSubShapes++;
                }
                else if(shapeFrame.isGroup()){
                    shapeFrame.getGroup().groupSelected=false;
                    shapeGroup.addChild(shapeFrame);
                    countSubShapes++;
                }

            }
            masterShapeList.add(shapeGroup);
            selectedShapes.clear();
            selectedShapes.add(shapeGroup);
        }
        listforShapes.shapeListDrawer(masterShapeList,selectedShapes);

        CommandHistory.add(this);
    }

    @Override
    public void undo() {

        ArrayList<ShapeFrame> selectedShapeList = listforShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> masterShapeList = listforShapes.getShapeList();

        selectedShapeList.remove(shapeGroup);
        masterShapeList.remove(shapeGroup);

        while(shapeGroup.getSize()!=0){
            ShapeFrame shapeFrame = shapeGroup.removeChild(shapeGroup.getSize()-1);
            masterShapeList.add(shapeFrame);
            selectedShapeList.add(shapeFrame);
            shapeFrame.getShape().shapeSelected=true;
        }
        listforShapes.shapeListDrawer(listforShapes.getShapeList(), listforShapes.getSelectedShapeList());
    }

    @Override
    public void redo() {


        ArrayList<ShapeFrame> selectedShapeList = listforShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> mainShapeList = listforShapes.getShapeList();

        for (ShapeFrame shapeFrame : selectedShapeList) {
            mainShapeList.remove(shapeFrame);
            if (!shapeFrame.isGroup()) {
                shapeGroup.addChild(shapeFrame);
                shapeFrame.getShape().shapeSelected = false;
            }
            else if (shapeFrame.isGroup()) {
                shapeFrame.getGroup().groupSelected = false;
                for (ShapeFrame shapeFrame1 : shapeFrame.getGroup().getGroupedSubshapes()) {
                    shapeGroup.addChild(shapeFrame1);
                }
            }
        }
        mainShapeList.add(shapeGroup);
        selectedShapeList.clear();
        selectedShapeList.add(shapeGroup);
        listforShapes.shapeListDrawer(listforShapes.getShapeList(), listforShapes.getSelectedShapeList());
    }

}
