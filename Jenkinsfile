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
                sh 'cp build/libs/demo-0.0.1-SNAPSHOT.jar $APP_PATH/'
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
        stage('Backup Old JAR') {
            steps {
                sh '''
                if [ -f $APP_PATH/demo-0.0.1-SNAPSHOT.jar ]; then
                    DATE=$(date +%d%m%Y_%H%M%S)
                    echo "Backing up existing JAR..."

                    mv $APP_PATH/demo-0.0.1-SNAPSHOT.jar \
                       $APP_PATH/demo-0.0.1-SNAPSHOT_$DATE.jar
                else
                    echo "No existing JAR found, skipping backup"
                fi
                '''
            }
        }

        stage('Run App') {
            steps {
                sh '''
                if [ ! -f $APP_PATH/run_app.sh ]; then
                    echo "run_app.sh not found. Generating new startup script..."
                    cat <<EOF > $APP_PATH/run_app.sh
#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=\\$JAVA_HOME/bin:\\$PATH
cd $APP_PATH
java -jar demo-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
EOF
                    chmod +x $APP_PATH/run_app.sh
                else
                    echo "Using existing run_app.sh"
                fi

                export JENKINS_NODE_COOKIE=dontKillMe
                $APP_PATH/run_app.sh
                '''
            }
        }

        stage('Check App') {
            steps {
                sh '''
                sleep 10
                ps -ef | grep demo | grep -v grep || true
                '''
            }
        }
    }
}