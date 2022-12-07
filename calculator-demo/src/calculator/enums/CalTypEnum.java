package calculator.enums;

import calculator.command.*;

import java.util.HashMap;
import java.util.Map;

public enum CalTypEnum {

    ADD("add", "加法"),
    SUB("sub", "减法"),
    MULTI("multi", "乘法"),
    DIV("div", "除法");

    private String calType;

    private String desc;

    private static Map<String, ConstructorBean> MAP = new HashMap<>();

    static {
        MAP.put(ADD.getCalType(), new ConstructorBean(AddCommand.class, Integer.class, Integer.class));
        MAP.put(SUB.getCalType(), new ConstructorBean(SubCommand.class, Integer.class, Integer.class));
        MAP.put(MULTI.getCalType(), new ConstructorBean(MultiCommand.class, Integer.class, Integer.class));
        MAP.put(DIV.getCalType(), new ConstructorBean(DivCommand.class, Integer.class, Integer.class));
    }

    public static ConstructorBean getCommandClassByType(String calType) {
        return MAP.get(calType);
    }

    CalTypEnum(String calType, String desc) {
        this.calType = calType;
        this.desc = desc;
    }

    public String getCalType() {
        return calType;
    }

    public String getDesc() {
        return desc;
    }

    public static class ConstructorBean {
        // Class类
        private Class<? extends AbstractCommand> clazz;
        // 构造函数形参，用于通过反射创建AbstractCommand实例
        private Class<?>[] parameterTypes;

        public Class<? extends AbstractCommand> getClazz() {
            return clazz;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public ConstructorBean(Class<? extends AbstractCommand> clazz, Class<?>... parameterTypes) {
            this.clazz = clazz;
            this.parameterTypes = parameterTypes;
        }
    }

}
