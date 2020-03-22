package per.cz.test;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 测试类
 * 作用：生成25张表
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class Activiti7Test {

    @Test
    public void testInitTable(){
        log.info("//1.创建ProcessEngineConfiguration对象");
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        log.info(" //2.创建processEngine对象");
        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
        log.info("/3.输出processEngine 对象 = {}",processEngine);
    }

    @Test
    public void testInitTableDefault(){
        //使用默认创建方式的约定条件；1.activiti.cfg.xml 2.bean中processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        log.info("/3.输出processEngine 对象 = {}",processEngine);
    }

}
