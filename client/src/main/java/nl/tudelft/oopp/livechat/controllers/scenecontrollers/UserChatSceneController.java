package nl.tudelft.oopp.livechat.controllers.scenecontrollers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nl.tudelft.oopp.livechat.businesslogic.InputValidator;
import nl.tudelft.oopp.livechat.controllers.AlertController;
import nl.tudelft.oopp.livechat.controllers.NavigationController;
import nl.tudelft.oopp.livechat.businesslogic.QuestionManager;
import nl.tudelft.oopp.livechat.data.Lecture;

import nl.tudelft.oopp.livechat.data.PollAndOptions;
import nl.tudelft.oopp.livechat.data.Question;

import nl.tudelft.oopp.livechat.servercommunication.LectureSpeedCommunication;
import nl.tudelft.oopp.livechat.servercommunication.PollCommunication;
import nl.tudelft.oopp.livechat.uielements.QuestionCellUser;
import nl.tudelft.oopp.livechat.data.User;
import nl.tudelft.oopp.livechat.servercommunication.QuestionCommunication;

import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.*;


/**
 * Class for the UserChat Scene controller.
 */
public class UserChatSceneController implements Initializable {

    @FXML
    private TextArea questionInputTextArea;

    @FXML
    private ListView<Question> questionPaneListView;

    @FXML
    private Text lectureNameText;

    @FXML
    private Text userNameText;

    @FXML
    private CheckBox sortByVotesCheckBox;

    @FXML
    private CheckBox sortByTimeCheckBox;

    @FXML
    private CheckBox answeredCheckBox;

    @FXML
    private CheckBox unansweredCheckBox;

    @FXML
    private CheckBox voteOnLectureSpeedFast;

    @FXML
    private CheckBox voteOnLectureSpeedSlow;

    @FXML
    private Button participants;

    @FXML
    private Button goToSettingsButton;

    @FXML
    private Button goToUserManualButton;

    @FXML
    private Button gobBackButton;

    @FXML
    private Button leaveLecture;

    /**
     * The Observable list.
     */
    @FXML
    ObservableList<Question> observableList = FXCollections.observableArrayList();

    private List<Question> questions;

    private Timeline timelineFetch;

    private Thread fetchingThread;


