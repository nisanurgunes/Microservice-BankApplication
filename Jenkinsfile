pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn -v'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker build -t gunesng022/accounts ./accounts'
                sh 'docker build -t gunesng022/cards ./cards'
                sh 'docker build -t gunesng022/loans ./loans'
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-token',
                                                 usernameVariable: 'USER',
                                                 passwordVariable: 'PASS')]) {

                    sh "echo $PASS | docker login -u $USER --password-stdin"

                    sh 'docker push gunesng022/accounts'
                    sh 'docker push gunesng022/cards'
                    sh 'docker push gunesng022/loans'
                }
            }
        }
    }
}
