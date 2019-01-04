pipeline {
    agent { label 'linuxslave' }

    environment {
      APP_VERSION = VersionNumber([
        versionNumberString: '${MAJOR_MINOR_VERSION}.${BUILD_DATE_FORMATTED, "yyyyMMdd"}.${BUILDS_TODAY}',
        versionPrefix: '',
        worstResultForIncrement: 'FAILURE'
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
                sh 'echo "$APP_VERSION"';
            }
        }


        stage('Tagging') {
            steps {
                echo 'Tagging..'

                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'GitHubCredentials',
                usernameVariable: 'gitUser', passwordVariable: 'gitPwd']]) {
                    sh 'echo uname=$gitUser pwd=$gitPwd'
                    //sh 'echo $SERVICE_REPO_URL##*//'

                    sh 'git config --global user.name $gitUser'
                    sh 'git tag -a ${APP_VERSION} -m "Version ${APP_VERSION}"'
                    //sh 'git push https://$gitUser:$gitPwd@${SERVICE_REPO_URL##*//}  --tags'
                    sh 'git push https://$gitUser:$gitPwd@github.com/rajeshhereforyou/myspringbootapp.git  --tags'

                    //sh 'echo "${SERVICE_REPO_URL##*//}"'
                 }


            }
        }

        stage('Gradle build') {
                    steps {
                        echo 'Building..'

                       // sh './gradlew build -x test'
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}

