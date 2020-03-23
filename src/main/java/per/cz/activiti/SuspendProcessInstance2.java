package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 单个流程实例挂起与激活
 *
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class SuspendProcessInstance2 {

    public static void main(String[] args) {
        //1.得到processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到repositoryService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.查询实例
        ProcessInstance myholiday = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey("myholiday")
                .singleResult();

        //4.得到当前流程定义的实例是否都为暂停
        boolean suspended = myholiday.isSuspended();
        String id = myholiday.getId();
        //5.判断
        if (suspended){
            //说明是暂停，就可以激活
            runtimeService.activateProcessInstanceById(id);
            log.info("激活={}",id);
        }else{
            runtimeService.suspendProcessInstanceById(id);
            log.info("挂起={}",id);
        }
    }

}
