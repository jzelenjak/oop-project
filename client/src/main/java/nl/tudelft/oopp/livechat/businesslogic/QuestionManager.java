package nl.tudelft.oopp.livechat.businesslogic;

import nl.tudelft.oopp.livechat.data.Lecture;
import nl.tudelft.oopp.livechat.data.Question;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class QuestionManager {

    private QuestionManager() {

    }

    /**
     * Sorts questions by time or number of votes according to the given arguments.
     * If both are selected, another algorithm will be used:
     *  "smaller" will be the question with smallest rank:
     *      the difference of the seconds since the start of the lecture and the number
     *      of votes times 42 minus 256 if the question is not answered
     * @param byVotes true iff needs to sort by votes
     * @param questions the list of questions to be sorted
     * @author Jegor Zelenjak
     */
    public static void sort(boolean byVotes, boolean byTime, List<Question> questions) {
        if (byVotes && !byTime) {
            questions.sort((q1, q2) -> {
                int result = Integer.compare(q2.getVotes(), q1.getVotes());
                if (result != 0) return result;
                return q2.getTime().compareTo(q1.getTime());
            });
        } else if (byVotes) {
            questions.sort((q1, q2) -> {
                System.out.println();
                int rank1 = (int) (Lecture.getCurrentLecture().getStartTime().getTime()
                        - q1.getTime().getTime()) / 1000 - (q1.getVotes() * 42);
                rank1 = !q1.isAnswered() ? rank1 - 256 : rank1;

                int rank2 = (int) (Lecture.getCurrentLecture().getStartTime().getTime()
                        - q2.getTime().getTime()) / 1000 - (q2.getVotes() * 42);
                rank2 = !q2.isAnswered() ? rank2 - 256 : rank2;

                return Integer.compare(rank1, rank2);
            });
        }  else {
            Collections.sort(questions);
        }
    }


    /**
     * Retains only the answered/unanswered questions according to the given arguments.
     * @param answered if true, retain answered questions
     * @param unanswered if true, retain unanswered questions
     * @param questions the list of questions to be filtered
     * @return the filtered list of questions
     */
    public static List<Question> filter(boolean answered, boolean unanswered,
                                        List<Question> questions) {
        //we don't need the answered questions, so we only retain the unanswered
        if (!answered) {
            questions =  questions.stream().filter((q)
                -> !q.isAnswered()).collect(Collectors.toList());
        }
        //we don't need the unanswered questions, so we only retain the answered ones
        //      (which will return the empty list if we don't need both)
        if (!unanswered) {
            return questions.stream().filter(Question::isAnswered).collect(Collectors.toList());
        }
        return questions;
    }
}
