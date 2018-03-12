package Exceptions;

import sun.plugin2.message.Message;

public class InvalidActionException extends Exception {

    public InvalidActionException(String message){
        super(message);
    }
}
