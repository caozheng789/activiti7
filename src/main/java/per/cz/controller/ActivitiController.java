package per.cz.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import per.cz.entity.Deploy;
import per.cz.util.IOUtil;
import per.cz.util.JsonUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程部署
 *
 * @author Administrator
 * @date 2020/3/25
 */
@Slf4j
@Controller
public class ActivitiController {

    /**
     * 获取所有部署流程信息
     */
    @RequestMapping("getProcessDeploy")
    @ResponseBody
    public JsonUtil showProcess(Model model, String page, String limit){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.orderByProcessDefinitionVersion().asc().list();

        List<Deploy> deploys = new ArrayList<>();
        for (ProcessDefinition pd:list) {
            Deploy deploy = new Deploy();
            deploy.setId(pd.getId());
            deploy.setName(pd.getName());
            deploy.setKey(pd.getKey());
            deploy.setVersion(pd.getVersion());
            deploy.setResourceName(pd.getResourceName());
            deploy.setDiagramResourceName(pd.getDiagramResourceName());
            deploy.setDeploymentId(pd.getDeploymentId());
            deploys.add(deploy);
        }
        return JsonUtil.createBySuccess(deploys);
    }

    @GetMapping("showDeployment")
    public String showPage(){
        return "deployment/list";
    }

    @GetMapping("showTodo")
    public String showTodo(){
        return "todo/todo";
    }

    /**
     * 展示查看流程图页面
     * @param model
     * @param key
     * @return
     */
    @GetMapping("showBpmn")
    public String showBpmn(Model model, String key){
        model.addAttribute("key",key);
        return "deployment/flowChart";
    }

    /**
     * 获取流程图
     */
    @RequestMapping("getBpmnFile")
    public void getBpmnFile(String key,HttpServletResponse response){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .singleResult();

        //7.通过repositoryService的方法，实现读取图片信息及bpmn文件信息（输入流）
        //getResourceAsStream 第一参数 部署id，第二参数 资源名称 图片资源名称
        InputStream pngIs = repositoryService.getResourceAsStream(definition.getDeploymentId(),
                definition.getDiagramResourceName());

        // 将图像输出到Servlet输出流中。
        IOUtil.getBpmnToWeb(response, pngIs);

    }

    @GetMapping("delProcess")
    @ResponseBody
    public String delProcess(String deployId){
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //设置true级联删除流程定义，即使该流程有流程实例启动也可以删除，设置false非级联删除方式
        repositoryService.deleteDeployment(deployId,true);
        return "delete success ~";
    }


}
