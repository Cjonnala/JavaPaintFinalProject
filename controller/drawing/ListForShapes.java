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

    private static PaintCanvas paintCanvas;
    public ApplicationState appState;


    public ListForShapes(PaintCanvasBase paintCanvas, ApplicationState appState) {
        this.paintCanvas = (PaintCanvas) paintCanvas;
        this.appState = appState;
    }

    public void addShape(ShapeFrame shape) {
        shapeList.add(shape);
        shapeListDrawer(shapeList, selectedShapeList);
    }


    public void shapeListDrawer(ArrayList<ShapeFrame> shapeList, ArrayList<ShapeFrame> selectedShapeList) {

        Graphics2D g = paintCanvas.getGraphics2D();
        g.setColor(Color.white);
        g.fillRect(0, 0, 99999, 99999);
        for (ShapeFrame s : shapeList) {
            s.draw(g);
            if (s.getSize() > 0) {
                s.drawChildren(g);
            }
        }
    }
}
