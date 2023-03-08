package controller.drawing;

import model.interfaces.ShapeFrame;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class ShapeOutline {
    PaintCanvasBase paintCanvas;
    public ShapeOutline(PaintCanvasBase paintCanvas){
        this.paintCanvas = paintCanvas;
    }
    public void outlineShape(ShapeFrame shapeFrame){

        Graphics2D g2d = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 2, new float[]{10}, 2);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        if(shapeFrame.getShape().shapeSelected)
        {
            switch (shapeFrame.getShape().getShapeType()) {
                case RECTANGLE ->
                        g2d.drawRect(shapeFrame.getShape().getMinXY().x - 5, shapeFrame.getShape().getMinXY().y - 5, shapeFrame.getShape().getWidth() + 10, shapeFrame.getShape().getHeight() + 10);
                case ELLIPSE ->
                        g2d.drawOval(shapeFrame.getShape().getMinXY().x - 5, shapeFrame.getShape().getMinXY().y - 5, shapeFrame.getShape().getWidth() + 10, shapeFrame.getShape().getHeight() + 10);
                case TRIANGLE -> {
                    Point newPoint = new Point(shapeFrame.getShape().getStartCoordinate().x, shapeFrame.getShape().getEndCoordinate().y);
                    int[] begin = new int[3];
                    int[] finish = new int[3];
                    begin[0] = shapeFrame.getShape().getStartCoordinate().getX();
                    begin[1] = shapeFrame.getShape().getEndCoordinate().getX();
                    begin[2] = (int) newPoint.getX();
                    finish[0] = shapeFrame.getShape().getStartCoordinate().getY();
                    finish[1] = shapeFrame.getShape().getEndCoordinate().getY();
                    finish[2] = (int) newPoint.getY();
                    g2d.drawPolygon(begin, finish, 3);
                }
                default -> System.out.println("this shape ain't selected!");
            }
        }

    }
    public void outlineGroup(ShapeFrame shapeFrame){
        int groupWidth = shapeFrame.getGroup().getMaximumCoordXY().x - shapeFrame.getGroup().getMinXY().x;
        int groupHeight = shapeFrame.getGroup().getMaximumCoordXY().y - shapeFrame.getGroup().getMinXY().y;

        Graphics2D g = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        g.setStroke(stroke);
        g.setColor(Color.BLACK);
        g.drawRect(shapeFrame.getGroup().getMinXY().x -5, shapeFrame.getGroup().getMinXY().y-5, groupWidth+10,groupHeight+10);
    }
}
