package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 *  处理当前用户的任务
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class ActivitiCompleteTask {

//    public static void main(String[] args) {
//        //1.得到processEngine对象
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        //2.得到TaskService对象
//        TaskService taskService = processEngine.getTaskService();
//        //3.处理任务，结合当前用户任务列表的查询操作 任务id=7505
////        taskService.complete("7505");//zhangsan 任务id
//        taskService.complete("10002");//lisi 任务id
//
//    }


    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //3.处理任务，结合当前用户任务列表的查询操作 任务id=7505
//        taskService.complete("7505");//zhangsan 任务id

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myholiday")
                .taskAssignee("wangwu") //,任务id=10002
                .singleResult();//查询唯一的一个任务
        taskService.complete(task.getId());//lisi 任务id

    }
}
