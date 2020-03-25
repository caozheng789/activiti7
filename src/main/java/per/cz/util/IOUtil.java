package per.cz.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Administrator
 * @date 2020/3/25
 */
public class IOUtil {

    /**
     * 将图片流直接返回给前端显示
     * @param response
     * @param pngIs
     */
    public static void getBpmnToWeb(HttpServletResponse response, InputStream pngIs) {
        ServletOutputStream sos =null;
        try {
            BufferedImage image = ImageIO.read(pngIs);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            // 将图像输出到Servlet输出流中。
            sos = response.getOutputStream();
            ImageIO.write(image, "png", sos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
