package Controller;

public enum Controllers {
    auth("auth"),
    courses("courses"),
    noController("");

    private String controller;

    Controllers(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return this.controller;
    }
}
