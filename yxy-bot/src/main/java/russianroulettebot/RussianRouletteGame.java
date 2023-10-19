package russianroulettebot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.EventListener;
import java.util.Random;

public class RussianRouletteGame extends ListenerAdapter implements EventListener {
    public static int[] chambers;
    private boolean gameStarted = false;

    private User gameStarter = null;

    public RussianRouletteGame() {
        chambers = new int[6];
        Random random = new Random();
        int next = random.nextInt(6);
        chambers[next] = 1;
    }

    public static int getChambers() {
        return chambers.length;
    }

    public void rotateChamberOne() {
        rotate(chambers, 1);
    }

    public void rotateChamber() {
        // Rotate the chamber by a random number of positions
        Random random = new Random();
        int spin = random.nextInt(6);
        rotate(chambers, spin);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(User user) {
        gameStarted = true;
        gameStarter = user;
    }

    public void endGame() {
        gameStarted = false;
    }

    public boolean isGameLost() {
        return chambers[0] == 1;
    }

    public User getGameStarter() {
        return gameStarter;
    }

    private static void rotate(int arr[], int spin) {
        int temp[] = new int[spin];
        for (int i = 0; i < spin; i++)
            temp[i] = arr[i];
        for (int i = spin; i < arr.length; i++) {
            arr[i - spin] = arr[i];
        }
        for (int i = 0; i < spin; i++) {
            arr[i + arr.length - spin] = temp[i];
        }
    }
}
