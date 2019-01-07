package Controller;

public enum Controllers {
    auth("auth"),
    courses("courses"),
    users("users"),
    cdl("cdl"),
    teachers("teachers"),
    noController("");

    private String controller;

    Controllers(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return this.controller;
    }
}
