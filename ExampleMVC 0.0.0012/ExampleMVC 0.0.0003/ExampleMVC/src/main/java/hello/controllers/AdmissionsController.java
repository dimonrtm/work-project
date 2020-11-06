package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class AdmissionsController {
    @Autowired
    IAdmissionService admissionService;

    @GetMapping(value = "/admissions", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Admissions showAdmissions() {
        return admissionService.findAll();
    }

    @RequestMapping(value = "/admissions/addXmlFormatData", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addAdmissionXmlFormatData(@RequestParam("admission_id") String admissionId,
                                     @RequestParam("warehouse_id") String warehouseId,
                                     @RequestParam("admission_date") String admissionDate,
                                     @RequestParam("admission_time") String admissionTime) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date admDateUtil = sdf.parse(admissionDate);
        Date admDate = new Date(admDateUtil.getTime());
        Time admTime = Time.valueOf(admissionTime);
        Admision admission = new Admision(admissionId, warehouseId, admDate, admTime);
        admissionService.insertAdmission(admission);
        return new Result("Add Admission");
    }

    @RequestMapping(value = "/admissions/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addAdmission(@RequestParam("admission_id") String admissionId,
                        @RequestParam("warehouse_id") String warehouseId,
                        @RequestParam("admission_date") String admissionDate,
                        @RequestParam("admission_time") String admissionTime) throws IOException, ParseException {
        Date admDate = Date.valueOf(admissionDate);
        Time admTime = Time.valueOf(admissionTime);
        Admision admission = new Admision(admissionId, warehouseId, admDate, admTime);
        admissionService.insertAdmission(admission);
        return new Result("Add Admission");
    }

    @GetMapping(value = "/admissions/{admission_id}/{warehouse_id}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Admision showAdmissionById(@PathVariable("admission_id") String admissionId,
                               @PathVariable("warehouse_id") String warehouseId) throws IOException {
        AdmissionKey admissionKey = new AdmissionKey(admissionId, warehouseId);
        return admissionService.getAdmissionById(admissionKey);
    }

    @RequestMapping(value = "/admissions/{admission_id}/{warehouse_id}/XmlFormat", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateAdmissionXmlFormat(@PathVariable("admission_id") String admissionId,
                                    @PathVariable("warehouse_id") String warehouseId,
                                    @RequestParam("admission_date") String admissionDate,
                                    @RequestParam("admission_time") String admissionTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date admDateUtil = sdf.parse(admissionDate);
        Date admDate = new Date(admDateUtil.getTime());
        Time admTime = Time.valueOf(admissionTime);
        admissionService.updateAdmission(admissionId, warehouseId, admDate, admTime);
        return new Result("Admission update");
    }

    @RequestMapping(value = "/admissions/{admission_id}/{warehouse_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateAdmission(@PathVariable("admission_id") String admissionId,
                           @PathVariable("warehouse_id") String warehouseId,
                           @RequestParam("admission_date") String admissionDate,
                           @RequestParam("admission_time") String admissionTime) throws Exception {
        Date admDate = Date.valueOf(admissionDate);
        Time admTime = Time.valueOf(admissionTime);
        admissionService.updateAdmission(admissionId, warehouseId, admDate, admTime);
        return new Result("Admission update");
    }

    @RequestMapping(value = "/admissions/{admission_id}/{warehouse_id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result deleteAdmission(@PathVariable("admission_id") String admissionId,
                           @PathVariable("warehouse_id") String warehouseId) throws IOException {
        AdmissionKey admissionKey = new AdmissionKey(admissionId, warehouseId);
        admissionService.deleteById(admissionKey);
        return new Result("Admission Delete");
    }
}
