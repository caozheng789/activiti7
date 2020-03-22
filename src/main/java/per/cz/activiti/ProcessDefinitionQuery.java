package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

/**
 *
 * 查询流程定义信息
 * Created by Administrator on 2020/3/22.
 */
@Slf4j
public class ProcessDefinitionQuery {

    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.创建RepositoryService对象

        //3.得到ProcessDefinitionQuery对象

        //4.设置条件，并查询出当前的所有流程定义

        //5.输出流程定义信息

    }
}
