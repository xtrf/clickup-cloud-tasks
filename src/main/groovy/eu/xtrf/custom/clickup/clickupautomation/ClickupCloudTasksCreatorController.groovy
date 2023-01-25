package eu.xtrf.custom.clickup.clickupautomation

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.tasks.v2.CloudTasksClient
import com.google.cloud.tasks.v2.QueueName
import com.google.cloud.tasks.v2.Task
import eu.xtrf.custom.clickup.clickupautomation.config.UrlConfigurationProperties
import eu.xtrf.custom.clickup.clickupautomation.util.HttpTaskCreator
import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Body
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/clickup-cloud-tasks")
class ClickupCloudTasksCreatorController {
    private final static Logger LOGGER = LoggerFactory.getLogger(this.simpleName)

    @Inject
    HttpTaskCreator httpTaskCreator

    @Inject
    UrlConfigurationProperties urlConfigurationProperties

    CloudTasksClient client = CloudTasksClient.create()
    ObjectMapper objectMapper = new ObjectMapper()

    @Value('${gcpProjectId}')
    String gcpProjectId

    @Value('${gcpLocationId}')
    String gcpLocationId

    @Value('${gcpQueueId}')
    String gcpQueueId

    @Post("/task/specification")
    void handleTaskCreatedInSpecificationsList(@Body Object body) {
        String queuePath = getFullyQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskSpecificationCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Specifications list has been created")
    }

    @Post("/task/task")
    void handleTaskCreatedInTasksList(@Body Object body) {
        String queuePath = getFullyQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskTaskCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Tasks list has been created")
    }

    @Post("/task/bug")
    void handleTaskCreatedInBugsList(@Body Object body) {
        String queuePath = getFullyQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskBugCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Bugs list has been created")
    }

    @Post("/task/updated")
    void handleTaskUpdated(@Body Object body) {
        String queuePath = getFullyQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskUpdated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to update CU task has been created")
    }

    private String getFullyQueueName() {
        QueueName.of(gcpProjectId, gcpLocationId, gcpQueueId).toString()
    }
}
