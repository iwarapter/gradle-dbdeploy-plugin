package com.lv.plugins.dbdeploy

import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult

/**
 * @author Sion Williams
 */
class DbDeployPluginIntegSpec extends IntegrationSpec {

    def setup() {
        directory('db')
        directory('src')
        copyResources('db', 'db')
        copyResources('src', 'src')
        copyResources('build.gradle', 'build.gradle')
    }

    def 'dbScripts produces output and undo scripts'() {
        when:
        runTasksSuccessfully('dbScripts')

        then:
        fileExists("build/dbdeploy/output.sql")
        fileExists("build/dbdeploy/undo.sql")
    }

    def 'update correctly applies the changes to the database'() {
        given:
        directory('build/dbdeploy')
        copyResources('dbdeploy', 'build/dbdeploy')

        when:
        runTasksSuccessfully('update')

        then:
        file('db/testdb.script').text.contains('INSERT INTO TEST VALUES(6,\'This is simple text\')')
    }
}
