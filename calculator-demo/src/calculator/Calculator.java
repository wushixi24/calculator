package calculator;

import calculator.command.*;
import calculator.enums.CalTypEnum;

import java.util.Stack;

/**
 * 设计思路：
 * 1、这里用了命令模式来实现计算器与运算类型(如加减乘除)算法解耦，虽然这里的加减乘除运算比较简单，主要是考虑到后续的扩展，
 * 例如以后计算器需要新增三角函数，指数，对数等功能，通过命令模式可以很好实现扩展
 * 2、redo和undo的实现是通过两个栈来实现，executeStack栈主要是用来存放连续运算的Command对象，用于实现undo和redo，
 * redoStack栈主要是用来存放undo的Command对象，用于实现undo；当发生连续运算时，将Command对象放入executeStack栈，
 * 如果此时执行undo操作，每执行一次，将executeStack栈顶对象获取放入redoStack栈，并执行运算类型的逆运算，如加法对应的逆运算
 * 是减法，乘法对应的逆运算是除法等；如果此时执行redo操作，则反过来将redoStack栈顶对象获取放入executeStack栈，以此实现redo和undo
 * 3、为方便测试，这里通过使用Integer整形对象用于测试，构造除法时，注意会有精度问题
 */
public class Calculator {

    // 运算结果，初始化默认为0
    private Integer total = 0;

    private Stack<AbstractCommand<Integer>> executeStack = new Stack<>();

    private Stack<AbstractCommand<Integer>> redoStack = new Stack<>();

    private CalInvoker calInvoker = new CalInvoker();

    /**
     * 加法，当输入一个参数时，表示在total基础上叠加，当输入两个参数时，直接得出结果，不参与叠加，以下减法，乘法，除法也是一样的逻辑
     * @param param 参数
     * @return 返回的结果
     */
    public Integer add(Integer... param) {
        return emCal(CalTypEnum.ADD.getCalType(), param);
    }

    public Integer sub(Integer... param) {
        return emCal(CalTypEnum.SUB.getCalType(), param);
    }

    public Integer multi(Integer... param) {
        return emCal(CalTypEnum.MULTI.getCalType(), param);
    }

    public Integer div(Integer... param) {
        return emCal(CalTypEnum.DIV.getCalType(), param);
    }

    /**
     * 二目运算通用方法
     * @param calType 运算类型，如加减乘除
     * @param param 参数
     * @return
     */
    public Integer emCal(String calType, Integer... param) {
        Object[] values = (param != null && param.length == 1) ? (new Integer[] {total, param[0]}) : param;
        /**
         * 这里通过运算类型获取AbstractCommand实现类Class对象，通过反射获取AbstractCommand实例，以后每扩展一种运算，如需要增加
         * “求平方”的运算，调用端可以不做任何修改，只需在CalTypEnum枚举类映射“求平方”这种运算类型的对应关系即可，提高扩展性
         */
        AbstractCommand<Integer> command = CommandFactory.getEMCommandInstance(calType, values);
        if (param.length == 1) {
            executeStack.push(command);
        } else {
            // 当输入的是两个参数时，不可执行redo，不可执行undo
            reset();
        }
        total = calInvoker.invoke(command);
        return total;
    }

    public Integer undo() {
        if (executeStack.size() == 0) {
            System.out.println("----------------------不符合undo规则，无法undo，抛出异常-------------------------");
            throw new RuntimeException("不符合undo规则，无法undo");
        }
        AbstractCommand<Integer> executeCommmand = executeStack.pop();
        redoStack.push(executeCommmand);
        total = executeCommmand.undo();
        return total;
    }

    public Integer redo() {
        if (redoStack.size() == 0) {
            System.out.println("----------------------不符合redo规则，无法redo，抛出异常-------------------------");
            throw new RuntimeException("不符合redo规则，无法redo，抛出异常");
        }
        AbstractCommand<Integer> redoCommmand = redoStack.pop();
        executeStack.push(redoCommmand);
        total = redoCommmand.execute();
        return total;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // 2-1=1
        System.out.println("2-1结果：" + calculator.sub(2, 1));
        // 1+3=4
        System.out.println("以上结果加3：" + calculator.add(3));
        // 4*2=8
        System.out.println("以上结果乘以2：" + calculator.multi(2));
        // 8/4=2
        System.out.println("以上结果除以4：" + calculator.div(4));
        // undo则将上面的除以4逆运算，也就是乘以4，即2*4=8
        System.out.println("以上结果执行undo：" + calculator.undo());
        // 逆运算8/2=4
        System.out.println("以上结果执行undo：" + calculator.undo());
        // 4*2=8
        System.out.println("以上结果执行redo：" + calculator.redo());
        // 8/4=2
        System.out.println("以上结果执行redo：" + calculator.redo());

        // 10-6=4
        System.out.println("10-6结果：" + calculator.sub(10, 6));
        // 输入两个参数之后，无法执行undo和redo，这里测试不符合undo规则，抛出异常
        System.out.println("以上结果执行undo：" + calculator.undo());
    }

    public void reset() {
        total = 0;
        executeStack = new Stack<>();
        redoStack = new Stack<>();
    }

}
