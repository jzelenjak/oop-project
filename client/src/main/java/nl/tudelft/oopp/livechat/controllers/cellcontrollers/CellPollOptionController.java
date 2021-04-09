package nl.tudelft.oopp.livechat.controllers.cellcontrollers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.livechat.controllers.popupcontrollers.PollingManagementPopupController;
import nl.tudelft.oopp.livechat.data.PollOption;

import java.io.IOException;

public class CellPollOptionController {

    @FXML
    private TextField optionTextField;

    @FXML
    private CheckBox pollOptionCellIsCorrectCheckBox;

    @FXML
    private AnchorPane optionCellAnchorPane;

    @FXML
    private Button deleteButton;

    private PollOption option;

    /**
     * Instantiates a new Cell data poll option.
     */
    public CellPollOptionController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/cell/pollOptionCell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sets listener.
     */
    public void setListener() {
        optionTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                option.setOptionText(optionTextField.getText());
            }
        });
    }


    /**
     * Sets delete button.
     */
    public void setDeleteButton() {
        deleteButton.setOnAction((ActionEvent event) -> {
            PollingManagementPopupController.getInEditingOptions().remove(option);
        });
    }

    /**
     * Sets poll option cell is correct check box.
     */
    public void setPollOptionCellIsCorrectCheckBox() {
        pollOptionCellIsCorrectCheckBox.setSelected(option.isCorrect());
    }

    /**
     * Change poll option cell is correct check box.
     */
    public void changePollOptionCellIsCorrectCheckBox() {
        pollOptionCellIsCorrectCheckBox.setOnAction((ActionEvent event) -> {
            option.setCorrect(pollOptionCellIsCorrectCheckBox.isSelected());
        });
    }

    /**
     * Sets the question.
     * @param option the question
     */
    public void setPollOption(PollOption option) {
        this.option = option;
    }

    /**
     * Sets option text.
     * @param value the value
     */
    public void setOptionText(String value) {
        if (value != null) {
            optionTextField.setText(value);
        }
    }

    /**
     * Sets poll type.
     */
    public void setPollType() {
        if (PollingManagementPopupController.getAllCorrect()) {
            pollOptionCellIsCorrectCheckBox.setDisable(true);
            pollOptionCellIsCorrectCheckBox.setVisible(false);
        }
    }

    /**
     * Gets box.
     * @return the box
     */
    public AnchorPane getBox() {
        return optionCellAnchorPane;
    }
}