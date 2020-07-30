package core;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class UserInteraction {

    public static int getUserAnswerToQuestion(String question, String[] acceptedAnswers) {
        ArrayList<Boolean> answersToQuestion = new ArrayList<>();

        int indexOfAcceptedAnswer = -1;

        while (true) {
            IO.out(question);
            String[] userAnswer = Utils.parseUserInput(IO.in());

            boolean userAnswerMatchedMoreThanOneAnswer = false;

            for (int i = 0; i < acceptedAnswers.length; i++) {
                for (String word : userAnswer) {
                    if (acceptedAnswers[i].toLowerCase().equals(word.toLowerCase())) {
                        if (indexOfAcceptedAnswer == -1) {
                            indexOfAcceptedAnswer = i;
                        } else {
                            userAnswerMatchedMoreThanOneAnswer = true;
                            break;
                        }
                    }
                }

                if (userAnswerMatchedMoreThanOneAnswer)
                    break;
            }

            if (userAnswerMatchedMoreThanOneAnswer) {
                indexOfAcceptedAnswer = -1;
                IO.out("Sorry, I'm confused. Can you try again?");
                continue;
            }

            if (indexOfAcceptedAnswer == -1) {
                IO.out("Sorry, that didn't seem to answer my question. Can you try again?");
                continue;
            }

            break;
        }

        return indexOfAcceptedAnswer;
    }
}
