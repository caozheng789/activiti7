package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class GroupTest {
    //部署
    public static void main1(String[] args) {
        //1.ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.部署
        Deployment deploy = repositoryService
                .createDeployment()
                .addClasspathResource("diagram/holiday5.xml")
//                .addClasspathResource("diagram/holiday5.png")
//                .name("请假流程 - 用户组01")
                .deploy();

        log.info("部署流程名称={}",deploy.getName());
        log.info("部署流程id={}",deploy.getId());
        log.info("部署key={}",deploy.getKey());
    }

    //启动流程实例
    public static void main(String[] args) {
        //1
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance mytest = runtimeService.startProcessInstanceByKey("myholiday1");
        //
        log.info(mytest.getProcessInstanceId());
        log.info(mytest.getId());

    }

    //完成任务
    public static void main3(String[] args) {
        //1.
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService
                .createTaskQuery()
                .processDefinitionKey("mytest")
                .taskAssignee("zhangsan")
                .list();
        for (Task t:list) {
            taskService.complete(t.getId());
            log.info(t.getId(),t.getProcessInstanceId());
        }
    }

    //查询候选任务
    public static void main4(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //设置参数 流程定义的key 候选用户
        String userName = "tom";
        String key = "mytest";
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(key)
//                .taskCandidateUser(userName)//查询候选任务
                .taskAssignee(userName) //查询用户当前任务
                .list();

        for (Task t: list) {
            log.info(t.getId(),t.getProcessInstanceId());
        }
    }

    //接取任务
    public static void main6(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //第一个参数 任务id 第二个参数 拾取任务用户名
//        taskService.claim("5002","jack");//拾取任务
        //如果拾取任务之后，不想做了，想归还任务 只需要把第二个参数设置为null
//        taskService.claim("5002",null);
        taskService.complete("5002");//提交任务
        log.info("任务拾取成功！~~~~");
    }


}
