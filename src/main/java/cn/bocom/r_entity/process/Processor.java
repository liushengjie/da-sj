package cn.bocom.r_entity.process;

public class Processor {
    public enum ProcessEnum {
        CONTENTPROC("CONTENTPROC","内容等价","row"),
        DATEPROC("DATEPROC","日期范围","row"),
        NOTNULLPROC("NOTNULLPROC","条件非空","row"),
        SPLITPROC("SPLITPROC","符号分割","col"),
        SUBSTRPROC("SUBSTRPROC","截取分割","col");
        
        /** 处理器ID */
        private String id;
        /** 处理器名称 */
        private String name;
        /** 处理器类型（行处理，列处理） */
        private String procType;
        
        private ProcessEnum(String id, String name, String procType) {
            this.id = id;
            this.name = name;
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
    }
}
