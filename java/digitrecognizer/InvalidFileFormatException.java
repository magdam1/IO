package digitrecognizer;

/** Exception thrown in case of invalid file format. */
public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException() {
        super("Invalid file format");
    }
}
