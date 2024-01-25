package executor.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import executor.model.SceneOutPut;
import executor.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JobHandlerService {
    private static Logger logger = LoggerFactory.getLogger(JobHandlerService.class);
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        logger.info("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            logger.info("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        // default success
    }

    @XxlJob("StartScene")
    public void startScene() throws Exception {
        String param = XxlJobHelper.getJobParam();
        XxlJobHelper.log("startPolicyHandle receive param : " + param);
        int exitValue = 1;
        SceneOutPut sceneOutPut = ObjectMapperUtil.objectMapper(param, SceneOutPut.class);
        try {
            logger.info("Start action {} {} of user {}", sceneOutPut.getAction(), sceneOutPut.getDeviceName(), sceneOutPut.getUserName());
        } catch (Exception e) {
            XxlJobHelper.log(e);
            exitValue = -1 ;
        }
        if (exitValue == 1) {
            XxlJobHelper.handleSuccess("startTimeHandle success");
        } else {
            XxlJobHelper.handleFail("startTimeHandle exit value(" + exitValue + ") is failed");
        }
    }
}
