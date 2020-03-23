package per.cz.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/24.
 */
@Slf4j
@Controller
public class TestController {



    @GetMapping("index")
    public String testIndex(Model model){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService
                .createTaskQuery()
                .processDefinitionKey("mytest")
                .taskAssignee("zhangsan")
                .list();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        for (Task t:list) {
            log.info("流程实例id={}",t.getProcessInstanceId());

            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(t.getProcessInstanceId())
                    .singleResult();
            //取出流程部署id
            String definitionId = processInstance.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionId(definitionId)
                    .singleResult();

            log.info("流程部署id={}",processDefinition.getDeploymentId());
            model.addAttribute("img","/diagram/holiday5.png");
            model.addAttribute("taskId",processDefinition.getDeploymentId());

            //查询任务坐标，用来显示高亮

//            ProcessDefinitionEntity entity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(definitionId);
//            entity.get

            return "index";
        }
        return null;
    }



}
