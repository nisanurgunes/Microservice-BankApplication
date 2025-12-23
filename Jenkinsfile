pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = "gunesng022"
        MAVEN_IMAGE = "maven:3.9.6-eclipse-temurin-17"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh """
                    docker run --rm \
                        -v \$PWD:/app \
                        -w /app \
                        ${MAVEN_IMAGE} \
                        mvn clean package -DskipTests
                """
            }
        }

        stage('Build Docker Images') {
            steps {
                sh """
                    docker build -t ${DOCKERHUB_REPO}/accounts ./accounts
                    docker build -t ${DOCKERHUB_REPO}/cards ./cards
                    docker build -t ${DOCKERHUB_REPO}/loans ./loans
                """
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-token',
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS'
                    )
                ]) {

                    sh "echo \$PASS | docker login -u \$USER --password-stdin"

                    sh """
                        docker push ${DOCKERHUB_REPO}/accounts
                        docker push ${DOCKERHUB_REPO}/cards
                        docker push ${DOCKERHUB_REPO}/loans
                    """
                }
            }
        }
    }
}
