package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 全部流程实例挂起与激活
 *
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class SuspendProcessInstance {

    public static void main(String[] args) {
        //1.得到processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.查询实例
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myholiday")
                .singleResult();

        //4.得到当前流程定义的实例是否都为暂停
        boolean suspended = definition.isSuspended();
        String id = definition.getId();
        //5.判断
        if (suspended){
            //说明是暂停，就可以激活
            repositoryService.activateProcessDefinitionById(id,true,null);
            log.info("激活={}",id);
        }else{
            repositoryService.suspendProcessDefinitionById(id,true,null);
            log.info("挂起={}",id);
        }
    }

}
