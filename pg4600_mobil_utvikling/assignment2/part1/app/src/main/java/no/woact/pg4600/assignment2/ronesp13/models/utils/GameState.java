package no.woact.pg4600.assignment2.ronesp13.models.utils;

import java.io.Serializable;

public enum GameState implements Serializable {
    INIT,
    COMPLETED_ROUND,
    REMEMBER,
    SELECT,
    STOPPED,
    GAME_COMPLETED
}
