pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = "gunesng022"
        MAVEN_IMAGE   = "maven:3.9.6-eclipse-temurin-17"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('List Workspace') {
            steps {
                echo "WORKSPACE = ${WORKSPACE}"
                sh "ls -R ${WORKSPACE}"
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    // POM gerçekten neredeyse ona göre mount edeceğiz
                    echo "Trying Maven build... (we will adjust path after listing)"

                    sh """
                        docker run --rm \
                            -v ${WORKSPACE}:/app \
                            -w /app \
                            ${MAVEN_IMAGE} mvn -q -DskipTests clean package || true
                    """
                }
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
                    sh "echo ${PASS} | docker login -u ${USER} --password-stdin"

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
