/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.config 
 * @author: liushengjie   
 * @date: 2018年8月13日 下午5:01:46 
 */
package cn.bocom.other.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/** 
 * @ClassName: ConfigClass 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月13日 下午5:01:46  
 */

@Configuration
@ImportResource(locations={"classpath:mybatis/spring-mybatis.xml"})
public class ConfigClass {
}
