pipeline {
    agent { label 'master' }

    stages {

        stage('Multiple SCM checkout ') {
            steps {
                echo 'SCM checkout..'

                checkout([$class: 'GitSCM', branches: [[name: '*/sonarqube_changes']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: $SERVICE_REPO_URL]]])

                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'HelloWorld']], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/rajeshhereforyou/HelloWorld.git']]])

            }
        }

        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}

