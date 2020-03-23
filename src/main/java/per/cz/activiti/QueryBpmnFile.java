package per.cz.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * 需求
 * 1.从Activiti的act_ge_bytearray表中读取两个文件
 * 2.将两个资源文件保存到路径：G://目录下
 *
 * 应用场景：用户想查看请假流程具体有哪些步骤要走？
 *
 * Created by Administrator on 2020/3/23.
 */
public class QueryBpmnFile {

    public static void main(String[] args) throws IOException {
        //1.得到ProcessEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService对象
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();

        //3.得到查询器：ProcessDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4.设置查询条件
        processDefinitionQuery.processDefinitionKey("myholiday");

        //5.执行查询操作，查询出想要的流程定义
        ProcessDefinition definition = processDefinitionQuery.singleResult();

        //6.通过流程定义信息，得到部署id
        String deploymentId = definition.getDeploymentId();

        //7.通过repositoryService的方法，实现读取图片信息及bpmn文件信息（输入流）
        //getResourceAsStream 第一参数 部署id，第二参数 资源名称 图片资源名称
        InputStream pngIs = repositoryService.getResourceAsStream(deploymentId,
                definition.getDiagramResourceName());
        InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId,
                definition.getResourceName());
        //8.构建OutputStream流
        String pngPath = "F:\\ImagePath\\diagram" ;
        File pngFile = new File(pngPath);
        if(!pngFile .exists()) {
            pngFile.mkdirs();//创建目录
        }

        FileOutputStream pngOs = new FileOutputStream("F:\\ImagePath\\" + definition.getDiagramResourceName());
        FileOutputStream bpmnOs = new FileOutputStream("F:\\ImagePath\\" + definition.getResourceName());

        //9.输入流、输出流的转换
        IOUtils.copy(pngIs,pngOs);
        IOUtils.copy(bpmnIs,bpmnOs);

        //关闭流
        pngOs.close();
        bpmnOs.close();
        pngIs.close();
        bpmnIs.close();
    }
}
