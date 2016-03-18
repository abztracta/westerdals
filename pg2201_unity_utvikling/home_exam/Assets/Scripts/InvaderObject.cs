using UnityEngine;
using System.Collections;

public abstract class InvaderObject : MonoBehaviour {

    public int points;

    protected Vector2 velocity;

    public delegate void ScoreListener(int score);
    public static event ScoreListener scoreListener;

    public abstract void Start();
    public abstract void Update();
    public abstract void OnCollisionEnter2D(Collision2D collision);
    protected abstract void OnDie();

    public virtual void FixedUpdate() {
        transform.rigidbody2D.velocity = velocity;
    }

    protected void OnDie(int score) {
        scoreListener(score);
    }
}
