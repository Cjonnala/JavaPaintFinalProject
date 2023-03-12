package controller.drawing;

import model.interfaces.ShapeFrame;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class ShapeOutline implements ShapeFrame{
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
                case OCTAGON -> {
                    int startX = Math.min(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int endX = Math.max(shapeFrame.gettheShape().startCoordinate.getX(), shapeFrame.gettheShape().endCoordinate.getX());
                    int startY = Math.min(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int endY = Math.max(shapeFrame.gettheShape().startCoordinate.getY(), shapeFrame.gettheShape().endCoordinate.getY());
                    int width = endX - startX;
                    int height = endY - startY;
                    int[] xPoints = new int[] { startX + width/4, startX + 3*width/4, endX, endX, startX + 3*width/4, startX + width/4, startX, startX };
                    int[] yPoints = new int[] { startY, startY, startY + height/4, startY + 3*height/4, endY, endY, startY + 3*height/4, startY + height/4 };
                    int offset = 0; // adjust the offset as needed
                    int[] xPointsOffset = new int[xPoints.length];
                    int[] yPointsOffset = new int[yPoints.length];
                    for (int i = 0; i < xPoints.length; i++) {
                        xPointsOffset[i] = xPoints[i] + offset;
                        yPointsOffset[i] = yPoints[i] + offset;
                    }
                    g2d.drawPolygon(xPointsOffset, yPointsOffset, xPoints.length);
                }
                case SEPTAGON -> {
                    int centerX = (shapeFrame.gettheShape().getStartCoordinate().getX() + shapeFrame.gettheShape().getEndCoordinate().getX()) / 2;
                    int centerY = (shapeFrame.gettheShape().getStartCoordinate().getY() + shapeFrame.gettheShape().getEndCoordinate().getY()) / 2;
                    int radius = Math.min(shapeFrame.gettheShape().getWidth(), shapeFrame.gettheShape().getHeight()) / 2;
                    int[] xPoints = new int[7];
                    int[] yPoints = new int[7];
                    for (int i = 0; i < 7; i++) {
                        double angle = i * Math.PI / 3.5;
                        xPoints[i] = (int) Math.round(centerX + radius * Math.cos(angle));
                        yPoints[i] = (int) Math.round(centerY + radius * Math.sin(angle));
                    }
                    int offset = 0; // adjust the offset as needed
                    int[] xPointsOffset = new int[xPoints.length];
                    int[] yPointsOffset = new int[yPoints.length];
                    for (int i = 0; i < xPoints.length; i++) {
                        xPointsOffset[i] = xPoints[i] + offset;
                        yPointsOffset[i] = yPoints[i] + offset;
                    }
                    g2d.drawPolygon(xPointsOffset, yPointsOffset, xPoints.length);
                }
                case TRAPEZOID -> {
                    int x1 = shapeFrame.gettheShape().startCoordinate.getX();
                    int x2 = shapeFrame.gettheShape().endCoordinate.getX();
                    int y1 = shapeFrame.gettheShape().startCoordinate.getY();
                    int y2 = shapeFrame.gettheShape().endCoordinate.getY();
                    int height = Math.abs(y2 - y1);
                    int width = Math.abs(x2 - x1);
                    int topWidth = (int) (width - ((-38.0) / height) * shapeFrame.gettheShape().getWidth());
                    int x3 = x1 + (width - topWidth) / 38;
                    int x4 = x3 + topWidth;

                    int[] xPoints = {x1-5, x3-5, x4+10, x2+8};
                    int[] yPoints = {y1-5, y2+10, y2+10, y1-5};
                    g2d.drawPolygon(xPoints, yPoints, 4);
                }
                case SQUARE -> {
                    int x1 = shapeFrame.gettheShape().startCoordinate.getX();
                    int x2 = shapeFrame.gettheShape().endCoordinate.getX();
                    int y1 = shapeFrame.gettheShape().startCoordinate.getY();
                    int y2 = shapeFrame.gettheShape().endCoordinate.getY();
                    int minX = Math.min(x1, x2) - 5;
                    int minY = Math.min(y1, y2) - 5;
                    int side = Math.abs(Math.min(x2 - x1, y2 - y1)) + 10;
                    g2d.drawRect(minX, minY, side, side);
                }

                default -> System.out.println("this shape ain't selected!");
            }
        }

    }
    public void outlineGroup(ShapeFrame shapeFrame){
        int groupWidth = shapeFrame.gettheGroup().getMaximumCordXY().x - shapeFrame.gettheGroup().getMinimumCoordXY().x;
        int groupHeight = shapeFrame.gettheGroup().getMaximumCordXY().y - shapeFrame.gettheGroup().getMinimumCoordXY().y;

        Graphics2D g = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        g.setStroke(stroke);
        g.setColor(Color.BLACK);
        g.drawRect(shapeFrame.gettheGroup().getMinimumCoordXY().x -5, shapeFrame.gettheGroup().getMinimumCoordXY().y-5, groupWidth+10,groupHeight+10);
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public Coordinate getStartCoordinate() {
        return null;
    }

    @Override
    public Coordinate getEndCoordinate() {
        return null;
    }

    @Override
    public Shape gettheShape() {
        return null;
    }

    @Override
    public int gettheSize() {
        return 0;
    }

    @Override
    public void drawSubShape(Graphics2D g) {

    }

    @Override
    public GroupForShapes gettheGroup() {
        return null;
    }

    @Override
    public boolean isGroup() {
        return false;
    }
}
