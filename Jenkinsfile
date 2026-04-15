pipeline {
    agent any

    environment {
        APP_PATH = "/data/mservice/jenkins"
    }

    stages {

        stage('Build') {
            steps {
                sh '''
                export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
                export PATH=$JAVA_HOME/bin:$PATH
                chmod +x gradlew
                ./gradlew clean build
                '''
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
               echo "Starting application..."
               export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
               export PATH=$JAVA_HOME/bin:$PATH
               java -jar $APP_PATH/demo-0.0.1-SNAPSHOT.jar > $APP_PATH/app.log 2>&1 &
               '''
           }
       }
       stage('Check App') {
           steps {
               sh '''
               sleep 10
               ps -ef | grep demo || true
               '''
           }
       }
    }
}