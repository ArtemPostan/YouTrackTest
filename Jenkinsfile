pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test -Dbase.uri=http://host.docker.internal:8080 -Dbase.url=http://host.docker.internal:8080 -Dgrid.url=http://host.docker.internal:4444/'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}