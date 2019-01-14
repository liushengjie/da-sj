/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.config 
 * @author: liushengjie   
 * @date: 2018年8月13日 下午5:01:46 
 */
package cn.bocom.other.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
 * @ClassName: ConfigClass 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月13日 下午5:01:46  
 */

@Configuration
public class IgniteConfig {
    @Bean
    public Ignite igniteInstance(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setIgniteInstanceName("clientNode");
        cfg.setClientMode(true);

        cfg.setPeerClassLoadingEnabled(true);
        
        cfg.setMetricsLogFrequency(0);
        
        Map userAttrs = new HashMap();
        userAttrs.put("cache.service.node", false);
        cfg.setUserAttributes(userAttrs);
        
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("192.168.124.242:47500..47501"));
        spi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(spi);

        return Ignition.start(cfg);
    }
    
}
