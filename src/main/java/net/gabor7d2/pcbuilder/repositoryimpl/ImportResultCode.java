package net.gabor7d2.pcbuilder.repositoryimpl;

/**
 * Enum containing the different outcomes a profile import operation can return.
 */
public enum ImportResultCode {
    SUCCESS("Operation completed successfully!"),
    SOURCE_SAME_AS_DESTINATION("Source path is the same as destination path!"),
    DIRECTORY_DOESNT_EXIST("The specified directory doesn't exist!"),
    FILE_DOESNT_EXIST("The specified file doesn't exist!"),
    FILE_IN_JAR_DOESNT_EXIST("The specified file doesn't exist in the JAR file!"),
    DIRECTORY_ALREADY_EXISTS("A directory with the same name already exists!"),
    FILE_ALREADY_EXISTS("A file with the same name already exists!"),
    COULD_NOT_COPY_TO_TARGET("Could not copy items at source path to destination path!"),
    COULD_NOT_DELETE_DIRECTORY("Could not delete directory!"),
    COULD_NOT_DELETE_FILE("Could not delete file!"),
    COULD_NOT_EXTRACT_ZIP("Could not extract zip file!"),
    MALFORMED_URL("The specified URL is malformed and cannot be processed!"),
    COULD_NOT_DOWNLOAD_FILE("Could not download file from the specified url!"),
    NOT_A_DIRECTORY("The specified path doesn't point to a directory!"),
    NOT_A_ZIP_ARCHIVE("The specified item is not a zip archive!"),
    CANCELLED("The operation was cancelled!"),
    UNKNOWN("An unknown error happened!");

    private final String localizedMessage;

    ImportResultCode(String locMsg) {
        localizedMessage = locMsg;
    }

    public String getMessage() {
        return localizedMessage;
    }
}
