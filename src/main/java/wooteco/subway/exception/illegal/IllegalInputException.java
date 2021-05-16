package wooteco.subway.exception.illegal;

public class IllegalInputException extends IllegalMethodException {

    private static final String MESSAGE = "입력이 올바르지 않습니다.";

    public IllegalInputException() {
        super(MESSAGE);
    }
}