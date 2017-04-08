package dlsu.wirtec.tokhangapp.game;

/**
 * Created by lyssa on 08/04/2017.
 */

public interface GameStateListener {
    public void onPlayerDeath(int score);
    public void onPlayerSuccess(int score);
}
