package controller.drawing;

import model.interfaces.ShapeFrame;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

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
        if(shapeFrame.gettheShape().selectedShape)
        {
            switch (shapeFrame.gettheShape().getShapeType()) {
                case RECTANGLE ->
                        g2d.drawRect(shapeFrame.gettheShape().getMinimumXY().x - 5, shapeFrame.gettheShape().getMinimumXY().y - 5, shapeFrame.gettheShape().getWidth() + 10, shapeFrame.gettheShape().getHeight() + 10);
                case ELLIPSE ->
                        g2d.drawOval(shapeFrame.gettheShape().getMinimumXY().x - 5, shapeFrame.gettheShape().getMinimumXY().y - 5, shapeFrame.gettheShape().getWidth() + 10, shapeFrame.gettheShape().getHeight() + 10);
                case TRIANGLE -> {
                    Point newPoint = new Point(shapeFrame.gettheShape().getStartCoordinate().x, shapeFrame.gettheShape().getEndCoordinate().y);
                    int[] begin = new int[3];
                    int[] finish = new int[3];
                    begin[0] = shapeFrame.gettheShape().getStartCoordinate().getX();
                    begin[1] = shapeFrame.gettheShape().getEndCoordinate().getX();
                    begin[2] = (int) newPoint.getX();
                    finish[0] = shapeFrame.gettheShape().getStartCoordinate().getY();
                    finish[1] = shapeFrame.gettheShape().getEndCoordinate().getY();
                    finish[2] = (int) newPoint.getY();
                    g2d.drawPolygon(begin, finish, 3);
                }
                case SEMI_CIRCLE ->
                        g2d.draw(new Arc2D.Double(shapeFrame.gettheShape().getMinimumXY().x - 5, shapeFrame.gettheShape().getMinimumXY().y - 5, shapeFrame.gettheShape().getWidth() + 10, shapeFrame.gettheShape().getHeight() + 10, 90, 180, Arc2D.OPEN));
                case PENTAGON -> {
                    int startX = Math.min(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int endX = Math.max(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int startY = Math.min(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int endY = Math.max(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int width = (endX - startX);
                    int height = (endY - startY);
                    int[] xPoints = new int[] { (startX + width/2), (startX + width), (startX + 3*width/4), (startX + width/4), startX};
                    int[] yPoints = new int[] { startY, (startY + height/2), endY, endY, (startY + height/2)};
                    g2d.drawPolygon(xPoints, yPoints, 5);
                }
                case RHOMBUS -> {
                    int[] xPoints = new int[] {
                            shapeFrame.gettheShape().getStartCoordinate().getX() + (shapeFrame.gettheShape().getWidth() / 2),
                            shapeFrame.gettheShape().getEndCoordinate().getX(),
                            shapeFrame.gettheShape().getStartCoordinate().getX() + (shapeFrame.gettheShape().getWidth() / 2),
                            shapeFrame.gettheShape().getStartCoordinate().getX()
                    };
                    int[] yPoints = new int[] {
                            shapeFrame.gettheShape().getStartCoordinate().getY(),
                            shapeFrame.gettheShape().getStartCoordinate().getY() + (shapeFrame.gettheShape().getHeight() / 2),
                            shapeFrame.gettheShape().getEndCoordinate().getY(),
                            shapeFrame.gettheShape().getStartCoordinate().getY() + (shapeFrame.gettheShape().getHeight() / 2)
                    };
                    g2d.drawPolygon(xPoints, yPoints, 4);
                }
                case DROPLET -> {
                    int startX = Math.min(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int endX = Math.max(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int startY = Math.min(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int endY = Math.max(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int width = endX - startX;
                    int height = endY - startY;
                    double controlX = width / 2.0;
                    double controlY = height / 2.0;
                    double topX = startX + controlX;
                    double topY = startY + controlY / 2.0;
                    GeneralPath path = new GeneralPath();
                    path.moveTo(topX, startY);
                    path.curveTo(endX, startY, endX, topY, topX, endY);
                    path.curveTo(startX, topY, startX, startY, topX, startY);
                    g2d.draw(path);
                }


                default -> System.out.println("this shape ain't selected!");
            }
        }

    }
    public void outlineGroup(ShapeFrame shapeFrame){
        int groupWidth = shapeFrame.gettheGroup().getMaximumCoordXY().x - shapeFrame.gettheGroup().getMinimumCoordXY().x;
        int groupHeight = shapeFrame.gettheGroup().getMaximumCoordXY().y - shapeFrame.gettheGroup().getMinimumCoordXY().y;

        Graphics2D g = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        g.setStroke(stroke);
        g.setColor(Color.BLACK);
        g.drawRect(shapeFrame.gettheGroup().getMinimumCoordXY().x -5, shapeFrame.gettheGroup().getMinimumCoordXY().y-5, groupWidth+10,groupHeight+10);
    }
}
