package per.cz.entity;

import lombok.Data;

/**
 *
 * @author Administrator
 * @date 2020/3/25
 */
@Data
public class Deploy {

    private String id;

    private String category;

    private String name;

    private String key;

    private String description;

    private int version;

    private String resourceName;

    private String deploymentId;

    private String diagramResourceName;

}
