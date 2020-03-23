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
 * 删除流程定义信息
 *
 * 1.当我们正在执行的流程，没有完全审批结束，要删除流程定义信息就会失败
 * 2.如果要强制删除
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class DelProcessDefinition {

    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.执行删除流程定义 参数代表 流程部署id
        repositoryService.deleteDeployment("1");

        //设置true级联删除流程定义，即使该流程有流程实例启动也可以删除，设置false非级联删除方式
        repositoryService.deleteDeployment("1",true);


    }
}
