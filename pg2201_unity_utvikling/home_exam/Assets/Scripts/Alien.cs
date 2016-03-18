using UnityEngine;
using System.Collections;

/**
 * This class represents an Alien.
 * It controls the movement of aliens by using a delegate
 * to notify all other alien objects when x bounds are reached.
 * It also controls the event where an Alien collides with
 * the Player, a Laser or x bounds.
 * An Alien can also fire a laser. This event is triggered 
 * by the AlienManager-script.
 */
public class Alien : InvaderObject {

    public Vector2 alienVelocity;
    public float yMovement;

    private Animator animator;

    private static bool isMovingLeft;
    private static bool isMovingRight;

    /**
     * This delegate/event is used to detect when an Alien collides
     * with one of the two walls.
     **/
    public delegate void WallCollisionListener(string tag);
    public static event WallCollisionListener wallCollisionListener;

    public override void Start() {
        animator = GetComponent<Animator>();
        Alien.wallCollisionListener += OnWallCollision;
        isMovingRight = true;
        isMovingLeft = false;
        velocity = alienVelocity;
        AlienManager.GetInstance().AddAlien(this);
    }

    public override void Update() {
        transform.rotation = Quaternion.identity;
    }

    public override void OnCollisionEnter2D(Collision2D collision) {
        if (collision.collider.tag.Equals("RightWall")) {
            if (isMovingRight) {
                wallCollisionListener(collision.collider.tag);
            }
        } else if (collision.collider.tag.Equals("LeftWall")) {
            if (isMovingLeft) {
                wallCollisionListener(collision.collider.tag);
            }
        }
        if (collision.collider.tag.Equals("Laser")) {
            Destroy(collision.collider.gameObject);
            AlienManager.GetInstance().OnAlienDeath();
            animator.SetBool("IsKilled", true);
            // OnDie() is called when the animation is finished playing
        }
        if (collision.collider.tag.Equals("Player")) {
            // Debug.Log("Alien collided with Player. Game Over.");
            GameManager.GetInstance().OnGameLost();
        }
    }

    private void OnWallCollision(string tag) {
        if (tag.Equals("RightWall")) {
            if (isMovingRight) {
                ChangeDirection();
                if (velocity.x > 0) {
                    ChangePositionAndVelocity();
                }
            } else if (isMovingLeft) {
                if (velocity.x > 0) {
                    ChangePositionAndVelocity();
                }
            }

        } else if (tag.Equals("LeftWall")) {
            if (isMovingRight) {
                if (velocity.x < 0) {
                    ChangePositionAndVelocity();
                }
            } else if (isMovingLeft) {
                ChangeDirection();
                if (velocity.x < 0) {
                    ChangePositionAndVelocity();
                }
            }
        }
    }

    private void ChangePositionAndVelocity() {
        velocity.x *= -1;
        transform.position = new Vector3(transform.position.x, transform.position.y + yMovement, 0);
    }

    private void ChangeDirection() {
        isMovingLeft = !isMovingLeft;
        isMovingRight = !isMovingRight;
    }

    /**
     * This method is called when the death-animation
     * of an Alien is completed.
     */
    protected override void OnDie() {
        AlienManager.GetInstance().RemoveAlien(this);
        base.OnDie(base.points);
        Destroy(this.gameObject);
        if (AlienManager.GetInstance().GetAlienCount() == 0) {
            GameManager.GetInstance().OnGameWon();
        }
    }

    public void OnFire() {
        RaycastHit2D raycast = Physics2D.Raycast(new Vector2(transform.position.x, transform.position.y - renderer.bounds.size.y), new Vector2(0, -1f)); // detect if another alien is in front
        if (raycast.collider == null || !raycast.collider.gameObject.tag.Equals("Alien")) {
            GameObject go = (GameObject)Instantiate(Resources.Load("Prefabs/Laser"), new Vector3(transform.position.x, transform.position.y - renderer.bounds.size.y, 0), Quaternion.identity);
            Laser laser = go.GetComponent<Laser>();
            laser.AlienFired();
        } else if (raycast.collider != null && raycast.collider.gameObject.tag.Equals("Alien")) {
            Alien alien = raycast.collider.gameObject.GetComponent<Alien>();
            alien.OnFire(); // assign the task of firing the laser to the Alien caught by the raycast.
        }
    }

    public void OnDestroy() {
        Alien.wallCollisionListener -= OnWallCollision;
    }
}
