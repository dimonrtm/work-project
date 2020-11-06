package hello.controllers;

import hello.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloadController {
    private static final String directory="C:/Test";
    private static final String defaultFileName="default.txt";
    @Autowired
    ServletContext servletContext;

    //http://localhost:8080/files?fileName=misc.xml
    @GetMapping("/files")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(defaultValue=defaultFileName) String fileName) throws IOException{
        MediaType mediaType= MediaTypeUtils.getMediaTypeFromFileName(this.servletContext,fileName);
        Path path= Paths.get(directory+"/"+fileName);
        byte [] data= Files.readAllBytes(path);
        ByteArrayResource resource=new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+path.getFileName().toString()).
                contentType(mediaType).
                contentLength(data.length).
                body(resource);
    }
}
