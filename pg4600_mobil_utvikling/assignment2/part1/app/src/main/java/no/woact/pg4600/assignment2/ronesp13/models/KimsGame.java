package no.woact.pg4600.assignment2.ronesp13.models;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import no.woact.pg4600.assignment2.ronesp13.db.DB;
import no.woact.pg4600.assignment2.ronesp13.db.HighscoreDAO;
import no.woact.pg4600.assignment2.ronesp13.db.NameDAO;
import no.woact.pg4600.assignment2.ronesp13.models.instance.KimsGameObject;
import no.woact.pg4600.assignment2.ronesp13.models.utils.GameState;

import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.COMPLETED_ROUND;
import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.GAME_COMPLETED;
import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.INIT;
import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.REMEMBER;
import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.SELECT;
import static no.woact.pg4600.assignment2.ronesp13.models.utils.GameState.STOPPED;

public class KimsGame implements Serializable {

    public static final int HIGHSCORE_LIST_SIZE = 7;
    public static final int MAX_ROUNDS = 20;
    private static final int MAX_VISIBLE_OBJECTS = 10;
    private static final int MAX_BUTTON_OBJECTS = 3;
    private static final int POINTS_FOR_CORRECT_ANSWER = 20;

    private GameState state;
    private Random random;
    private int score;
    private int roundNumber;
    private List<KimsGameObject> objectsToRemember;
    private List<KimsGameObject> objectsOnButtons;
    private KimsGameObject toBeRemembered;

    public KimsGame() {
        state = INIT;
        random = new Random();
    }

    public boolean isGameOver() {
        return state == STOPPED;
    }

    public boolean isCompletedRound() {
        return state == COMPLETED_ROUND;
    }

    public boolean isGameCompleted() {
        return state == GAME_COMPLETED;
    }

    public void initGame() {
        score = 0;
        roundNumber = 1;
        initialize();
        state = REMEMBER;
    }

    private void initialize() {
        List<KimsGameObject> elements = createKimsGameObjects();
        setObjectsToRememberList(elements);
        setObjectToBeRemembered();
        setObjectsOnButtonsList(elements);
    }

    public void selectActivity() {
        state = SELECT;
        objectsToRemember.remove(toBeRemembered);
        Collections.shuffle(objectsToRemember);
    }

    public void userSelected(int index) {
        KimsGameObject object = objectsOnButtons.get(index);
        if (object.isToBeRemembered()) {
            handleCorrectChoice();
        } else {
            handleWrongChoice();
        }
    }

    private void handleWrongChoice() {
        state = STOPPED;
    }

    private void handleCorrectChoice() {
        score += POINTS_FOR_CORRECT_ANSWER;
        if (roundNumber == MAX_ROUNDS) {
            state = GAME_COMPLETED;
            return;
        }
        state = COMPLETED_ROUND;
    }

    private void setObjectToBeRemembered() {
        toBeRemembered = objectsToRemember.get(random.nextInt(objectsToRemember.size()));
        toBeRemembered.setIsToBeRemembered(true);
    }

    public void setObjectsOnButtonsList(List<KimsGameObject> elements) {
        objectsOnButtons = new ArrayList<>();
        objectsOnButtons.add(toBeRemembered);
        while (objectsOnButtons.size() < MAX_BUTTON_OBJECTS) {
            objectsOnButtons.add(elements.remove(0));
        }
        Collections.shuffle(objectsOnButtons);
    }

    private void setObjectsToRememberList(List<KimsGameObject> elements) {
        Collections.shuffle(elements);
        objectsToRemember = new ArrayList<>();
        while (objectsToRemember.size() < MAX_VISIBLE_OBJECTS) {
            objectsToRemember.add(elements.remove(random.nextInt(elements.size())));
        }
    }

    public void startNewRound() {
        ++roundNumber;
        initialize();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public GameState getState() {
        return state;
    }

    public List<KimsGameObject> getObjectsToRemember() {
        return objectsToRemember;
    }

    public List<KimsGameObject> getObjectsOnButtons() {
        return objectsOnButtons;
    }

    public KimsGameObject getToBeRemembered() {
        return toBeRemembered;
    }

    private List<KimsGameObject> createKimsGameObjects() {
        List<KimsGameObject> list = new ArrayList<>();
        Cursor cursor = DB.nameDAO.getAll();
        while (cursor.moveToNext()) {
            list.add(new KimsGameObject(cursor.getString(cursor.getColumnIndex(NameDAO.NAME_ATTR))));
        }
        cursor.close();
        return list;
    }

    public boolean isNewHighscore() {
        return isHighscore(score);
    }

    private boolean isHighscore(int score) {
        if (score == 0) {
            return false;
        }
        Cursor cursor = DB.highscoreDAO.getAll();
        if (cursor.getCount() < HIGHSCORE_LIST_SIZE) {
            cursor.close();
            return true;
        } else {
            int iterations = 0;
            while (cursor.moveToNext()) {
                iterations++;
                int dbScore = cursor.getInt(cursor.getColumnIndex(HighscoreDAO.SCORE_ATTR));
                if (score > dbScore) {
                    cursor.close();
                    return true;
                }
                if (iterations > HIGHSCORE_LIST_SIZE) {
                    break;
                }
            }
            cursor.close();
            return false;
        }
    }
}
