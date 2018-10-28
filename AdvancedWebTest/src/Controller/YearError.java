package Controller;

public enum YearError {
    error400(400),
    error404(404);

    private int error;

    YearError(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }
}