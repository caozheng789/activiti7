package per.cz.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 *
 * @author Administrator
 * @date 2020/3/24
 */
@Slf4j
@Controller
public class TestController {

    @GetMapping("index")
    public String testIndex(Model model){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        Task t = taskService
                .createTaskQuery()
                .processDefinitionKey("mytest")
                .taskAssignee("zhangsan")
                .singleResult();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询历史任务
        HistoryService historyService = processEngine.getHistoryService();

        //查询任务坐标，用来显示高亮
        //获取节点信息
        BpmnModel bpmnModel = repositoryService.getBpmnModel(t.getProcessDefinitionId());
        if(bpmnModel != null) {
            Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
            }
        }
        //根据流程定义id 获取BpmnModel
        //获取当前任务坐标
        FlowElement flowElement = bpmnModel.getFlowElement(t.getTaskDefinitionKey());
        GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowElement.getId());
        log.info("graphicInfo.getX() = {}" , graphicInfo.getX());
        log.info("graphicInfo.getY() ={} " , graphicInfo.getY());
        log.info("graphicInfo.getHeight() = {}" , graphicInfo.getHeight());
        log.info("graphicInfo.getWidth() = {}" , graphicInfo.getWidth());
        log.info("流程实例id={}",t.getProcessInstanceId());
        log.info("流程定义id 任务中获取 ={}",t.getProcessDefinitionId());
        log.info("任务id={}",t.getTaskDefinitionKey());
        model.addAttribute("img","/diagram/holiday5.png");
        model.addAttribute("graphicInfo",graphicInfo);

        List<HistoricActivityInstance> historys = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(t.getProcessInstanceId())
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        for (HistoricActivityInstance hai:historys) {
            log.info("流程活动id{},活动名称{}",hai.getActivityId(),hai.getActivityName());
            log.info("流程定义id{},流程实例id{}",hai.getProcessDefinitionId(),hai.getProcessInstanceId());
        }

        return "index";
            
        
      
    }



}
