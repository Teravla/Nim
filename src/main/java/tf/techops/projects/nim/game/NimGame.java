package tf.techops.projects.nim.game;

public class NimGame {
    private int totalMatches;

    public NimGame(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getTotalMatches() {
        return totalMatches;
    }
    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public boolean removeMatches(int matches) {
        if (matches > 0 && matches <= 3 && matches <= totalMatches) {
            totalMatches -= matches;
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameOver() {
        return totalMatches <= 0;
    }
}