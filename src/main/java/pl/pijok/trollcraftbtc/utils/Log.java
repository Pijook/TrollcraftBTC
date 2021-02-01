package pl.pijok.trollcraftbtc.utils;

public class Log {

    //String nickname, long time, String date, String serverName
    private String nickname;
    private long time;
    private String date;
    private String serverName;

    public Log(String nickname, long time, String date, String serverName){
        this.nickname = nickname;
        this.time = time;
        this.date = date;
        this.serverName = serverName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
