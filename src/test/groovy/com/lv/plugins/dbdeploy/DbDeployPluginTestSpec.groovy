package com.lv.plugins.dbdeploy

import nebula.test.PluginProjectSpec
import org.gradle.api.Task

/**
 * @author Sion Williams
 */
class DbDeployPluginTestSpec extends PluginProjectSpec  {
    static final String PLUGIN_ID = 'com.lv.dbdeploy'

    @Override
    String getPluginName() {
        return PLUGIN_ID
    }

    def setup() {
        project.apply plugin: pluginName
    }

    def 'apply creates dbdeploy extension'() {
        expect: project.extensions.findByName( 'dbdeploy' )
    }

    def "apply creates dbScripts task"() {
        expect: project.tasks.findByName( 'dbScripts' )
    }

    def "apply creates update task"() {
        expect: project.tasks.findByName( 'update' )
    }

    def "apply creates changeScript task"() {
        expect: project.tasks.findByName( 'changeScript' )
    }

    def 'changeScript task has correct default values'() {
        setup:        
            Task task = project.tasks.findByName( 'changeScript' )

        expect:
            task.group == 'DbDeploy'
            task.description == 'Generate a new timestamped dbdeploy change script'
            task.scriptdirectory == new File('src/main/sql')
            task.driver == null
            task.url == null
            task.password == null
            task.userid == null
            task.changeLogTableName == 'changelog'
            task.encoding == null
            task.delimiter == null
            task.delimiterType == null
            task.lineEnding == null
            task.lastChangeToApply == null
            task.nameSuffix == "new_change_script"
    }

    def 'update task has correct default values'() {
        setup:        
            Task task = project.tasks.findByName( 'update' )

        expect:
            task.group == 'DbDeploy'
            task.description == 'Apply dbdeploy change scripts to the database.'
            task.scriptdirectory == new File('src/main/sql')
            task.driver == null
            task.url == null
            task.password == null
            task.userid == null
            task.changeLogTableName == 'changelog'
            task.encoding == null
            task.delimiter == null
            task.delimiterType == null
            task.lineEnding == null
            task.lastChangeToApply == null
    }

    def 'dbScripts task has correct default values'() {
        setup:        
            Task task = project.tasks.findByName( 'dbScripts' )

        expect:
            task.group == 'DbDeploy'
            task.description == 'Create the apply and undo scripts.'
            task.scriptdirectory == new File('src/main/sql')
            task.driver == null
            task.url == null
            task.password == null
            task.userid == null
            task.changeLogTableName == 'changelog'
            task.encoding == null
            task.delimiter == null
            task.delimiterType == null
            task.lineEnding == null
            task.lastChangeToApply == null
            task.outputfile == null
            task.dbms == null
            task.undoOutputfile == null
            task.templateDirectory == null
    }

    def 'tasks use correct values when extension is used'() {
        when:
            project.dbdeploy {
                scriptdirectory = new File('.')
                driver = 'org.hsqldb.jdbcDriver'
                url = 'jdbc:hsqldb:file:db/testdb;shutdown=true'
                password = ''
                userid = 'sa'
            }

        then:
            Task task = project.tasks.findByName( DbDeployPlugin.UPDATE_TASK_NAME )
            task.scriptdirectory == new File('.')
            task.driver == 'org.hsqldb.jdbcDriver'
            task.url == 'jdbc:hsqldb:file:db/testdb;shutdown=true'
            task.password == ''
            task.userid == 'sa'
    }
}
