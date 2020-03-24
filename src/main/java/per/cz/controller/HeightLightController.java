package per.cz.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;

import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import per.cz.entity.BpmsActivityTypeEnum;
import per.cz.util.UtilMisc;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2020/3/24
 */
@Slf4j
@Controller
public class HeightLightController {


    /**
     * 查看流程图
     */
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public HttpServletResponse image(HttpServletRequest request, HttpServletResponse response) throws IOException {


        InputStream stream = getResourceDiagramInputStream("2501");

        OutputStream out = response.getOutputStream();
        IOUtils.copy(stream,out);

        log.info("stream ={}",stream);
        return response;

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        HistoryService historyService = processEngine.getHistoryService();
//
//        //根据流程定义id来获取BpmnModel对象
//        String processDefinitionId="mytest:1:4";
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
//
//        String executionId="2501";
//        //设置需要标注高亮的节点
//        List<String> hightLightElements = runtimeService.getActiveActivityIds(executionId);
//        List<String> hightLightFlows = new ArrayList<>();
//        //得到已经走过的节点的id集合
//        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
//        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.processInstanceId(executionId).list();
//        for(HistoricActivityInstance hi:historicActivityInstanceList) {
//            String taskKey=hi.getActivityId();
//            hightLightElements.add(taskKey);
//        }
//        //已执行flow的集和
//
//        hightLightFlows.add("flow1");
//
//        //这个类在5.22.0往上的版本中才有
//        DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();
//        //绘制bpmnModel代表的流程的流程图
//        InputStream inputStream = diagramGenerator
//                .generateDiagram(
//                        bpmnModel,
//                        "png",
//                        hightLightElements,
//                        hightLightElements,
//                        "宋体",
//                        "宋体",
//                        null,
//                        1.0);
//
//        FileOutputStream output=new FileOutputStream(new File("d:/test.png"));
//        IOUtils.copy(inputStream, output);
//
//        output.close();
//        inputStream.close();
//        System.out.println("输出完成");
    }


    private List<String> getExecutedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 流转线ID集合
        List<String> flowIdList = new ArrayList<String>();
        // 全部活动实例
        List<FlowNode> historicFlowNodeList = new LinkedList<FlowNode>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstanceList = new LinkedList<HistoricActivityInstance>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            historicFlowNodeList.add((FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true));
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstanceList.add(historicActivityInstance);
            }
        }

        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        FlowNode currentFlowNode = null;
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstanceList) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlowList = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的
             * 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或包含网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最近的流转节点视为有效流转
             */
            FlowNode targetFlowNode = null;
            if (BpmsActivityTypeEnum.PARALLEL_GATEWAY.getType().equals(currentActivityInstance.getActivityType())
                    || BpmsActivityTypeEnum.INCLUSIVE_GATEWAY.getType().equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicFlowNodeList.contains(targetFlowNode)) {
                        flowIdList.add(sequenceFlow.getId());
                    }
                }
            } else {
                List<Map<String, String>> tempMapList = new LinkedList<Map<String,String>>();
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, String> toMap = UtilMisc.toMap("flowId", sequenceFlow.getId(),
                                    "activityStartTime", String.valueOf(historicActivityInstance.getStartTime().getTime()));
                            tempMapList.add(toMap);
                        }
                    }
                }

                // 遍历匹配的集合，取得开始时间最早的一个
                long earliestStamp = 0L;
                String flowId = null;
                for (Map<String, String> map : tempMapList) {
                    long activityStartTime = Long.valueOf(map.get("activityStartTime"));
                    if (earliestStamp == 0 || earliestStamp >= activityStartTime) {
                        earliestStamp = activityStartTime;
                        flowId = map.get("flowId");
                    }
                }
                flowIdList.add(flowId);
            }
        }
        return flowIdList;
    }


    public InputStream getResourceDiagramInputStream(String id) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        HistoryService historyService = processEngine.getHistoryService();


        try {
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();

            // 获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(id).orderByHistoricActivityInstanceId().asc().list();

            // 构造已执行的节点ID集合
            List<String> executedActivityIdList = new ArrayList<String>();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
            }

            // 获取bpmnModel
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            // 获取流程已发生流转的线ID集合
            List<String> flowIds = this.getExecutedFlows(bpmnModel, historicActivityInstanceList);

            // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
            DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();
            InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds,
                    "宋体", "微软雅黑", null, 2.0);

            FileOutputStream output=new FileOutputStream(new File("d:/test.png"));
            IOUtils.copy(imageStream, output);

            output.close();
            imageStream.close();
            return imageStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
