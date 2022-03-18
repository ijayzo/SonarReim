pipeline {
 environment {
        registry = 'ijayzo/proj1'
        dockerHubCreds = 'docker_hub'
        dockerImage = ''
    }
 agent any
    stages {
        stage('Test') {
            when {
                branch 'feature/*'
            }
        steps {
            withMaven {
                cleanWs()
                sh 'test'
            }
         }
       }
       stage('Build') {
          when {
             branch 'main'
          }
          steps {
            withMaven {

              sh 'mvn -f Reimburse/pom.xml clean install'
              sh 'mvn -f Reimburse/pom.xml clean package -DskipTests'

            }
          }
       }
       stage('Docker Build') {
           when {
               branch 'main'
           }
           steps {
            dir('Reimburse/') {
               script {
                  echo "$registry:$currentBuild.number"
                  dockerImage = docker.build "$registry"
               }
            }
           }
       }

       stage('Docker Deliver') {
           when {
               branch 'main'
           }
           steps {
               script {
                   docker.withRegistry('', dockerHubCreds) {
                       dockerImage.push("$currentBuild.number")
                       dockerImage.push("latest")
                   }
               }
           }
        }
      
stage('Wait for approval') {
        when {
            branch 'main'
        }
        steps {
            script {
                try {
                    timeout(time: 1, unit: 'MINUTES') {
                        approved = input message: 'Deploy to production?', ok: 'Continue',
                            parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy build to production')]
                        if(approved != 'Yes') {
                            error('Build did not pass approval')
                        }
                    }
                } catch(error) {
                    error('Build failed because timeout was exceeded')
                }
            }
        }
    }
 stage('Deploy to GKE') {
                when {
                    branch 'main'
                }
                steps{
                    sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./Reimburse/k8s/deployment.yml'
                    sh 'cat ./Reimburse/k8s/deployment.yml'
                    step([$class: 'KubernetesEngineBuilder',
                        projectId: 'revbanking',
                        clusterName: 'revbanking-gke',
                        zone: 'us-central1',
                        manifestPattern: 'Reimburse/k8s/',
                        credentialsId: 'revbanking',
                        verifyDeployments: true
                    ])
                               }
                            }
    } 
}
