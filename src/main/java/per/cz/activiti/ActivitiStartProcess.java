package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 启动流程实例 :前提是部署了流程
 * Created by Administrator on 2020/3/22.
 * 发生改变的表
 * act_hi_actinst, 已完成的活动信息
 * act_hi_identitylink 参与者信息
 * act_hi_procinst 流程实例
 * act_hi_taskinst, 任务实例
 * act_ru_execution, 执行表
 * act_ru_identitylink, 参与者信息
 * act_ru_task  任务
 */
@Slf4j
public class ActivitiStartProcess {

    public static void main(String[] args) {
        //1.得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到RuntimeService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.创建流程实例 myholiday
        ProcessInstance myholiday = runtimeService.startProcessInstanceByKey("myholiday");
        //4.输出实例相关信息
        log.info("流程部署Id={},流程实例id = {}"
                ,myholiday.getDeploymentId()
                ,myholiday.getId()
                );

        log.info("活动id ={}",myholiday.getActivityId());

    }
}
