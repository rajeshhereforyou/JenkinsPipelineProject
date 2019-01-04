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
                //VersionNumber projectStartDate: '2018-12-19', versionNumberString: '${MAJOR_MINOR_VERSION}.${BUILD_DATE_FORMATTED, "yyyyMMdd"}.${BUILDS_TODAY}', versionPrefix: '', worstResultForIncrement: 'FAILURE'
                echo 'Setting App Version'
                 //VersionNumber projectStartDate: '2018-12-19', versionNumberString: '${MAJOR_MINOR_VERSION}', versionPrefix: '', worstResultForIncrement: 'FAILURE'
                 VersionNumber projectStartDate: '', versionNumberString: '${BUILD_NUMBER}', versionPrefix: '', worstResultForIncrement: 'FAILURE'
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

