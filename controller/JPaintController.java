package controller;

import controller.commands.*;
import controller.drawing.ListForShapes;
import model.interfaces.IApplicationState;
import view.EventName;
import view.interfaces.IUiModule;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JPaintController implements IJPaintController {
    private final IUiModule uiModule;
    private final IApplicationState applicationState;
    private final ListForShapes listForShapes;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState, ListForShapes listForShapes) {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
        this.listForShapes = listForShapes;
    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {
        uiModule.addEvent(EventName.CHOOSE_SHAPE, applicationState::setActiveShape);
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, applicationState::setActivePrimaryColor);
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, applicationState::setActiveSecondaryColor);
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, applicationState::setActiveShadingType);
        uiModule.addEvent(EventName.CHOOSE_MOUSE_MODE, applicationState::setActiveStartAndEndPointMode);
        uiModule.addEvent(EventName.UNDO, () -> new Undo().run());
        uiModule.addEvent(EventName.REDO, () -> new Redo().run());
        uiModule.addEvent(EventName.COPY, () -> new CopyCommand(listForShapes).run());
        uiModule.addEvent(EventName.PASTE, () -> new PasteCommand(listForShapes).run());
        uiModule.addEvent(EventName.DELETE, () -> new DeleteCommand(listForShapes).run());
        uiModule.addEvent(EventName.GROUP, () -> new GroupCommand(listForShapes).run());
        uiModule.addEvent(EventName.UNGROUP, () -> new UngroupCommand(listForShapes).run());

        uiModule.addEvent(EventName.TUTORIAL, () -> {
            JFrame tutorialFrame = new JFrame("Tutorial and Guide");
            JEditorPane tutorialEditorPane = new JEditorPane();
            tutorialEditorPane.setContentType("text/html");
            tutorialEditorPane.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(tutorialEditorPane);
            tutorialFrame.getContentPane().add(scrollPane);
            tutorialFrame.setSize(600, 400);

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("TUTORIAL.html");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String text = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                tutorialEditorPane.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
                tutorialEditorPane.setText("Failed to read the tutorial document.");
            }

            tutorialFrame.setVisible(true);
        });


    }

}