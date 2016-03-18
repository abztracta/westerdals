using UnityEngine;
using System.Collections;
using System;

public class Mothership : InvaderObject {

    public Vector2 leftStartPosition;
    public Vector2 rightStartPosition;
    public Vector2 motherShipVelocity;

    private bool isMovingRight;

	public override void Start () {
        isMovingRight = Convert.ToBoolean(UnityEngine.Random.Range(0, 2)); // randomly select which side the mothership will spawn
        if (isMovingRight) {
            velocity = motherShipVelocity;
            transform.position = leftStartPosition;
        } else {
            velocity = motherShipVelocity * -1;
            transform.position = rightStartPosition;
        }
        rightStartPosition.x += renderer.bounds.size.x;
        this.gameObject.renderer.enabled = true;
	}

    public override void OnCollisionEnter2D(Collision2D collision) {
        if (collision.collider.tag.Equals("Laser")) {
            OnDie();
            base.OnDie(base.points); // Call delegate-method to update score.
            Destroy(collision.collider.gameObject);
        }
    }

    public override void Update() {
        if (isMovingRight) {
            if (transform.position.x > rightStartPosition.x) {
                OnDie();
            }
        } else {
            if (transform.position.x < leftStartPosition.x) {
                OnDie();
            }
        }
    }

    protected override void OnDie() {
        Destroy(this.gameObject);
    }
}
