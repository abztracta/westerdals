using UnityEngine;
using System.Collections;

/**
 * This class keeps track of the amount of points
 * a player has accumulated through a game.
 * This class is listening to a InvaderObject event/delegate.
 */
public class ScoreManager : MonoBehaviour {

    public Font font;
    private static ScoreManager instance;
    private int score;

    private bool drawScoreLabel;

    public void Awake() {
        instance = this;
        InvaderObject.scoreListener += AddScore;
        drawScoreLabel = true;
    }

    /** This method draws the score on the top left of the screen
     * when the game is running
     */
    public void OnGUI() {
        if (drawScoreLabel) {
            GUI.skin.font = font;
            GUI.skin.label.fontSize = 20;
            GUI.Label(new Rect(50f, 2f, 1024f, 22f), "SCORE: " + score);
        }
    }

    private void AddScore(int score) {
        this.score += score;
    }

    public bool SaveScore() {
        if (score > PlayerPrefs.GetInt("score")) {
            PlayerPrefs.SetInt("score", score);
            return true;
        }
        return false;
    }

    public void OnDestroy() {
        // Debug.Log("Fjernet scoreListener");
        InvaderObject.scoreListener -= AddScore;
    }

    public static ScoreManager GetInstance() {
        return instance;
    }
}
