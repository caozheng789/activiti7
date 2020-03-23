package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;


/**
 * 流程部署
 * Created by Administrator on 2020/3/22.
 *
 * 流程部署后，那些表发生变化?
 *  act_re_deployment, 部署信息
 *  act_re_procdef, 流程定义的信息 比如 key
 *  act_ge_bytearray 流程定义的bpmn文件png文件
 */
@Slf4j
public class ActivitiDeployment {

    /**
     * 流程定义部署
     * @param args
     */
    public static void main(String[] args) {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deploy = repositoryService
                .createDeployment()
                .addClasspathResource("diagram/holiday02.bpmn")
                .addClasspathResource("diagram/diagram01.png")
                .name("请假流程03")
                .deploy();



        //4.输出部署信息
        log.info("流程名称 = {}，流程id={}",deploy.getName(),deploy.getId());
    }
}
