pipeline {
    agent any
    parameters {
        choice(name: 'action', choices: 'apply\ndestroy', description: 'Choose the action you want')
    }
    environment {
        postgres_ip = null
        hammer_ip = null
    }
    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/AbhishekRaoV/Intel_IceLake.git'
            }
        }
        stage('Build infra') {
            steps {
                script {
                    if (params.action == 'destroy') {
                        sh "terraform destroy --auto-approve "
                    }
                    if (params.action == 'apply') {
                        sh '''
                            terraform init
                            terraform validate
                            terraform plan -out=tfplan
                            terraform apply tfplan -no-color
                            terraform output -json private_ips | jq -r '.[]'
                        '''
                        environment.postgres_ip = sh(script: "terraform output -json private_ips | jq -r '.[]' | head -1", returnStdout: true).trim()
                        environment.hammer_ip = sh(script: "terraform output -json private_ips | jq -r '.[]' | tail -1", returnStdout: true).trim()
                        sh '''
                        echo "Postgres IP: ${environment.postgres_ip}"
                        echo "Hammer IP: ${environment.hammer_ip}"
                        '''
                    }
                }
            }
        }

        stage('Generate Inventory file') {
            steps {
                script {
                    sh 'chmod +x inventoryfile.sh'
                    sh 'bash ./inventoryfile.sh'
                }
            }
        }

        stage('Install ansible') {
            steps {
                script {
                    sh "ssh ubuntu@${environment.postgres_ip} -- 'sudo apt install ansible'"
                    sh "ssh ubuntu@${environment.hammer_ip} -- 'sudo apt install ansible'"
                }
            }
        }

        stage('Install Tools') {
            steps {
                script {
                    sh """
                        ansible-playbook -i myinventory postgres_install.yaml
                        ansible-playbook -i myinventory hammerdb_install.yaml
                        ansible-playbook -i myinventory prometheus_install.yaml
                        ansible-playbook -i myinventory postgres_exporter_install.yaml -e postgres_ip=${environment.postgres_ip}
                        ansible-playbook -i myinventory grafana_install.yaml
                    """
                }
            }
        }
        stage('Configure') {
            steps {
                script {
                    sh """
                        ansible-playbook -i myinventory postgres_config.yaml -e postgres_ip=${environment.postgres_ip}, hammer_ip=${environment.hammer_ip}
                        ansible-playbook -i myinventory hammer_config.yaml -e postgres_ip=${environment.postgres_ip}
                        ansible-playbook -i myinventory postgres_backup.yaml 
                        ansible-playbook -i myinventory test_hammer.yaml -e postgres_ip=${environment.postgres_ip}
                        ansible-playbook -i myinventory restore_db.yaml 
                    """
                }
            }
        }
    }
}
