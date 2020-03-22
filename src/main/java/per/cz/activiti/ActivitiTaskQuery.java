package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 *  当前用户的任务列表
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class ActivitiTaskQuery {

    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //3.根据流程定义的key 负责人assignee来实现当前用户的任务列表查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myholiday")
                .taskAssignee("lisi") //,任务id=10002
                .singleResult();//查询唯一的一个任务
//                .list();
        //4.任务列表展示

//        for (Task task :list) {
            log.info("流程实例id ={},任务id={}",task.getProcessInstanceId(),task.getId());
            log.info("负责人={}，任务名称={}",task.getAssignee(),task.getName());
//        }


    }
}
