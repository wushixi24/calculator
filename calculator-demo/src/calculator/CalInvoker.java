package calculator;

import calculator.command.AbstractCommand;

public class CalInvoker {

    public <Result> Result invoke(AbstractCommand<Result> command) {
        return command.execute();
    }
}
