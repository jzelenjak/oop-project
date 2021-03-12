package nl.tudelft.oopp.livechat.controllers.scenecontrollers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import nl.tudelft.oopp.livechat.controllers.AlertController;
import nl.tudelft.oopp.livechat.controllers.NavigationController;
import nl.tudelft.oopp.livechat.data.Lecture;
import nl.tudelft.oopp.livechat.data.Question;
import nl.tudelft.oopp.livechat.data.QuestionCellUser;
import nl.tudelft.oopp.livechat.servercommunication.QuestionCommunication;

import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The type User chat page controller.
 */
public class UserChatSceneController implements Initializable {

    @FXML
    private TextField inputQuestionTextTextField;
    @FXML
    private ListView<Question> questionPaneListView;
    @FXML
    private Text lectureNameText;
    @FXML
    ObservableList<Question> observableList = FXCollections.observableArrayList();

    /**
     * method that runs when the scene is first initialized.
     * @param location location of current scene
     * @param resourceBundle resource bundle
     */
    public void initialize(URL location, ResourceBundle resourceBundle) {
        lectureNameText.setText(Lecture.getCurrentLecture().getName());
        lectureNameText.setTextAlignment(TextAlignment.CENTER);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
            ae -> fetchQuestions()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Fetch questions.
     */
    public void fetchQuestions() {

        List<Question> list = QuestionCommunication.fetchQuestions();
        if (list == null)
            return;

        observableList.setAll(list);
        questionPaneListView.setItems(observableList);

        questionPaneListView.setCellFactory(
                new Callback<ListView<Question>, ListCell<Question>>() {
                    @Override
            public ListCell<Question> call(ListView<Question> listView) {
                        return new QuestionCellUser();
                    }
                });
        //System.out.println(list.size());

        questionPaneListView.getItems().clear();
        questionPaneListView.getItems().addAll(list);
    }

    /**
     * Go back to main.
     *
     * @throws IOException the io exception
     */
    public void goBackToMain() throws IOException {

        //Navigating back to Main Page

        Alert alert = AlertController.createAlert(Alert.AlertType.CONFIRMATION,
                "Confirm your action", "Are you sure do you want to quit this lecture?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NavigationController.getCurrentController().goToMainScene();
        }
    }

    /**
     * Go to user manual.
     *
     * @throws IOException the io exception
     */
    public void goToUserManual() throws IOException {
        NavigationController.getCurrentController().goToUserManual();
    }

    /**
     * Go to settings.
     *
     * @throws IOException the io exception
     */
    public void goToSettings() throws IOException {
        NavigationController.getCurrentController().goToSettings();
    }

    /**
     * Send a question to the server.
     *
     * @param ae the enter button
     * @return Integer showing status of the action
     *      1- Everything is good
     *      -1 -Lecture has not been initialized
     *      -2/ -3 -Server error.
     */
    @FXML
    public int askQuestion(ActionEvent ae) {

        int ret = QuestionCommunication.askQuestion(inputQuestionTextTextField.getText());
        //inputQuestion.setText("");
        System.out.println(ae);

        System.out.println(ret);
        if (ret <= 0) {
            AlertController.alertError("ERROR",
                    "There was a problem with asking question!");
        }

        Question question = new Question(
                Lecture.getCurrentLecture().getUuid(), inputQuestionTextTextField.getText(), 0);

        //questionPaneListView.getItems().add(question.getText());

        inputQuestionTextTextField.clear();

        return (ret);
    }
}
