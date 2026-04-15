pipeline {
    agent any

    environment {
        APP_PATH = "/data/mservice/jenkins"
    }

    stages {

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Verify Build') {
            steps {
                sh 'ls -lh build/libs/'
            }
        }

        stage('Copy JAR') {
            steps {
                sh '''
                cp build/libs/demo-0.0.1-SNAPSHOT.jar $APP_PATH/
                '''
            }
        }

        stage('Stop Old App') {
            steps {
                sh '''
                pkill -9 -f "demo-0.0.1-SNAPSHOT.jar" || true
                sleep 5
                '''
            }
        }

        stage('Run App') {
            steps {
                sh '''
                nohup java -jar $APP_PATH/demo-0.0.1-SNAPSHOT.jar > $APP_PATH/app.log 2>&1 &
                '''
            }
        }
    }
}