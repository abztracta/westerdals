package no.woact.pg4100.assignment2.ronesp13.server;

import no.woact.pg4100.assignment2.ronesp13.server.model.Book;
import no.woact.pg4100.assignment2.ronesp13.server.model.handler.BookHandler;

import static no.woact.pg4100.assignment2.ronesp13.server.QuizClientState.RETRY_QUIZ;
import static no.woact.pg4100.assignment2.ronesp13.server.QuizClientState.SHUTDOWN;
import static no.woact.pg4100.assignment2.ronesp13.server.QuizClientState.STARTED_QUIZ;
import static no.woact.pg4100.assignment2.ronesp13.server.QuizClientState.WELCOME;
import static no.woact.pg4100.assignment2.ronesp13.server.QuizClientState.WELCOME_RESPONSE;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class QuizProtocol {

    private QuizClientState state;
    private Book book;

    public QuizProtocol() {
        state = WELCOME;
    }

    public String handleInput(String input) {
        switch (state) {
            case WELCOME: return getWelcomeMessage();
            case WELCOME_RESPONSE: return respondToWelcomeMessage(input);
            case WELCOME_RETRY: return "";
            case STARTED_QUIZ: return evaluateAnswer(input);
            case RETRY_QUIZ: return retryQuiz(input);
            default: return "En feil har oppstått.";
        }
    }

    private String getWelcomeMessage() {
        state = WELCOME_RESPONSE;
        return "Vil du delta i forfatter-QUIZ? (ja/nei)";
    }

    private String respondToWelcomeMessage(String input) {
        switch (input) {
            case "nei":
                state = SHUTDOWN;
                return "Takk for at du deltok!";
            default:
                state = STARTED_QUIZ;
                return getQuestion();
        }
    }

    private void getBook() {
        BookHandler handler = new BookHandler();
        book = handler.getBook();
    }

    private String getQuestion() {
        getBook();
        return "Hvem har skrevet " + book.getTitle() + "?";
    }

    private String evaluateAnswer(String input) {
        state = RETRY_QUIZ;
        String output;
        if (input.equalsIgnoreCase(book.getAuthor())) {
            output = "Riktig!\n";
        } else {
            output = "Feil! Det er " + book.getAuthor() + ".\n";
        }
        return output + "Vil du fortsette? (ja/nei)";
    }

    private String retryQuiz(String input) {
        switch (input) {
            case "nei":
                state = SHUTDOWN;
                return "Takk for at du deltok!";
            default:
                state = STARTED_QUIZ;
                return getQuestion();
        }
    }
}
