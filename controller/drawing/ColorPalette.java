package controller.drawing;

import model.ShapeColor;

import java.awt.*;

public class ColorPalette {

    private Color color;

    public ColorPalette(ShapeColor colorEnum){

        switch (colorEnum) {
            case BLACK -> this.color = Color.BLACK;
            case BLUE -> this.color = Color.BLUE;
            case CYAN -> this.color = Color.CYAN;
            case DARK_GRAY -> this.color = Color.darkGray;
            case GRAY -> this.color = Color.GRAY;
            case GREEN -> this.color = Color.GREEN;
            case LIGHT_GRAY -> this.color = Color.LIGHT_GRAY;
            case MAGENTA -> this.color = Color.MAGENTA;
            case ORANGE -> this.color = Color.ORANGE;
            case RED -> this.color = Color.red;
            case PINK -> this.color = Color.pink;
            case WHITE -> this.color = Color.white;
            case YELLOW -> this.color = Color.yellow;
        }

    }
    public Color getColor(){
        return this.color;
    }
}
