pipeline {
     agent any

     tools {
         jdk '1.8'
         gradle '7.4.2'
     }

     parameters {
          booleanParam(defaultValue: true, description: 'run smoke tests', name: 'smoke')
          booleanParam(defaultValue: false, description: 'run regression tests', name: 'regression')
     }

    stages {
         stage('ui trello smoke') {
             when {
                expression { return params.smoke }
             }
             steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean smoketests'
             }
         }
         stage('ui trello regression') {
              when {
                 expression { return params.regression }
              }
              steps {
                 sh 'chmod +x gradlew'
                 sh './gradlew clean regressiontests'
              }
         }
      }
     post {
         always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'build/allure-results']]
            ])
         }
     }
}
