pipeline {
    agent { label 'linuxslave' }

    environment {
      VERSION = VersionNumber([
        versionNumberString : '${BUILD_YEAR}.${BUILD_MONTH}.${BUILD_ID}',
        projectStartDate : '2014-05-19'
      ]);
    }

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
                sh 'echo "$VERSION"';
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

