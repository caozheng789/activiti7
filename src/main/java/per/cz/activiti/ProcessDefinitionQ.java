package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

/**
 *
 * 查询流程定义信息
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class ProcessDefinitionQ {

    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.得到ProcessDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //4.设置条件，并查询出当前的所有流程定义 查询条件：流程定义的key
        List<ProcessDefinition> myholiday = processDefinitionQuery.processDefinitionKey("myholiday")
                .orderByProcessDefinitionVersion().asc().list();

        //5.输出流程定义信息
        for (ProcessDefinition pd:myholiday) {
            log.info("流程定义id={},流程定义名称={}",pd.getId(),pd.getName());
            log.info("流程定义key={},流程定义版本={}",pd.getKey(),pd.getVersion());
            log.info("流程部署id={}",pd.getDeploymentId());
        }

    }
}
