package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    private final String SUCCESS = "success";

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        if (!isTestsPassed(project)) {
            sendEmail("Tests failed");
            return;
        }

        if (!isDeploySuccessful(project)) {
            sendEmail("Deployment failed");
            return;
        }

        sendEmail("Deployment completed successfully");

    }

    private void sendEmail(String text) {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled");
            return;
        }
        log.info("Sending email");
        emailer.send(text);
    }

    private boolean isDeploySuccessful(Project project) {
        if (!SUCCESS.equals(project.deploy())) {
            log.error("Deployment failed");
            return false;
        }
        log.info("Deployment successful");
        return true;
    }

    private boolean isTestsPassed(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return true;
        }
        if (!SUCCESS.equals(project.runTests())) {
            log.error("Tests failed");
            return false;
        }
        log.info("Tests passed");
        return true;
    }
}
