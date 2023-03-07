package controller.drawing;

import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;

public class ListForShapes {

    public static ArrayList<ShapeFrame> shapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> deletedShapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> selectedShapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> deselectedShapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> copiedShapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> pasteShapeList = new ArrayList<>();
    public static ArrayList<ShapeFrame> undoRedoList = new ArrayList<>();
    public static ArrayList<ShapeFrame> groupList = new ArrayList<>();

    private static PaintCanvas paintCanvas;
    public ApplicationState appState;


    public ListForShapes(PaintCanvasBase paintCanvas, ApplicationState appState) {
        ListForShapes.paintCanvas = (PaintCanvas) paintCanvas;
        this.appState = appState;
    }

    public void addShape(ShapeFrame shape) {
        shapeList.add(shape);
        shapeListDrawer(shapeList,selectedShapeList);
    }


    public void shapeListDrawer(ArrayList<ShapeFrame> shapeList, ArrayList<ShapeFrame> selectedShapeList){

        Graphics2D g = paintCanvas.getGraphics2D();
        g.setColor(Color.white);
        g.fillRect(0,0,9999,9999);
        for (ShapeFrame s: shapeList){
            s.draw(g);
            if(s.getSize()>0){
                s.drawChildren(g);
            }
        }
        if(selectedShapeList.size()>0){
            for (ShapeFrame z: selectedShapeList){
                ShapeOutline shapeOutline = new ShapeOutline(paintCanvas);
                if(z.isGroup()){
                    shapeOutline.outlineGroup(z);
                }
                else {
                    shapeOutline.outlineShape(z);
                }
            }
        }

//        CommandHistory.add(this);
    }

    public void removeShape() {
        if (shapeList.size() == 0) {
            System.out.println("There's nothing in the list to remove!");
            return;
        }
        ShapeFrame lastShape = shapeList.get(shapeList.size() - 1);
        lastShape.getShape().shapeSelected = false;
        shapeList.remove(lastShape);
        deletedShapeList.add(lastShape);
        shapeListDrawer(shapeList,selectedShapeList);
    }

    public void redoShape() {
        if (deletedShapeList.size() == 0 && shapeList.size() == 0) {
            System.out.println("There's nothing to redo!");
        } else if (deletedShapeList.size() == 0) {
            ShapeFrame lastShape = shapeList.get(shapeList.size() - 1);
            if (lastShape.getShape().undoPerformered) {
                lastShape.getShape().redoMove();
                lastShape.getShape().undoPerformered = false;
                lastShape.getShape().shapeSelected();
                shapeListDrawer(shapeList,selectedShapeList);
            }
        } else {
            addDeletedShapes();
        }
    }

    public void addDeletedShapes() {
        ShapeFrame d = deletedShapeList.remove(deletedShapeList.size() - 1);
        shapeList.add(d);
        d.getShape().shapeSelected = true;

        shapeListDrawer(shapeList,selectedShapeList);
    }


    public ArrayList<ShapeFrame> getSelectedShapeList() {
        return selectedShapeList;
    }

    public ArrayList<ShapeFrame> getShapeList() {
        return shapeList;
    }

    public ArrayList<ShapeFrame> getDeletedShapeList() {
        return deletedShapeList;
    }

    public ArrayList<ShapeFrame> getCopiedShapeList() {
        return copiedShapeList;
    }

    public ArrayList<ShapeFrame> getPasteShapeList() {
        return pasteShapeList;
    }

    public ArrayList<ShapeFrame> getUndoRedoList() {
        return undoRedoList;
    }
    public ArrayList<ShapeFrame> getGroupList() { return groupList;}
}