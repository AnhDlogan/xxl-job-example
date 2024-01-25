package executor.controller;

import executor.jobhandler.ScheduleJobService;
import executor.model.SceneOutPut;
import executor.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/schedule")
public class ScheduleJobEndPoint {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobEndPoint.class);
    private ScheduleJobService scheduleJobService;

    @Autowired
    public void setScheduleJobService(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    @PostMapping(value = "/scene")
    public Integer uploadMetadata(@RequestBody SceneOutPut sceneOutPut) {
        logger.info("Request upload metadata, params {}", ObjectMapperUtil.toJsonString(sceneOutPut));
        return scheduleJobService.addAndStartScheduleJob(sceneOutPut);
    }

}