    /**
     * Method that runs when the scene is first initialized.
     * @param location location of current scene
     * @param resourceBundle resource bundle
     */
    public void initialize(URL location, ResourceBundle resourceBundle) {
        lectureNameText.setText(Lecture.getCurrent().getName());
        userNameText.setText(User.getUserName());
        fetchVotes();

        getQuestions(true);
        timelineFetch = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            setQuestions();
            getVotesOnLectureSpeed();
            fetchVotes();
        }));
        timelineFetch.setCycleCount(Animation.INDEFINITE);
        timelineFetch.play();

        fetchingThread = new Thread(
            () -> {
                while (Lecture.getCurrent() != null) {
                    List<Question> list = QuestionCommunication.fetchQuestions(false);
                    if (list != null) {
                        Question.setCurrentList(list);
                    }
                }
            }
        );
        fetchingThread.setDaemon(true);
        fetchingThread.start();

        //Tooltip
        participants.setTooltip(new Tooltip("See the lecture participants"));
        goToSettingsButton.setTooltip(new Tooltip("Open Settings page"));

        goToUserManualButton.setTooltip(new Tooltip("Open Help & Documentation page"));
        gobBackButton.setTooltip(new Tooltip("Go back to the main page"));

        leaveLecture.setTooltip(new Tooltip("Leave this lecture"));
    }

    /**
     * Fetch questions.
     */
    public void setQuestions() {
        questions = Question.getCurrentList();
        questions = QuestionManager.filter(answeredCheckBox.isSelected(),
                unansweredCheckBox.isSelected(), questions);
        QuestionManager.sort(sortByVotesCheckBox.isSelected(),
                sortByTimeCheckBox.isSelected(), questions);

        observableList.setAll(questions);
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
        questionPaneListView.getItems().addAll(questions);
    }

    private void getQuestions(boolean firstTime) {
        List<Question> list = QuestionCommunication.fetchQuestions(firstTime);
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            System.out.println("There are no questions");
        }
        Question.setCurrentList(list);
    }

    /**
     * Go back to main.
     * @throws IOException the io exception
     */
    public void goBackToMain() throws IOException {

        //Navigating back to Main Page

        Alert alert = AlertController.createAlert(Alert.AlertType.CONFIRMATION,
                "Confirm your action", "Are you sure do you want to quit this lecture?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            timelineFetch.stop();
            fetchingThread.stop();
            fetchingThread = null;
            Question.setCurrentList(new ArrayList<>());
            NavigationController.getCurrent().goToMainScene();
            Lecture.setCurrent(null);
        }
    }

    /**
     * Go to user manual.
     * @throws IOException the io exception
     */
    public void goToUserManual() throws IOException {
        NavigationController.getCurrent().goToUserManual();
    }

    /**
     * Go to settings.
     * @throws IOException the io exception
     */
    public void goToSettings() throws IOException {
        NavigationController.getCurrent().goToSettings();
    }

    /**
     * Send a question to the server.
     * @return true if successful, false if not
     */
    public boolean askQuestion() {
        String text = questionInputTextArea.getText();
        if (text.length() == 0) {
            return false;
        }
        if (text.length() > 2000) {
            AlertController.alertWarning("Long question",
                    "Your question is too long! (max 2000 characters)");
            return false;
        }
        if (!InputValidator.checkCurseWords(text)) {
            AlertController.alertWarning("Curse language",
                    "Your text contains curse language and/or offensive words "
                            + "and will not be posted");
            return false;
        }
        boolean res = QuestionCommunication.askQuestion(
                User.getUid(), Lecture.getCurrent().getUuid(), text);
        //inputQuestion.setText("");

        if (!res) {
            System.out.println("not asked");
        }

        Question question = new Question(
                Lecture.getCurrent().getUuid(), questionInputTextArea.getText(), 0);

        //questionPaneListView.getItems().add(question.getText());

        questionInputTextArea.clear();

        setQuestions();
        return (false);
    }

    /**
     * Vote on lecture speed fast.
     *
     * @return true if successful, false if not
     */
    public boolean voteOnLectureSpeedFast() {
        voteOnLectureSpeedSlow.setSelected(false);

        return LectureSpeedCommunication.voteOnLectureSpeed(
                User.getUid(),
                Lecture.getCurrent().getUuid(),
                "faster");
    }

    /**
     * Vote on lecture speed slow.
     * @return 0 if everthing is fine -1 if not
     */
    public boolean voteOnLectureSpeedSlow() {
        voteOnLectureSpeedFast.setSelected(false);

        return LectureSpeedCommunication.voteOnLectureSpeed(User.getUid(),
                Lecture.getCurrent().getUuid(), "slower");
    }

    /**
     * Gets votes on lecture speed.
     */
    public void getVotesOnLectureSpeed() {
        UUID uuid = Lecture.getCurrent().getUuid();
        List<Integer> speeds = LectureSpeedCommunication.getVotesOnLectureSpeed(uuid);
        if (speeds != null && speeds.get(0).equals(0) && speeds.get(1).equals(0)) {
            voteOnLectureSpeedFast.setSelected(false);
            voteOnLectureSpeedSlow.setSelected(false);
        }
    }

    private void fetchVotes() {
        PollAndOptions fetched = (PollCommunication
                .fetchPollAndOptionsStudent(Lecture.getCurrent().getUuid()));
        if (fetched == null) {
            return;
        }

        //If new poll
        if (!fetched.equals(PollAndOptions.getCurrent())) {
            PollAndOptions.setCurrent(fetched);
            NavigationController.getCurrent().popupPollVoting();
            return;
        }

        //If poll is closed
        if (!fetched.getPoll().isOpen()
                && PollAndOptions.getCurrent().getPoll().isOpen()) {
            PollAndOptions.setCurrent(fetched);
            NavigationController.getCurrent().popupPollResult();
        }

        //If poll is reopened
        if (!PollAndOptions.getCurrent().getPoll().isOpen()
                && fetched.getPoll().isOpen()) {
            PollAndOptions.setCurrent(fetched);
            NavigationController.getCurrent().popupPollVoting();
        }

    }

}
