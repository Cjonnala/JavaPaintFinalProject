package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.GroupForShapes;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import java.util.ArrayList;

public class UngroupCommand implements IEventCallback, IUndoable {

    ListForShapes listForShapes;

    public UngroupCommand(ListForShapes listForShapes) {
        this.listForShapes = listForShapes;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getGroupList();

        ShapeFrame shapeFrame1 = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 0) {
            System.out.println("there's nothing here!");
        } else if (shapesGroupList.size() == 1) {
            for (ShapeFrame shapeFrame : shapeFrame1.getGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame);
                selectedShapeList.add(shapeFrame);
                shapeFrame.getShape().shapeSelected = true;
            }
            shapeFrame1.getGroup().groupSelected = false;
        } else if (shapesGroupList.size() > 1) {

            for (ShapeFrame shapeFrame2 : shapeFrame1.getGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame2);
                selectedShapeList.add(shapeFrame2);
                if (shapeFrame2.isGroup() == false) {
                    shapeFrame2.getShape().shapeSelected = true;
                } else {
                    shapeFrame2.getGroup().groupSelected = true;
                }
            }
            shapeFrame1.getGroup().groupSelected = false;
        }

        shapesGroupList.remove(shapeFrame1);
        masterShapeList.remove(shapeFrame1);
        selectedShapeList.remove(shapeFrame1);
        undoRedoList.add(shapeFrame1);
        listForShapes.shapeListDrawer(masterShapeList, selectedShapeList);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getGroupList();

        GroupForShapes shapeGroup = new GroupForShapes();

        for (ShapeFrame shapeFrame2 : selectedShapeList) {
            masterShapeList.remove(shapeFrame2);
            shapeGroup.addChild(shapeFrame2);
            if (shapeFrame2.isGroup() == false) {
                shapeFrame2.getShape().shapeSelected = false;
            } else {
                shapeFrame2.getGroup().groupSelected = false;
            }
        }

        masterShapeList.add(shapeGroup);
        selectedShapeList.clear();
        selectedShapeList.add(shapeGroup);
        shapesGroupList.add(shapeGroup);
        shapeGroup.groupSelected = true;
        listForShapes.shapeListDrawer(masterShapeList, selectedShapeList);
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getGroupList();

        ShapeFrame outerShapeGroup = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 0) {
            System.out.println("there's nothing here!");
        } else if (shapesGroupList.size() == 1) {
            for (ShapeFrame shapeFrame3 : outerShapeGroup.getGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame3);
                selectedShapeList.add(shapeFrame3);
                shapeFrame3.getShape().shapeSelected = true;
            }
            outerShapeGroup.getGroup().groupSelected = false;
        } else if (shapesGroupList.size() > 1) {
            for (ShapeFrame s : outerShapeGroup.getGroup().groupedSubshapes) {
                masterShapeList.add(s);
                selectedShapeList.add(s);
                if (s.isGroup() == false) {
                    s.getShape().shapeSelected = true;
                } else {
                    s.getGroup().groupSelected = true;
                }
            }
            outerShapeGroup.getGroup().groupSelected = false;
        }

        shapesGroupList.remove(outerShapeGroup);
        masterShapeList.remove(outerShapeGroup);
        selectedShapeList.remove(outerShapeGroup);
        undoRedoList.add(outerShapeGroup);

        listForShapes.shapeListDrawer(masterShapeList, selectedShapeList);
    }
}
