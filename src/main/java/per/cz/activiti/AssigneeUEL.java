package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动流程实例，动态设置assignee
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class AssigneeUEL {

    public static void main(String[] args) {
        //1.得到processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.runtimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.设置assignee取值 用户可以在界面上设置流程的执行人
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0","zzzz");
        map.put("assignee1","ssss");
        map.put("assignee2","cccc");

        //4.启动流程实例，同时还要设置流程定义的assignee的值
        ProcessInstance holiday2 = runtimeService.startProcessInstanceByKey("mytest01", map);

        //5.输出
        log.info("流程名称={}",processEngine.getName());


    }
}
