package controller.drawing;

import model.interfaces.ShapeFrame;

import java.awt.*;
import java.util.ArrayList;

public class GroupForShapes implements ShapeFrame {

    ShapeFrame shape;
    Graphics2D g;
    int newX, newY;

    public boolean selectedGroup;
    public ArrayList<ShapeFrame> groupedSubShapes;

    public GroupForShapes() {
        groupedSubShapes = new ArrayList<>();
    }

    public ArrayList<ShapeFrame> getGroupedSubShapes() {
        return groupedSubShapes;
    }

    public void addSubShape(ShapeFrame shape) {
        groupedSubShapes.add(shape);
    }

    public ShapeFrame removeSubShape(int i) {
        ShapeFrame shapeFrame;
        shapeFrame = this.groupedSubShapes.remove(i);
        return shapeFrame;
    }

    public int gettheSize() {
        int size = 0;
        for (ShapeFrame ignored : groupedSubShapes) {
            size++;
        }
        return size;
    }


    public Point getMinimumCoordXY() {
        int startShapeX = 9999;
        int startShapeY = 9999;
        for (int i = 0; i < groupedSubShapes.size(); i++) {
            ShapeFrame child = groupedSubShapes.get(i);
            if (!child.isGroup()) {
                if (child.gettheShape().getMinimumXY().x < startShapeX)
                    startShapeX = child.gettheShape().getMinimumXY().x;
                if (child.gettheShape().getMinimumXY().y < startShapeY)
                    startShapeY = child.gettheShape().getMinimumXY().y;
            } else {
                if (child.gettheGroup().getMinimumCoordXY().x < startShapeX)
                    startShapeX = child.gettheGroup().getMinimumCoordXY().x;
                if (child.gettheGroup().getMinimumCoordXY().y < startShapeY)
                    startShapeY = child.gettheGroup().getMinimumCoordXY().y;
            }


        }
        return new Point(startShapeX, startShapeY);
    }

    public Point getMaximumCordXY() {
        int endShapeX = 0;
        int endShapeY = 0;
        for (int i = 0; i < groupedSubShapes.size(); i++) {
            ShapeFrame child = groupedSubShapes.get(i);
            if (!child.isGroup()) {
                if (child.gettheShape().getMaximumXY().x > endShapeX) endShapeX = child.gettheShape().getMaximumXY().x;
                if (child.gettheShape().getMaximumXY().y > endShapeY) endShapeY = child.gettheShape().getMaximumXY().y;
            } else {
                if (child.gettheGroup().getMaximumCordXY().x > endShapeX)
                    endShapeX = child.gettheGroup().getMaximumCordXY().x;
                if (child.gettheGroup().getMaximumCordXY().y > endShapeY)
                    endShapeY = child.gettheGroup().getMaximumCordXY().y;
            }

        }
        return new Point(endShapeX, endShapeY);
    }

    @Override
    public void drawSubShape(Graphics2D g) {
        this.g = g;
        for (int i = 0; i < groupedSubShapes.size(); i++) {
            ShapeFrame shapeFrame = groupedSubShapes.get(i);
            if (shapeFrame.gettheSize() > 0) shapeFrame.drawSubShape(g);
            else shapeFrame.draw(g);
        }
    }

    @Override
    public boolean isGroup() {
        return true;
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
    public GroupForShapes gettheGroup() {
        return this;
    }

    public void moveGroupedSubShapes(int newCoordinateX, int newCoordinateY){
        this.newX = newCoordinateX;
        this.newY = newCoordinateY;
        for (int i = 0, groupedSubShapesSize = groupedSubShapes.size(); i < groupedSubShapesSize; i++) {
            ShapeFrame shapeFrame = groupedSubShapes.get(i);
            shapeFrame.gettheShape().setStartCoordinate((shapeFrame.gettheShape().getStartCoordinate().x) + newCoordinateX, (shapeFrame.gettheShape().getStartCoordinate().y) + newCoordinateY);
            shapeFrame.gettheShape().setEndCoordinate((shapeFrame.gettheShape().getEndCoordinate().x) + newCoordinateX, (shapeFrame.gettheShape().getEndCoordinate().y) + newCoordinateY);
        }
    }

    public void undoMovedGroupShapes(){
        for (int i = 0, groupedSubShapesSize = groupedSubShapes.size(); i < groupedSubShapesSize; i++) {
            ShapeFrame shapeFrame1 = groupedSubShapes.get(i);
            shapeFrame1.gettheShape().setStartCoordinate((shapeFrame1.gettheShape().getStartCoordinate().x) - newX, (shapeFrame1.gettheShape().getStartCoordinate().y) - newY);
            shapeFrame1.gettheShape().setEndCoordinate((shapeFrame1.gettheShape().getEndCoordinate().x) - newX, (shapeFrame1.gettheShape().getEndCoordinate().y) - newY);
        }
    }

    public void redoMovedGroupShapes() {
        for (int i = 0, groupedSubShapesSize = groupedSubShapes.size(); i < groupedSubShapesSize; i++) {
            ShapeFrame shapeFrame2 = groupedSubShapes.get(i);
            shapeFrame2.gettheShape().setStartCoordinate((shapeFrame2.gettheShape().getStartCoordinate().x) + newX, (shapeFrame2.gettheShape().getStartCoordinate().y) + newY);
            shapeFrame2.gettheShape().setEndCoordinate((shapeFrame2.gettheShape().getEndCoordinate().x) + newX, (shapeFrame2.gettheShape().getEndCoordinate().y) + newY);
        }
    }
}
