package calculator.command;

public abstract class AbstractCommand<Result> {

    // 运算类型，如加、减、乘、除、平方、根号等
    protected String calType;

    // 运算结果，回写主要用于做undo操作
    protected Result result;

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result execute() {
        Result r = calculate();
        this.result = r;
        return r;
    }

    public abstract Result calculate();

    public abstract Result undo();
}
