package per.cz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;

import java.util.List;

/**
 * 历史信息查询
 * Created by Administrator on 2020/3/23.
 */
@Slf4j
public class HistoryQuery {

    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.得到HistoryService
        HistoryService historyService = processEngine.getHistoryService();
        //3.得到HistoricActivityInstanceQuery
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService
                .createHistoricActivityInstanceQuery();
        //流程实例id
        historicActivityInstanceQuery.processInstanceId("5001");

        //4.执行查询
        List<HistoricActivityInstance> list = historicActivityInstanceQuery
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        for (HistoricActivityInstance hai:list) {
            log.info("流程活动id{},活动名称{}",hai.getActivityId(),hai.getActivityName());
            log.info("流程定义id{},流程实例id{}",hai.getProcessDefinitionId(),hai.getProcessInstanceId());
        }


    }

}
