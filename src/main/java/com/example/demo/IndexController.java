package com.example.demo;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class IndexController {

    @Etag
    @GetMapping("/")
    public String index() {
        return "hello world";
    }

    @Etag
    @GetMapping("/demo/{id}")
    public String demo(@PathVariable(name = "id") Long id) {
        return String.format("hello world demo %d", id);
    }

    @PostMapping(path = "/convert", produces = { MediaType.APPLICATION_PDF_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<byte []> convertJsonToPDF(@RequestBody String jsonBody) throws Exception {

        String json = jsonBody.replaceAll("\"", "\\\"");

        
        var jrxmlInputStream = new ClassPathResource("jasperreport/customers.jasper").getInputStream();
       
        var parameters = new HashMap<String, Object>();
        parameters.put("JSON_FILE", json);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jrxmlInputStream, parameters);

        var outputStream = new ByteArrayOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        byte [] pdf = outputStream.toByteArray();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).contentLength(pdf.length)
                .body(pdf);

    }

}
