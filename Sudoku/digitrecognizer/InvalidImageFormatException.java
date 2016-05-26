package digitrecognizer;

/** Exception thrown in case of invalid image format. */
public class InvalidImageFormatException extends Exception {
    public InvalidImageFormatException() {
        super("Invalid image format");
    }
}