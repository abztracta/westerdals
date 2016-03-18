package no.woact.pg4100.assignment2.ronesp13.server;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public enum QuizClientState {
    WELCOME,
    WELCOME_RESPONSE,
    WELCOME_RETRY,
    STARTED_QUIZ,
    RETRY_QUIZ,
    SHUTDOWN
}
