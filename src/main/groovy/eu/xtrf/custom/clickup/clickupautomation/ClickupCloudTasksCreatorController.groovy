package eu.xtrf.custom.clickup.clickupautomation

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.tasks.v2.CloudTasksClient
import com.google.cloud.tasks.v2.QueueName
import com.google.cloud.tasks.v2.Task
import eu.xtrf.custom.clickup.clickupautomation.config.UrlConfigurationProperties
import eu.xtrf.custom.clickup.clickupautomation.util.HttpTaskCreator
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Body
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/clickup-cloud-tasks")
class ClickupCloudTasksCreatorController {
    private final static Logger LOGGER = LoggerFactory.getLogger(this.simpleName)

    private final CloudTasksClient client = CloudTasksClient.create()
    private final ObjectMapper objectMapper = new ObjectMapper()

    @Inject
    private HttpTaskCreator httpTaskCreator

    @Inject
    private UrlConfigurationProperties urlConfigurationProperties

    @Value('${gcpProjectId}')
    private String gcpProjectId

    @Value('${gcpLocationId}')
    private String gcpLocationId

    @Value('${gcpQueueId}')
    private String gcpQueueId

    @Post("/task/specification")
    HttpResponse handleTaskCreatedInSpecificationsList(@Body Object body) {
        String queuePath = getFullQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskSpecificationCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Specifications list has been created")
        return HttpResponse.ok()
    }

    @Post("/task/task")
    HttpResponse handleTaskCreatedInTasksList(@Body Object body) {
        String queuePath = getFullQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskTaskCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Tasks list has been created")
        return HttpResponse.ok()
    }

    @Post("/task/bug")
    HttpResponse handleTaskCreatedInBugsList(@Body Object body) {
        String queuePath = getFullQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskBugCreated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to Bugs list has been created")
        return HttpResponse.ok()
    }

    @Post("/task/updated")
    HttpResponse handleTaskUpdated(@Body Object body) {
        String queuePath = getFullQueueName()
        String bodyAsString = objectMapper.writeValueAsString(body)
        String url = urlConfigurationProperties.url + urlConfigurationProperties.clickupTaskUpdated
        Task task = client.createTask(queuePath, httpTaskCreator.createHttpTask(url, bodyAsString))
        LOGGER.info("Task ${task.name} to update CU task has been created")
        return HttpResponse.ok()
    }

    private String getFullQueueName() {
        QueueName.of(gcpProjectId, gcpLocationId, gcpQueueId).toString()
    }
}
