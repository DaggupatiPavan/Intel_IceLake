pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/Ramteja777/icelake.git'
            }
        }
        stage('Build infra'){
            steps{
                sh '''
                terraform init
                terraform validate
                terraform plan -out=tfplan
                terraform apply tfplan
                terraform output -json private_ips | jq -r '.[]'
                '''
            }
        }
    }
}