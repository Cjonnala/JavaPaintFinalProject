package controller.drawing;

import view.interfaces.IEventCallback;

public class Undo implements IEventCallback {
    @Override
    public void run() {
        CommandHistory.undo();
    }
}