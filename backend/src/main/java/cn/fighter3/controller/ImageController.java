package cn.fighter3.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {
    @GetMapping("/photo/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            // 构造资源对象，这里假设图片文件在类路径下的 "static/photo/" 目录中
            Resource resource = new ClassPathResource("static/photo/" + filename);

            // 读取资源的字节数组
            byte[] data = new byte[(int) resource.contentLength()];
            resource.getInputStream().read(data);

            // 设置响应的内容类型为图片类型（根据实际情况调整）
            MediaType mediaType = MediaType.IMAGE_JPEG; // JPEG 图片
            if (filename.endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG; // PNG 图片
            }

            // 创建 ResponseEntity 返回图片数据
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}