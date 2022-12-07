package calculator.command;

import calculator.enums.CalTypEnum;


public class DivCommand extends AbstractCommand<Integer> {

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

    public DivCommand(Integer first, Integer second) {
        if (second == 0) {
            throw new RuntimeException("除数不允许为0");
        }
        this.calType = CalTypEnum.DIV.getCalType();
        this.first = first;
        this.second = second;
    }

    @Override
    public Integer calculate() {
        return first / second;
    }

    /**
     * 除法这里由于逆运算可能损失精度，因此返回first也是一样的
     * @return
     */
    @Override
    public Integer undo() {
        return first;
    }
}
