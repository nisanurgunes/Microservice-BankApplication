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

        stage('Find Project Root') {
            steps {
                script {
                    echo "WORKSPACE = ${env.WORKSPACE}"
                    sh "ls -R ${WORKSPACE}"

                    // pom.xml nerede diye kontrol et
                    def found = sh(
                        script: "find ${WORKSPACE} -maxdepth 3 -name pom.xml | grep bankapplicationn/pom.xml || true",
                        returnStdout: true
                    ).trim()

                    if(found == "") {
                        error("❌ Parent pom.xml bulunamadı! Jenkins proje dizinini yanlış check-out etmiş.")
                    }

                    env.PROJECT_ROOT = found.replace("/pom.xml","")
                    echo "📌 Project root found at: ${env.PROJECT_ROOT}"
                }
            }
        }

        stage('Build with Maven') {
            steps {
                sh """
                    docker run --rm \
                        -v ${PROJECT_ROOT}:/app \
                        -w /app \
                        ${MAVEN_IMAGE} mvn -q -DskipTests clean package
                """
            }
        }

        stage('Build Docker Images') {
            steps {
                sh """
                    docker build -t ${DOCKERHUB_REPO}/accounts ${PROJECT_ROOT}/accounts
                    docker build -t ${DOCKERHUB_REPO}/cards ${PROJECT_ROOT}/cards
                    docker build -t ${DOCKERHUB_REPO}/loans ${PROJECT_ROOT}/loans
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
