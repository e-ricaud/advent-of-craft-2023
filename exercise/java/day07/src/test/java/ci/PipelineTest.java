package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;

import static ci.dependencies.Project.builder;
import static ci.dependencies.TestStatus.*;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PipelineTest {
    private final Config config = mock(Config.class);
    private final CapturingLogger log = new CapturingLogger();
    private final Emailer emailer = mock(Emailer.class);

    private Pipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new Pipeline(config, emailer, log);
    }

    @Test
    void project_with_tests_that_deploys_successfully_with_email_notification() {
        when(config.sendEmailSummary()).thenReturn(true);

        pipeline.run(project(p -> p.setTestStatus(PASSING_TESTS).setDeploysSuccessfully(true), true));
        
        assertLog("INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email");

        verify(emailer).send("Deployment completed successfully");
    }

    private Project project(
            Function<Project.ProjectBuilder, Project.ProjectBuilder> project,
            boolean shouldSendEmail) {
        when(config.sendEmailSummary()).thenReturn(shouldSendEmail);
        return project.apply(builder()).build();
    }

    private void assertLog(String... expectedLines) {
        assertThat(log.getLoggedLines())
                .isEqualTo(stream(expectedLines).toList());
    }

    @Test
    void project_with_tests_that_deploys_successfully_without_email_notification() {
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = builder()
                .setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }

    @Test
    void project_without_tests_that_deploys_successfully_with_email_notification() {
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = builder()
                .setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Sending email"
        ), log.getLoggedLines());

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void project_without_tests_that_deploys_successfully_without_email_notification() {
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = builder()
                .setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_that_fail_with_email_notification() {
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = builder()
                .setTestStatus(FAILING_TESTS)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "ERROR: Tests failed",
                "INFO: Sending email"
        ), log.getLoggedLines());

        verify(emailer).send("Tests failed");
    }

    @Test
    void project_with_tests_that_fail_without_email_notification() {
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = builder()
                .setTestStatus(FAILING_TESTS)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "ERROR: Tests failed",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_and_failing_build_with_email_notification() {
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = builder()
                .setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Sending email"
        ), log.getLoggedLines());

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_with_tests_and_failing_build_without_email_notification() {
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = builder()
                .setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }

    @Test
    void project_without_tests_and_failing_build_with_email_notification() {
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = builder()
                .setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Sending email"
        ), log.getLoggedLines());

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_without_tests_and_failing_build_without_email_notification() {
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = builder()
                .setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(false)
                .build();

        pipeline.run(project);

        assertEquals(Arrays.asList(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }
}