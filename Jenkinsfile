pipeline {
	agent any
    environment {
        NAMESPACE = 'atp'
        CIRCLECI_PROJECT_SLUG = 'circleci/NVW8jDWaLTTZtWWpb2T9ts/Qt1bvTiqz9q5RKVM6zhXB2'
        CIRCLECI_DEFINITION_ID = '7e2b58f0-c87d-43a3-9c83-808b75fe7583'
        CIRCLECI_TOKEN = credentials('CIRCLECI_TOKEN')
    }
  
    stages {
        stage('Trigger CircleCI Pipeline') {
            steps {
                script {
                    echo "Starting CircleCI Pipeline...."

                    // CircleCI 파이프라인 실행 및 ID 가져오기
                    def pipeline_id = sh(script: """
                        curl -X POST 'https://circleci.com/api/v2/project/$CIRCLECI_PROJECT_SLUG/pipeline/run' \
                        --header 'Circle-Token: $CIRCLECI_TOKEN' \
                        --header 'Content-Type: application/json' \
                        --data '{
                            "definition_id": "$CIRCLECI_DEFINITION_ID",
                            "config": {
                                "branch": "develop"
                            },
                            "checkout": {
                                "branch": "develop"
                            },
                            "parameters": {
                                "tag": "$BUILD_ID"
                            }
                        }'  | grep -o '"id":"[^"]*"' | awk -F':' '{print \$2}' | tr -d '"'
                    """, returnStdout: true).trim()

                    echo "Triggered CircleCI Pipeline ID: ${pipeline_id}"
                    env.PIPELINE_ID = pipeline_id
                }
            }
        }
        stage('Wait for CircleCI Completion') {
            steps {
                script {
                    echo "Waiting for CircleCI Pipeline to Complete..."

                    def status = "running"
                    while (status == "running" || status == "pending") {
                        sleep(10) // 10초 대기 후 다시 확인

                        status = sh(script: """
                            curl --silent --location 'https://circleci.com/api/v2/pipeline/${env.PIPELINE_ID}/workflow' \
                            --header 'Circle-Token: $CIRCLECI_TOKEN' | grep -o '"status" *: *"[^"]*"' | awk -F': ' '{print \$2}' | tr -d '"'
                        """, returnStdout: true).trim()
                        echo "CircleCI Pipeline Status: ${status}"
                    }

                    // 실패 또는 취소된 경우 빌드 중단
                    if (status != "success") {
                        error "CircleCI Pipeline Failed or Canceled! Status: ${status}"
                    }

                    echo "CircleCI Pipeline Completed Successfully!"
                }
            }
        }
}
