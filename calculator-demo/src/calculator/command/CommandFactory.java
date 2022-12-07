package calculator.command;

import calculator.enums.CalTypEnum;

import java.lang.reflect.Constructor;

public class CommandFactory {

    // 获取二目运算符command
    public static <T> AbstractCommand<T> getEMCommandInstance(String calType, Object... values) {
        if (values == null || values.length != 2) {
            throw new RuntimeException("参数不合法");
        }
        return getCommandInstance(calType, values);
    }

    // 获取三目运算符command
    public static <T> AbstractCommand<T> getSMCommandInstance(String calType, Object... values) {
        if (values == null || values.length != 3) {
            throw new RuntimeException("参数不合法");
        }
        return getCommandInstance(calType, values);
    }

    @SuppressWarnings("unchecked")
    private static <T> AbstractCommand<T> getCommandInstance(String calType, Object... values) {
        CalTypEnum.ConstructorBean constructorBean = CalTypEnum.getCommandClassByType(calType);
        Class<? extends AbstractCommand> clazz = constructorBean.getClazz();
        // 获取构造方法参数数组，对应new ConstructorBean(AddCommand.class, Integer.class, Integer.class)中的两个Integer.class
        Class<?>[] parameterTypes = constructorBean.getParameterTypes();
        try {
            // 这里是通过AddCommand类中的AddCommand(Integer first, Integer second)构造方法来创建实例对象
            Constructor constructor = (parameterTypes == null || parameterTypes.length == 0) ?
                    clazz.getConstructor() : clazz.getConstructor(parameterTypes);
            return (AbstractCommand<T>) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
