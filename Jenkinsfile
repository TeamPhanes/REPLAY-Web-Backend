def profileRules = [
    'dev': 'dev',
    'main': 'prod'
]

pipeline {
    agent {
        label 'phanes'
    }
    environment {
        REGISTRY = "harbor.phanescloud.com"
    }
    stages {
        stage('Build') {
            steps {
                def profile = env.CHANGE_ID ? 'local' : profileRules[env.GIT_BRANCH]
                sh 'chmod +x gradlew'
                sh "make build PROFILE=${profile}"
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