package executor.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import executor.model.SceneOutPut;
import executor.model.XxlJobInfo;
import executor.util.ObjectMapperUtil;
import executor.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleJobService {

    @Value("${xxl.job.admin.api}")
    private String baseUri ;
    private final static String JOB_INFO_API_URI = "/api/jobinfo";
    @Value("${xxl.group.cron-id}")
    private Integer cronGroupId;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobService.class);
    public Integer addAndStartScheduleJob(SceneOutPut sceneOutPut) {
        XxlJobInfo jobInfo = new XxlJobInfo();
        jobInfo.setJobDesc(sceneOutPut.getSceneName());
        String cron;
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime = currentTime.plusMinutes(1);
        //cron = currentTime.getSecond() + " " + currentTime.getMinute() + " " + currentTime.getHour() + " " + currentTime.getDayOfMonth() + " " + currentTime.getMonthValue() + " ? " + currentTime.getYear();
        cron = "0 55 9 * * ? *";
        jobInfo.setScheduleConf(cron);
        jobInfo.setExecutorParam(ObjectMapperUtil.toJsonString(sceneOutPut));
        jobInfo.setExecutorHandler("StartScene"); // call jobHanldeService
        return addAndStartJob(jobInfo);  // scheduleId from xxl
    }

    public Integer addAndStartJob(XxlJobInfo jobInfo) {
        jobInfo.setJobGroup(cronGroupId);
        jobInfo.setAuthor("admin");
        jobInfo.setExecutorRouteStrategy("ROUND");
        jobInfo.setExecutorBlockStrategy(String.valueOf(ExecutorBlockStrategyEnum.SERIAL_EXECUTION));
        jobInfo.setGlueType(String.valueOf(GlueTypeEnum.BEAN));
        jobInfo.setScheduleType("CRON");
        jobInfo.setMisfireStrategy("FIRE_ONCE_NOW");
        String response = RequestUtils.sendPOSTRequest(baseUri + JOB_INFO_API_URI + "/add-start", ObjectMapperUtil.toJsonString(jobInfo), null);
        logger.info("【addAndStartJob】= {}", response);
        ReturnT returnT = ObjectMapperUtil.objectMapper(response, ReturnT.class);
        if (returnT.getCode() == ReturnT.SUCCESS_CODE) {
            String id = (String) returnT.getContent();
            return Integer.valueOf(id);
        } else {
            return 0;
        }
    }


}
