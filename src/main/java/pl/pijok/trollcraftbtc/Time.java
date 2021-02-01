package pl.pijok.trollcraftbtc;

public class Time {

    private String nickname;
    private long time;

    public Time(String nickname, long time){
        this.nickname = nickname;
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
