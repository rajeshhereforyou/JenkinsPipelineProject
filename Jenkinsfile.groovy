node('linuxslave') {
   echo ' Will run on the slave with name or tag specialSlave'

   env.APP_VERSION = VersionNumber([
     versionNumberString: '${MAJOR_MINOR_VERSION}.${BUILD_DATE_FORMATTED, "yyyyMMdd"}.${BUILDS_TODAY}',
     versionPrefix: '',
     worstResultForIncrement: 'FAILURE'
   ]);


    stage('Setting App Version'){
        sh 'echo "$APP_VERSION"';

        env.BUILDSCRIPTS_DIR = "${WORKSPACE}/${BUILDSCRIPTS_DIR}"
    }

    stage('Multiple SCM checkout ') {
        echo 'SCM checkout..'
        checkout([$class: 'GitSCM', branches: [[name: '${BUILDSCRIPTS_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '${BUILDSCRIPTS_DIR}']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${BUILDSCRIPTS_REPO_URL}']]])

        //build job: 'RootPipelineTemplate', parameters: [string(name: 'BUILDSCRIPTS_REPO_URL', value: '${BUILDSCRIPTS_REPO_URL}'), string(name: 'BUILDSCRIPTS_REPO_BRANCH', value: '${BUILDSCRIPTS_REPO_BRANCH}'), string(name: 'BUILDSCRIPTS_DIR', value: '${BUILDSCRIPTS_DIR}'), string(name: 'MAJOR_MINOR_VERSION', value: '1.0'), booleanParam(name: 'executePrintTask', value: false)]

        //build job: 'RootPipelineTemplate', parameters: [string(name: 'BUILDSCRIPTS_REPO_URL', value: 'https://github.com/rajeshhereforyou/JenkinsPipelineProject.git'), string(name: 'BUILDSCRIPTS_REPO_BRANCH', value: '${BUILDSCRIPTS_REPO_BRANCH}'), string(name: 'BUILDSCRIPTS_DIR', value: '${BUILDSCRIPTS_DIR}'), string(name: 'MAJOR_MINOR_VERSION', value: '1.0'), booleanParam(name: 'executePrintTask', value: false)]


        checkout([$class: 'GitSCM', branches: [[name: '${SERVICE_REPO_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHubCredentials', url: '${SERVICE_REPO_URL}']]])
    }

    stage('Build'){
        sh './gradlew clean build'
    }

    stage('Test'){
        junit '**/build/test-results/test/*.xml'
    }

//    stage('Latest Changes'){
//        passedBuilds = []
//
//        lastSuccessfulBuild(passedBuilds, currentBuild);
//
//        for(int i=0; i<passedBuilds.size();i++){
//            print(passedBuilds[i].getNumber());
//        }
//
//        def changeLog = getChangeLog(passedBuilds)
//        echo "changeLog is  ${changeLog}"
//    }

}

/*node('linuxslave') {
    stage('TEsting different nodes'){
        echo ' Will run on the slave with name or tag specialSlave'
    }
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

        println( "################### changeLogSets.size() is "+changeLogSets.size())

        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                log += "* ${entry.msg} with ${entry.commitId} by ${entry.author} \n"
            }
        }
    }
    return log;
}*/

