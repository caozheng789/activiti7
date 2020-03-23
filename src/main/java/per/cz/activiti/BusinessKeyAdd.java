package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 启动流程实例，添加进businessKey
 * Created by Administrator on 2020/3/23.
 * act_ru_execution表中的businessKey的字段存入业务标识
 */
@Slf4j
public class BusinessKeyAdd {

    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到runtimeService
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3.启动流程实例，同时还要指定业务标识businessKey,它本身就是请假单的id
        //第一个参数 指定流程定义id
        //第二个参数 业务标识businessKey
        ProcessInstance myholiday = runtimeService.startProcessInstanceByKey("myholiday", "10001");

        //输出 ProcessInstance 相关实例
        log.info("businessKey={}",myholiday.getBusinessKey());

    }
}
