package view.gui;

import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.event.MouseAdapter;

public class PaintCanvas extends PaintCanvasBase {

    public Graphics2D getGraphics2D() {
        return (Graphics2D)getGraphics();
    }
    class MyMouseListener extends MouseAdapter {

    }
}
