package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 高亮
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class HeightLight {

    public static void main(String[] args) {

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//
//        List<Task> list = taskService
//                .createTaskQuery()
//                .processDefinitionKey("mytest")
//                .taskAssignee("zhangsan")
//                .list();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        for (Task t:list) {
//            log.info("流程实例id={}",t.getProcessInstanceId());
//
//            ProcessInstance processInstance = runtimeService
//                    .createProcessInstanceQuery()
//                    .processInstanceId(t.getProcessInstanceId())
//                    .singleResult();
//            //取出流程部署id
//            String definitionId = processInstance.getProcessDefinitionId();
//            ProcessDefinition processDefinition = repositoryService
//                    .createProcessDefinitionQuery()
//                    .processDefinitionId(definitionId)
//                    .singleResult();
//
//            log.info("流程部署id={}",processDefinition.getDeploymentId());
//
//        }


    }
}
