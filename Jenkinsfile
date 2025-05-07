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
        PROFILE = profileRules.get(GIT_BRANCH, 'local')
    }
    stages {
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