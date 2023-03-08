package controller.drawing;

import model.interfaces.ShapeFrame;

import java.awt.*;
import java.util.ArrayList;

public class GroupForShapes implements ShapeFrame {

    ShapeFrame shape;
    public ArrayList<ShapeFrame> groupedSubshapes;
    Graphics2D g;
    public boolean groupSelected;
    int newX, newY;

    public GroupForShapes() {
        groupedSubshapes = new ArrayList<>();
    }

    public ArrayList<ShapeFrame> getGroupedSubshapes() {
        return groupedSubshapes;
    }

    public void addChild(ShapeFrame shape) {
        groupedSubshapes.add(shape);
    }

    public ShapeFrame removeChild(int i) {
        ShapeFrame shapeFrame;
        shapeFrame = this.groupedSubshapes.remove(i);
        return shapeFrame;
    }

    public int getSize() {
        int size = 0;
        for (ShapeFrame ignored : groupedSubshapes) {
            size++;
        }
        return size;
    }


    public Point getMinXY() {
        int shapeStartX = 9999;
        int shapeStartY = 9999;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.getShape().getMinXY().x < shapeStartX) {
                    shapeStartX = child.getShape().getMinXY().x;
                }
                if (child.getShape().getMinXY().y < shapeStartY) {
                    shapeStartY = child.getShape().getMinXY().y;

                }
            } else {
                if (child.getGroup().getMinXY().x < shapeStartX) {
                    shapeStartX = child.getGroup().getMinXY().x;
                }
                if (child.getGroup().getMinXY().y < shapeStartY) {
                    shapeStartY = child.getGroup().getMinXY().y;
                }
            }


        }
        return new Point(shapeStartX, shapeStartY);
    }

    public Point getMaximumCoordXY() {
        int shapeEndX = 0;
        int shapeEndY = 0;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.getShape().getMaximumCoordXY().x > shapeEndX) {
                    shapeEndX = child.getShape().getMaximumCoordXY().x;
                }
                if (child.getShape().getMaximumCoordXY().y > shapeEndY) {
                    shapeEndY = child.getShape().getMaximumCoordXY().y;
                }
            } else {
                if (child.getGroup().getMaximumCoordXY().x > shapeEndX) {
                    shapeEndX = child.getGroup().getMaximumCoordXY().x;
                }
                if (child.getGroup().getMaximumCoordXY().y > shapeEndY) {
                    shapeEndY = child.getGroup().getMaximumCoordXY().y;
                }
            }

        }
        return new Point(shapeEndX, shapeEndY);
    }

    @Override
    public void drawChildren(Graphics2D g) {
        this.g = g;
        for (ShapeFrame shapeFrame : groupedSubshapes) {
            if (shapeFrame.getSize() > 0) {
                shapeFrame.drawChildren(g);
            } else {
                shapeFrame.draw(g);
            }
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
    public Shape getShape() {
        return null;
    }

    @Override
    public GroupForShapes getGroup() {
        return this;
    }

    public void moveGroupedSubShapes(int newCoordinateX, int newCoordinateY){
        this.newX = newCoordinateX;
        this.newY = newCoordinateY;
        for(ShapeFrame shapeFrame : groupedSubshapes){
            shapeFrame.getShape().setStartCoordinate((shapeFrame.getShape().getStartCoordinate().x)+newCoordinateX, (shapeFrame.getShape().getStartCoordinate().y)+newCoordinateY);
            shapeFrame.getShape().setEndCoordinate((shapeFrame.getShape().getEndCoordinate().x)+newCoordinateX, (shapeFrame.getShape().getEndCoordinate().y)+newCoordinateY);
        }
    }

    public void undoMovedGroupShapes(){
        for(ShapeFrame shapeFrame1 : groupedSubshapes){
            shapeFrame1.getShape().setStartCoordinate((shapeFrame1.getShape().getStartCoordinate().x)- newX, (shapeFrame1.getShape().getStartCoordinate().y)- newY);
            shapeFrame1.getShape().setEndCoordinate((shapeFrame1.getShape().getEndCoordinate().x)- newX, (shapeFrame1.getShape().getEndCoordinate().y)- newY);
        }
    }

    public void redoMovedGroupShapes() {
        for (ShapeFrame shapeFrame2 : groupedSubshapes) {
            shapeFrame2.getShape().setStartCoordinate((shapeFrame2.getShape().getStartCoordinate().x) + newX, (shapeFrame2.getShape().getStartCoordinate().y) + newY);
            shapeFrame2.getShape().setEndCoordinate((shapeFrame2.getShape().getEndCoordinate().x) + newX, (shapeFrame2.getShape().getEndCoordinate().y) + newY);
        }
    }
}
