pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-17'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t gunesng022/accounts ./accounts'
                sh 'docker build -t gunesng022/cards ./cards'
                sh 'docker build -t gunesng022/loans ./loans'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: "dockerhub-token", variable: "TOKEN")]) {
                    sh "echo $TOKEN | docker login -u gunesng022 --password-stdin"
                    sh "docker push gunesng022/accounts"
                    sh "docker push gunesng022/cards"
                    sh "docker push gunesng022/loans"
                }
            }
        }
    }
}
