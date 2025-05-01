pipeline {
    agent {
        label 'phanes'
    }
    environment {
        REGISTRY = "harbor.phanescloud.com"
        PROFILE = 'local'
    }
    stages {
        stage('Set Profile') {
            steps {
                script {
                    echo "Branch: ${env.GIT_BRANCH}"
                    if (env.GIT_BRANCH == 'dev') {
                        env.PROFILE = 'dev'
                    }
                    echo "Selected Profile: ${env.PROFILE}"
                }
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh "make build PROFILE=${env.PROFILE}"
            }
        }
        stage('Image Build and Push') {
            when {
                expression { return env.CHANGE_ID == null }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'harbor',
                                                     usernameVariable: 'HARBOR_USER',
                                                     passwordVariable: 'HARBOR_PASSWORD')]) {
                    sh "docker login --username \"$HARBOR_USER\" --password \"$HARBOR_PASSWORD\" ${REGISTRY}"
                }
                sh "make BRANCH=${env.GIT_BRANCH} docker-push"
            }
        }
    }
}