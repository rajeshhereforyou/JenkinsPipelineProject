pipeline {
    agent { label 'linuxslave' }

    stages {
        stage('Multiple SCM checkout ') {
            steps {
                echo 'SCM checkout..'
                checkout([$class: 'GitSCM', branches: [[name: '${SERVICE_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${SERVICE_REPO_URL}']]])
                checkout([$class: 'GitSCM', branches: [[name: '${BUILDSCRIPTS_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '${BUILDSCRIPTS_DIR}']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${BUILDSCRIPTS_REPO_URL}']]])
            }
        }

        stage('Setting App Version'){
            steps {
                def versionNumber = VersionNumber(versionNumberString: '${BUILDS_ALL_TIME}', versionPrefix: '1.0.', buildsAllTime: '12')
                echo "VersionNumber2"
            }
        }

        stage('Gradle build') {
            steps {
                echo 'Building..'

                sh './gradlew build -x test'
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

