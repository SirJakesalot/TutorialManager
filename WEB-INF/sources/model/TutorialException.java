package tutorialdb_model;

public class TutorialException extends Exception {
    public TutorialException(String message) {
        super(message);
    }
    public TutorialException(String message, Throwable throwable) {
        super(message, throwable);
    }
}