package Controller;

import DataAccess.DataAccess;

import java.util.TimerTask;

public class SessionTask extends TimerTask {

    private DataAccess dataAccess = new DataAccess();

    public void run() {
        dataAccess.deleteOldSessions();
    }
}
