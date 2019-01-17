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

        stage('Post Build Actions - Build chaining'){
            def currentBuildResult = "${currentBuild.getCurrentResult()}"

            if(currentBuildResult != null && currentBuildResult.toString().equalsIgnoreCase("SUCCESS")){
                echo "${currentBuildResult}"
                def testVar = build('TestFreeStyle2')
                echo "${testVar.getCurrentResult()}"
            } else
                echo "Build Failure"

            def buildNumber = Jenkins.instance.getItem('TestFreeStyle2').lastSuccessfulBuild.number

            echo "${buildNumber}"
        }

    /*stage('GetLastSuccessfulBuldVersionOf'){
        def jenkinsUrl = new URI(System.getenv("JENKINS_URL"))
        def js = new JenkinsServer(jenkinsUrl, System.getenv("JenkinsUser"), System.getenv("JenkinsPwd"))
        def jobs = js.getJobs()
        def job = jobs.get(System.getenv("TestFreeStyle2"))
        def jobWithDetails = job.details()
        def lastBuild = jobWithDetails.getLastBuild()
        def lastBuildWithDetail = lastBuild.details()

        lastSuccessfulBuild(jobWithDetails, jobWithDetails.getLastBuild());
    }*/
}


def lastSuccessfulBuild(jobWithDetails, build) {
    /*if ((build != null) && build.details() != null
            && (build.details().getResult() == null
            || !build.details().getResult().toString().equalsIgnoreCase("SUCCESS")
    ) ) {
        if(build.getNumber() >1){
            lastSuccessfulBuild(passedBuildsWithDetails, jobWithDetails, jobWithDetails.getBuildByNumber(build.getNumber() -1))
        }
    }*/

    println("build.details().getResult() :"+build.details().getResult())
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

