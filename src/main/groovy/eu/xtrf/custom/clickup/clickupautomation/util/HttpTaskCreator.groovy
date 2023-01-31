package eu.xtrf.custom.clickup.clickupautomation.util

import com.google.cloud.tasks.v2.HttpMethod
import com.google.cloud.tasks.v2.HttpRequest
import com.google.cloud.tasks.v2.Task
import com.google.protobuf.ByteString
import jakarta.inject.Singleton

import java.nio.charset.Charset

@Singleton
class HttpTaskCreator {

    Task createHttpTask(String url, String payload) throws IOException {
        Task.Builder taskBuilder = Task.newBuilder()
        .setHttpRequest(
                HttpRequest.newBuilder()
                .setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
                .setUrl(url)
                .putHeaders("Content-Type", "application/json")
                .setHttpMethod(HttpMethod.POST)
                .build()
        )
        Task task = taskBuilder.build()
        return task
    }
}
