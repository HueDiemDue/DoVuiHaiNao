package dovui.com.dovuihainao.models;

/**
 * Created by hue on 31/05/2017.
 */

public class Player {
    private String namePlayer, id;
    private int point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player(String namePlayer, int point) {
        this.id = java.util.UUID.randomUUID().toString();
        this.namePlayer = namePlayer;
        this.point = point;
    }


    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
