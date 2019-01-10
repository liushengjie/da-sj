package cn.sj.common.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.services.ServiceConfiguration;

/** 
 * @ClassName: CacheServiceFilter 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月11日 上午11:53:08  
 */
public class CacheServiceFilter implements IgnitePredicate<ClusterNode> {
	
    public boolean apply(ClusterNode node) {
        Boolean dataNode = node.attribute("cache.service.node");

        return dataNode != null && dataNode;
    }
}
