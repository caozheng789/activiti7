package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import per.cz.entity.Holiday;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排他网关测试
 *
 * @author Administrator
 * @date 2020/3/26
 */
@Slf4j
public class ExclusiveGateWayTest01 {

    /**
     * 部署流程
     * 流程名称 = null，流程id=10001
     */
    @Test
    public void testDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("diagram/holiday6.xml")
                .addClasspathResource("diagram/holiday6.png")
                .name("网关流程部署01")
                .key("myTestA")
                .deploy();
        //4.输出部署信息
        log.info("流程名称 = {}，流程id={}",deploy.getName(),deploy.getId());
        log.info("流程KEY = {}，流程id={}",deploy.getKey(),deploy.getId());

    }

    /**
     * caozheng请假
     */
    @Test
    public void testStart(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.设置assignee取值 用户可以在界面上设置流程的执行人
        Map<String,Object> map = new HashMap<>();
        Holiday holiday = new Holiday();
        holiday.setUserName("joker");
        holiday.setNum(5F);
        map.put("holiday",holiday);
        ProcessInstance myTestA = runtimeService.startProcessInstanceByKey("myHoliday6", map);
        //5.输出
        log.info("流程名称={}",processEngine.getName());
    }

    /**
     * 查询任务，并完成
     */
    @Test
    public void testTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().
                processDefinitionKey("myHoliday6")
                .taskAssignee("zhaoliu")
                .list();
        for (Task task:list) {
            log.info("taskname ={}, 流程实例id={}" ,task.getName(),task.getProcessInstanceId());
            log.info("assignee = {}" ,task.getAssignee());
            taskService.complete(task.getId());
        }
    }

    /**
     * 查询历史记录
     */
    @Test
    public void testHistory(){
        HistoryService historyService = ProcessEngines.getDefaultProcessEngine().getHistoryService();

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId("2501")
                .orderByHistoricActivityInstanceStartTime()
                .asc().list();

        for (HistoricActivityInstance hai:list) {
            log.info("流程活动id{},活动名称{}",hai.getActivityId(),hai.getActivityName());
            log.info("流程定义id{},流程实例id{}",hai.getProcessDefinitionId(),hai.getProcessInstanceId());
        }
    }


}
