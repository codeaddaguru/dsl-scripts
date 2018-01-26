folder('CODEADDAGURU') {
        description('CODEADDAGURU project foloder created')
}
freeStyleJob('CODEADDAGURU/compile') {
    logRotator(-1, 10)
    scm {
        github('NagireddyGuduru/myweb', 'master')
    }
    steps {
        maven('clean compile')
    }
    publishers {
        downstream('CODEADDAGURU/test', 'SUCCESS')
    }
}
mavenJob('CODEADDAGURU/test') {
    logRotator(-1, 10)
     scm {
        github('NagireddyGuduru/myweb', 'master')
    }
    goals('clean test')
   
   publishers {
        downstream('CODEADDAGURU/sonar', 'SUCCESS')
    }
}
mavenJob('CODEADDAGURU/sonar') {
    logRotator(-1, 10)
     scm {
        github('NagireddyGuduru/myweb', 'master')
    }
    goals('clean sonar:sonar')
  publishers {
        downstream('CODEADDAGURU/nexus', 'SUCCESS')
    }
}
mavenJob('CODEADDAGURU/nexus') {
    logRotator(-1, 10)
     scm {
        github('NagireddyGuduru/myweb', 'master')
    }
    goals('clean deploy')
}
buildPipelineView('CODEADDAGURU/build-pipeline') {
    filterBuildQueue()
    filterExecutors()
    title('CODEADDAGURU CI Pipeline')
    displayedBuilds(5)
    selectedJob('CODEADDAGURU/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
