package dk.sdu.sesp.geight.bulletsystem;

public class PreviousShot {
    private boolean shot;
    private int[] x;
    private int[] y;

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

}
