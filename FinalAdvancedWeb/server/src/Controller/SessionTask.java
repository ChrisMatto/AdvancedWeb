package Controller;

import DataAccess.DataAccess;

import java.util.TimerTask;

public class SessionTask extends TimerTask {

    public void run() {
        DataAccess.deleteOldSessions();
    }
}
