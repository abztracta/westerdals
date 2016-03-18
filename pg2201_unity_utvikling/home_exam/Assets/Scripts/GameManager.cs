using UnityEngine;
using System.Collections;

public class GameManager : MonoBehaviour {

    private static GameManager instance;

    public enum GameState { MAIN_MENU, GAME_RUNNING, GAME_WON, GAME_LOST}
    private GameState state;
    public Font font;
    private bool isNewHighScore;

    /**
     * Create persistent GameManager Singleton
     */
    public void Start() {
        state = GameState.MAIN_MENU;
        if (instance != null && instance != this) {
            Destroy(this.gameObject);
            return;
        }
        instance = this;
        DontDestroyOnLoad(this.gameObject);
    }

    /** When a Player dies this method is called.
     * Checks if theres a new highscore before removing all
     * unneccesary GameObjects and changing GameState
     */
    public void OnGameLost() {
        if (ScoreManager.GetInstance().SaveScore()) {
            isNewHighScore = true;
        }
        RemoveAllGameObjects();
        state = GameState.GAME_LOST;
    }

    /** When a Player is victorious this method is called.
     * Checks if theres a new highscore before removing all
     * unneccesary GameObjects and changing GameState
     */
    public void OnGameWon() {
        if (ScoreManager.GetInstance().SaveScore()) {
          isNewHighScore = true;
        }
        RemoveAllGameObjects();
        state = GameState.GAME_WON;
    }

    private void RemoveAllGameObjects() {
        GameObject[] objects = (GameObject[])GameObject.FindObjectsOfType<GameObject>();
        foreach (GameObject go in objects) {
            if (go.tag.Equals("GameController") || go.tag.Equals("MainCamera")) {
                if (go.GetComponent<ScoreManager>() != null) {
                    go.GetComponent<ScoreManager>().enabled = false;   
                }
                continue;
            } else {
                Destroy(go);
            }
        }
    }

    /**
     * This method is responsible for determining which GUI
     * should be drawn. 
     * 
     * NOTE: The code associated with this method
     * should probably have been placed in another class.
     */
    public void OnGUI() {
        GUI.skin.font = font;
        switch (state) {
            case GameState.MAIN_MENU: DrawMainMenu(); break;
            case GameState.GAME_WON: DrawGameWon(); break;
            case GameState.GAME_LOST: DrawGameLost(); break;
        }
    }

    private void DrawMainMenu() {
        GUI.skin.label.fontSize = 72;
        Vector2 v = GUI.skin.label.CalcSize(new GUIContent("Space Invaders"));
        float x = (Screen.width / 2) - (v.x / 2);
        float y = (Screen.height / 2) - (v.y / 2) - 100;
        GUI.color = new Color(255, 0, 255);
        GUI.Label(new Rect(x,y, v.x, v.y), "Space Invaders");

        GUI.skin.label.fontSize = 32;
        GUI.Label(new Rect(375, 350, 500, 42), "Highscore: " + PlayerPrefs.GetInt("score"));

        if (GUI.Button(new Rect(350, 425, 100, 30), "PLAY")) {
            state = GameState.GAME_RUNNING;
            Application.LoadLevel(1);
        }
        if (GUI.Button(new Rect(500, 425, 100, 30), "QUIT")) {
            Application.Quit();
        }
    }

    private void DrawGameWon() {
        if (Input.GetButtonUp("Jump")) {
            isNewHighScore = false;
            state = GameState.MAIN_MENU;
            Application.LoadLevel(0);
        }
        GUI.skin.font = font;
        GUI.skin.label.fontSize = 48;
        GUI.color = new Color(255, 255, 0);
        Vector2 value = GUI.skin.label.CalcSize(new GUIContent("Victory!"));
        GUI.Label(new Rect((Screen.width / 2) - (value.x / 2), (Screen.height / 2) - (value.y / 2) - 100, value.x, value.y), "Victory!");
        GUI.color = new Color(255, 255, 255);
        if (isNewHighScore) {
            GUI.skin.label.fontSize = 20;
            Vector2 value1 = GUI.skin.label.CalcSize(new GUIContent("New highscore: " + PlayerPrefs.GetInt("score")));
            GUI.color = new Color(255, 255, 255);
            GUI.Label(new Rect((1024 / 2) - (value1.x / 2), (768 / 2) - (value1.y / 2) - 100 + 30, value1.x, value1.y), "New highscore: " + PlayerPrefs.GetInt("score"));
        }
        GUI.skin.label.fontSize = 12;
        
        Vector2 value2 = GUI.skin.label.CalcSize(new GUIContent("Click \"Space\" to continue"));
        GUI.Label(new Rect((Screen.width / 2) - (value2.x / 2), (Screen.height / 2) - (value2.y / 2) - 100 + 50 + 20, value2.x, value2.y), "Click \"Space\" to continue");
    }

    private void DrawGameLost() {
        if (Input.GetButtonUp("Jump")) {
            isNewHighScore = false;
            state = GameState.MAIN_MENU;
            Application.LoadLevel(0);
        }
        GUI.skin.font = font;
        GUI.skin.label.fontSize = 48;
        GUI.color = new Color(255, 0, 0);
        Vector2 value = GUI.skin.label.CalcSize(new GUIContent("Game Over"));
        GUI.Label(new Rect((Screen.width / 2) - (value.x / 2), (Screen.height / 2) - (value.y / 2) - 100, value.x, value.y), "Game Over");
        GUI.color = new Color(255, 255, 255);
        if (isNewHighScore) {
            GUI.skin.label.fontSize = 20;
            Vector2 value1 = GUI.skin.label.CalcSize(new GUIContent("New highscore: " + PlayerPrefs.GetInt("score")));
            GUI.color = new Color(255, 255, 255);
            GUI.Label(new Rect((1024 / 2) - (value1.x / 2), (768 / 2) - (value1.y / 2) - 100 + 30, value1.x, value1.y), "New highscore: " + PlayerPrefs.GetInt("score"));
        }
        GUI.skin.label.fontSize = 12;
        Vector2 value2 = GUI.skin.label.CalcSize(new GUIContent("Click \"Space\" to continue"));
        GUI.Label(new Rect((Screen.width / 2) - (value2.x / 2), (Screen.height / 2) - (value2.y / 2) - 100 + 50 + 20, value2.x, value2.y), "Click \"Space\" to continue");
    }

    public static GameManager GetInstance() {
        return instance;
    }
}
