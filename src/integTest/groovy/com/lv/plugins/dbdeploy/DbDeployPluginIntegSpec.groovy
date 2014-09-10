package com.lv.plugins.dbdeploy

import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult

/**
 * @author Sion Williams
 */
class DbDeployPluginIntegSpec extends IntegrationSpec   {
    def 'runs build'() {
        when:
        ExecutionResult result = runTasks('dependencies')

        then:
        result.failure == null
    }

    def 'setup new build and check tasks are available'() {
        buildFile << '''
            apply plugin: 'com.lv.dbdeploy'
        '''.stripIndent()

        when:
        ExecutionResult result = runTasksSuccessfully('tasks')

        then:
        result.standardOutput.contains('changeScript - Generate a new timestamped dbdeploy change script')
        result.standardOutput.contains('dbScripts - Create the apply and undo scripts.')
        result.standardOutput.contains('update - Apply dbdeploy change scripts to the database.')
    }
}
