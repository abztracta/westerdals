using UnityEngine;
using System.Collections;

/**
 * This class handles Alien attacks and spawning of Motherships.
 */
public class AlienManager : MonoBehaviour {

    public float motherShipInterval;
    public Vector2 alienAttackInterval;
    
    private static AlienManager instance;
    private ArrayList aliens;

    private AudioSource audio;

    public void Awake() {
        aliens = new ArrayList();
	    audio = GetComponent<AudioSource>();
        instance = this;
        StartCoroutine(DoAlienAttack());
        StartCoroutine(SpawnMothership());
	}

    /**
     * Waits a random amount of seconds before
     * selecting a random alien who should
     * fire a laser.
     */
    private IEnumerator DoAlienAttack() {
        while (true) {
            yield return new WaitForSeconds(FireInXSeconds());
            int index = Random.Range(0, aliens.Count);
            Alien alien = (Alien)aliens[index];
            alien.OnFire();
        }
    }

    /**
     * Spawns a MotherShip after a determined amount of seconds.
     */
    private IEnumerator SpawnMothership() {
        while (true) {
            yield return new WaitForSeconds(motherShipInterval);
            GameObject go = (GameObject)Instantiate(Resources.Load("Prefabs/Mothership"), Vector2.zero, Quaternion.identity);
            go.renderer.enabled = false;
        }
    }

    private float FireInXSeconds() {
        return Random.Range(alienAttackInterval.x, alienAttackInterval.y);
    }

    public void AddAlien(Alien alien) {
        aliens.Add(alien);
    }

    public void RemoveAlien(Alien alien) {
        aliens.Remove(alien);
    }

    public int GetAlienCount() {
        return aliens.Count;
    }

    public void OnAlienDeath() {
        audio.Play();
    }

    public static AlienManager GetInstance() {
        return instance;
    }
}
