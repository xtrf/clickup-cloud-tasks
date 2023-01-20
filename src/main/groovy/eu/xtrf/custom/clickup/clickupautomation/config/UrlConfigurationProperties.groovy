package eu.xtrf.custom.clickup.clickupautomation.config

import io.micronaut.context.annotation.ConfigurationProperties

import javax.validation.constraints.NotBlank

@ConfigurationProperties("clickupAutomation")
class UrlConfigurationProperties {
    @NotBlank
    String url
    @NotBlank
    String clickupTaskBugCreated
    @NotBlank
    String clickupTaskTaskCreated
    @NotBlank
    String clickupTaskSpecificationCreated
    @NotBlank
    String clickupTaskUpdated
}
