package per.cz.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * 请假实体类
 * Created by Administrator on 2020/3/23.
 */
@Data
public class Holiday implements Serializable{

    private Integer id;

    /**
     * 请假人
     */
    private String userName;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 请假天数
     */
    private Float num;

    /**
     * 事由
     */
    private String reason;

    /**
     * 请假类型
     */
    private String type;

//    /**
//     * 部门经理
//     */
//    private String departmentManager;
//
//
//    /**
//     * 总经理
//     */
//    private String generalManager;
//
//    /**
//     * 人事经理
//     */
//    private String personnelManager;
//
//    /**
//     * 请假天数
//     */
//    private String num;
}
