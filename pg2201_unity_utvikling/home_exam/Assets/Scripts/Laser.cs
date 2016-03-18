using UnityEngine;
using System.Collections;

/**
 * This is a representation of a laser being fired from a Player
 * or an Alien object.
 */
public class Laser : MonoBehaviour {

    public float yMax;
    public float yMin;
    public Vector2 laserVelocity;

    private Vector2 velocity;

	public void Update () {
        transform.rotation = Quaternion.identity;
        if (transform.position.y > yMax || transform.position.y < yMin) {
            Destroy(this.gameObject);
            // Debug.Log("Destroyed lasershot");
        }
	}

    public void FixedUpdate() {
        transform.rigidbody2D.velocity = velocity;
    }

    public void AlienFired() {
        velocity = laserVelocity * -1;
    }

    public void PlayerFired() {
        velocity = laserVelocity;
    }
}
