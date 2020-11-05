def project_name = "kubernetes-config"
def git_url = "https://github.com/shangaro/Hands-on-with-Jenkins-CI-CD-Pipelines-in-Kubernetes.git"
freeStyleJob(project_name) {

    logRotator(-1, 30)

    properties {
        copyArtifactPermissionProperty {
            projectNames('*')
        }
        githubProjectUrl(git_url)
    }
    triggers {
        githubPush()
    }

    scm {
        git {
            remote {
                url(git_url)
                name('${JOB_NAME}')
            }
            branch('master')
        }
    }

    steps {
        shell('''#!/bin/bash
COMMIT_ID=$(git rev-parse HEAD)

read -r -d '' VAR <<- EOM
$JOB_NAME:
  repo_url : $GIT_URL
  revision : $COMMIT_ID
  
EOM

echo "$VAR" > metadata.txt

''')

    }

    publishers {
        archiveArtifacts{
            pattern('metadata.txt')
            onlyIfSuccessful()
            fingerprint()
        }
    }
}


