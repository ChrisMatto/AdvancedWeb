package Controller;

import DataAccess.DataAccess;

import javax.inject.Inject;
import java.util.TimerTask;

public class SessionTask extends TimerTask {

    @Inject
    private DataAccess dataAccess;

    public void run() {
        dataAccess.deleteOldSessions();
    }
}
