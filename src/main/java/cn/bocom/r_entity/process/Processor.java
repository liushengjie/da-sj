package cn.bocom.r_entity.process;

public class Processor {
    public enum ProcessEnum {
        CONTENTPROC("content","内容等价","1","row"),
        DATEPROC("date","日期范围","2","row"),
        NOTNULLPROC("notnull","条件非空","3","row"),
        SPLITPROC("split","符号分割","4","col"),
        SUBSTRPROC("substr","截取分割","5","col");
        
        /** 处理器ID */
        private String id;
        /** 处理器名称 */
        private String name;
        /** 信息描述 */
        private String desc;
        /** 处理器类型（行处理，列处理） */
        private String procType;
        
        private ProcessEnum(String id, String name, String desc, String procType) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.procType = procType;
        }
        
        public static ProcessEnum match(String id, ProcessEnum defaultProcess) {
            for (ProcessEnum item : ProcessEnum.values()) {
                if (item.id == id) {
                    return item;
                }
            }
            return defaultProcess;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getProcType() {
            return procType;
        }

        public String getDesc() {
            return desc;
        }
        
    }
}
