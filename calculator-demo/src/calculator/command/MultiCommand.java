package calculator.command;

import calculator.enums.CalTypEnum;

public class MultiCommand extends AbstractCommand<Integer> {

    private Integer first;

    private Integer second;

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public MultiCommand(Integer first, Integer second) {
        this.calType = CalTypEnum.MULTI.getCalType();
        this.first = first;
        this.second = second;
    }

    @Override
    public Integer calculate() {
        return first * second;
    }

    @Override
    public Integer undo() {
        return result / second;
    }
}
