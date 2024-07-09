package pt.isel.leic.svlc.util.results;

/**
 * This class represents a success value.
 * It includes a method for creating a success value
 * and a method for getting the success value.
 * */
public record Success<I>(I value) {

    public I value() {
        return value;
    }
}
