package model;

public class ModelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    ModelException(){
        super();
    }

    ModelException(String message, Throwable exception){
        super(message, exception);
    }

    ModelException(String message){
        super(message);
    }

    ModelException(Throwable exception){
        super(exception);
    }
}
