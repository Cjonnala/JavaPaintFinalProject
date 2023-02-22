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
                    Point newPoint = new Point(shapeFrame.getShape().getStartPoint().x, shapeFrame.getShape().getEndPoint().y);
                    int[] begin = new int[3];
                    int[] finish = new int[3];
                    begin[0] = shapeFrame.getShape().getStartPoint().getX();
                    begin[1] = shapeFrame.getShape().getEndPoint().getX();
                    begin[2] = (int) newPoint.getX();
                    finish[0] = shapeFrame.getShape().getStartPoint().getY();
                    finish[1] = shapeFrame.getShape().getEndPoint().getY();
                    finish[2] = (int) newPoint.getY();
                    g2d.drawPolygon(begin, finish, 3);
                }
                default -> System.out.println("this shape ain't selected!");
            }
        }

    }
}
