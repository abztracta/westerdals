using UnityEngine;
using System.Collections;

public class Player : MonoBehaviour {

    private AudioSource audio;

    public float fireDelay;
    private float fireReadyAt;

    public void Start() {
        audio = GetComponent<AudioSource>();
    }

	public void Update () {
        if (Input.GetAxis("Horizontal") != 0) {
            // Debug.Log("Player moving");
            transform.position = new Vector3(transform.position.x + (Input.GetAxis("Horizontal") * Time.deltaTime), transform.position.y, 0);
        }
        if (Input.GetButtonUp("Fire1") && Time.time > fireReadyAt) {
            // Debug.Log("Player firing laser");
            OnFire();
        }
	}

    public void OnCollisionEnter2D(Collision2D collision) {
        if (collision.collider.gameObject.tag.Equals("Laser")) {
            // Debug.Log("Player hit by laser. Game over");
            GameManager.GetInstance().OnGameLost();
        }
    }

    private void OnFire() {
        // Debug.Log("Player firing laser");
        audio.Play();
        fireReadyAt = Time.time + fireDelay;
        GameObject go = (GameObject)Instantiate(Resources.Load("Prefabs/Laser"), new Vector2(transform.position.x, transform.position.y + renderer.bounds.size.y), Quaternion.identity);
        Laser laser = go.GetComponent<Laser>();
        laser.PlayerFired();
    }
}
