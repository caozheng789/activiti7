package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import per.cz.entity.Holiday;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程变量测试
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class VariableTest {
        //部署
//    public static void main(String[] args) {
//        //1.得到ProcessEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        //2.得到repositoryService
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        //3.部署
//        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("diagram/holiday4.bpmn")
//                .addClasspathResource("diagram/holiday04.png")
//                .name("请假流程-流程变量")
//                .deploy();
//        log.info("部署id={}",deploy.getId());
//        log.info("部署key={}",deploy.getKey());
//        log.info("部署name={}",deploy.getName());
//    }


    //启动
//    public static void main(String[] args) {
//        //1.得到ProcessEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        //2.得到repositoryService
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        //3.设置assignee取值 用户可以在界面上设置流程的执行人
//        Map<String,Object> map = new HashMap<>();
//        map.put("userName","jack");
//        map.put("departmentManager","tom");
//        map.put("generalManager","green");
//        map.put("personnelManager","joe");
//
//        //流程变量的值
//        map.put("num","1");
//        //4.启动流程实例，同时还要设置流程定义的assignee的值 流程变量的值
//        ProcessInstance holiday2 = runtimeService.startProcessInstanceByKey("holidayTest", map);
//
//        //输出
//        log.info("流程实例名={},流程定义id={}",holiday2.getName(),holiday2.getProcessDefinitionId());
//        log.info("流程id={},流程实例id={}",holiday2.getId(),holiday2.getProcessInstanceId());
//    }

    //查询待办任务
    public static void main(String[] args) {
        //1.得到ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到repositoryService
        TaskService taskService = processEngine.getTaskService();
        //3.查询当前账号的待办任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holidayTest")
                .taskAssignee("jack")
                .list();

        for (Task task:list) {
            log.info("流程实例id ={},任务id={}",task.getProcessInstanceId(),task.getId());
            log.info("负责人={}，流程实例id={}",task.getAssignee(),task.getProcessInstanceId());
            taskService.complete(task.getId());
            log.info("负责人 ={} ，提交任务={}",task.getAssignee(),task.getName());
        }


        //查询历史
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> historyList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(list.get(0).getProcessInstanceId())
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        for (HistoricActivityInstance instance: historyList) {
            log.info("流程活动id{},活动名称{}",instance.getActivityId(),instance.getActivityName());
            log.info("流程定义id{},流程实例id{}",instance.getProcessDefinitionId(),instance.getProcessInstanceId());
        }


    }
}
