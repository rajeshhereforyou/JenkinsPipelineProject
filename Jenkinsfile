node('linuxslave') {
   echo ' Will run on the slave with name or tag specialSlave'

   env.APP_VERSION = VersionNumber([
     versionNumberString: '${MAJOR_MINOR_VERSION}.${BUILD_DATE_FORMATTED, "yyyyMMdd"}.${BUILDS_TODAY}',
     versionPrefix: '',
     worstResultForIncrement: 'FAILURE'
   ]);

    stage('Setting App Version'){
        sh 'echo "$APP_VERSION"';
    }

    stage('Multiple SCM checkout ') {
        echo 'SCM checkout..'
        checkout([$class: 'GitSCM', branches: [[name: '${SERVICE_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${SERVICE_REPO_URL}']]])
        checkout([$class: 'GitSCM', branches: [[name: '${BUILDSCRIPTS_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '${BUILDSCRIPTS_DIR}']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${BUILDSCRIPTS_REPO_URL}']]])
    }

    stage('Latest Changes'){
        passedBuilds = []

        lastSuccessfulBuild(passedBuilds, currentBuild);

        for(int i=0; i<passedBuilds.size();i++){
            print(passedBuilds[i].getNumber());
        }

        def changeLog = getChangeLog(passedBuilds)
        echo "changeLog is  ${changeLog}"
    }

    stage('Testing Conditional Task Execution') {
        sh 'echo $executePrintTask'

        def b = $executePrintTask

        if(b){
            sh './gradlew printGlobalEnvProps'
        }
    }

    /*stage('Gradle build'){
        sh './gradlew build -x test'
    }

    stage('Tagging') {
        echo 'Tagging..'

        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'GitHubCredentials',
        usernameVariable: 'gitUser', passwordVariable: 'gitPwd']]) {
            sh 'git config --global user.name $gitUser'
            sh 'git tag -a ${APP_VERSION} -m "Version ${APP_VERSION}"'
            sh 'git push https://$gitUser:$gitPwd@${SERVICE_REPO_URL##*//}  --tags'
         }
    }*/
}

def lastSuccessfulBuild(passedBuilds, build) {
  if ((build != null) && (build.result != 'SUCCESS')) {
      passedBuilds.add(build)
      lastSuccessfulBuild(passedBuilds, build.getPreviousBuild())
   }
}

def getChangeLog(passedBuilds) {
    def log = ""
    for (int x = 0; x < passedBuilds.size(); x++) {
        def currentBuild = passedBuilds[x];
        def changeLogSets = currentBuild.changeSets
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                log += "* ${entry.msg} with ${entry.commitId} by ${entry.author} \n"
            }
        }
    }
    return log;
}

