# 🎁팀원 소개
> **[한화시스템 BEYOND SW캠프 12기] Final Project**  
> **공차1팀 - 월정액 인생 DevOps**

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/celarim">
        <img src="https://mblogthumb-phinf.pstatic.net/MjAxNzA0MTNfMTQ2/MDAxNDkyMDg4OTU0NzU2.X-Ise8QGLx6BeA7f6y1lStSFaxdMRMNieJK_sB2sdokg.ll6BBI3GcX8hmiVP10LOy9b2rAZ2hHKnZFncXmzexsgg.JPEG.swhyun98/downloadfile.jpg?type=w420"
             width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>김경준</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Shin-JiHyun">
        <img src="https://i.namu.wiki/i/8wUNYOFiU0KQem2XbLBTkTmgGg4knQ1_xAxhTh2Yl6E0OUbwJKCNXuO32wS48LTPfXT1U3hzEmclYUhu0kOg3GBu7VFfhN-larrInwpPz2Bc6OIplUQSvQy2sMz4gMUmPxcxCsZZ_XFaOLpXsp363Q.webp"
             width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>신지현</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/leewoojin12">
        <img src="https://i.namu.wiki/i/OOrcrlumPF7y0fWMNwJGrUw29c5kJ9qtpPbLsKlKOV2OVBH3Y3j3hg9FWPNy3kCvTUMgHD68wTF2k3OscKuTtw.webp"
             width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>이우진</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/wkdlrn">
        <img src="https://i.namu.wiki/i/GlPkp9Dy4UIg4_LiRXKtZ2g5V-NsjY3LZi8k6WT6N3lQIHIKz8EaNESQLfZfV7lDi4E7k-VloLwSHDr21bQxVg.webp"
             width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>김재구</b></sub>
      </a>
    </td>
  </tr>
</table>

<br>
---

### 📑 목차
- [🛠 기술 스택](#-기술-스택)
- [📚 데모 사이트 링크](#-데모-사이트-바로가기)
- [🎨 서비스 소개](#-서비스-소개)
- [🏗️ 시스템 아키텍처](#-시스템-아키텍처)
- [📚 데브옵스 프로젝트 목표](#-데브옵스-프로젝트-목표)
- [⚙️ 주요 기능 시연](#-주요-기능-시연)
- [📂 프로젝트 폴더 바로가기](#-프로젝트-폴더-바로가기)

---

## 🛠 기술 스택

#### &nbsp;　[ Frontend ]
![Vue.js](https://img.shields.io/badge/vue.js-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)

#### &nbsp;　[ Backend ]
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-%232F7D32.svg?style=for-the-badge&logo=json-web-tokens&logoColor=white)
<img src="https://img.shields.io/badge/Spring Batch-6DB33F?style=for-the-badge&logo=Spring&logoColor=white" style="border-radius: 5px;">


#### &nbsp;　[ DB ]
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/elasticsearch-%230377CC.svg?style=for-the-badge&logo=elasticsearch&logoColor=white)
![Longhorn](https://img.shields.io/badge/Longhorn-FF6600?style=for-the-badge&logo=rancher&logoColor=white)

#### &nbsp;　[ CI/CD ]
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![CircleCI](https://img.shields.io/badge/circleCI-343434?style=for-the-badge&logo=circleci&logoColor=white)
![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)

#### &nbsp;　[ Communication ]
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

<br>
---

## 🎨 서비스 소개

CI/CD 파이프라인을 통해 개발자의 실수나 반복적인 배포 절차에서 오는 실수, 부담을 줄이고자 했습니다. Jenkins 기반 자동화, Kubernetes Blue/Green 방식 무중단 배포, 그리고 로그/모니터링 시스템까지 구축하여 안정적이고 반복 가능한 배포 체계를 갖추었습니다.

> 주요 도입 요소:
> - GitHub → Jenkins → CircleCI → DockerHub → Kubernetes 자동 배포  
> - 웹훅 기반 배포 연동 및 테스트 자동화

---

## 🏗️ 시스템 아키텍처

- CI Trigger: GitHub Webhook  
- CI Server: Jenkins (Docker 기반)  
- Container Registry: DockerHub  
- K8s: Master 1대, Worker 6대 운영 (192.0.20.90~95)  
- LoadBalancer: MetalLB (192.0.20.100~200)  
- Storage: Longhorn + PVC
- Monitoring: Prometheus + Grafana  
- Log: Logstash → Elasticsearch → Kibana  
- Ingress ssl 인증서 적용용


<img src="https://github.com/user-attachments/assets/772e1bce-3339-4ea9-805b-a6b122d66347" width="617" />

---

## 📚 데브옵스 프로젝트 목표

- `CI/CD 파이프라인 구축` : 코드 변경 → 자동 빌드 → 테스트 → 배포까지 자동화
- `모니터링 및 로깅` : 애플리케이션 상태 모니터링 (Prometheus, Grafana 등)
- `컨테이너화 및 오케스트레이션` : Docker, Kubernetes 등을 통한 배포 및 확장성 확보
- `보안 및 백업 관리` : 서버 보안, SSL 인증서 관리, 정기 백업 자동화

---

## ⚙️ 주요 기능 시연

<details>
<summary>✅ Jenkins 기반 자동화</summary>

- Webhook 이벤트 감지 → Git clone → 테스트/빌드 → DockerHub push → K8s 배포  
![MonthlyLife_jenkins_pipeline](https://github.com/user-attachments/assets/e1aac91d-a6ab-47b8-9b66-807bb6a1e69c)

</details>

<details>
<summary>✅ 무중단 배포 (Blue/Green Deployment)</summary>

- 기존 배포를 유지한 채, 기존과 다른 색상의 Kubernetes Deployment를 업로드
- actuator health check를 이용하여 정상 상태 확인 후 제어
- `kubectl rollout status`, `kubectl scale` 등을 통한 트래픽 전환 및 제어  
- 장애 발생 시 빠르게 롤백 가능

</details>

<details>
<summary>✅ 점진적 배포 (Canary Deployment)</summary>

- 기존 배포를 유지한 채, 새로운 Kubernetes Deployment를 업로드
- Ingress를 이용하여 임의의 비율의 유저는 새로운 Frontend, 나머지는 기존 Frontend로 연결
- 처음 사이트에 접속하면 cookie를 이용하여 해당 버전으로 고정
- `kubectl rollout status`, `kubectl scale` 등을 통한 트래픽 전환 및 제어  
- 장애 발생 시 빠르게 롤백 가능
- canary test
![canary_test](https://github.com/user-attachments/assets/a2f7bda2-5dbd-460d-9d7b-be36ac913e4b)

처음 두 번은 기존 프론트엔드로 연결, 마지막 한 번은 새로운 버전의 프론트엔드를 확인(쿠키를 삭제하여 변경)
점진적으로 기존 버전과 새로운 버전의 노출 비율을 변경하여 배포포

</details>

<details>
<summary>✅ 자동화 + 무중단 배포 테스트 영상</summary>

- [무중단 배포 테스트 영상](https://drive.google.com/file/d/1BSC3BdyDtF_MrZUQtffgR682OGdFT0uh/view?usp=drive_link)
- 테스트 방법 : 백엔드 서버를 지속적으로 호출하는 동안 main branch에 push하여 자동화 배포 및 중단 여부 확인

</details>



<details>
<summary>✅ 모니터링 및 로깅</summary>

- Grafana 대시보드: http://192.0.20.90:31189/  
- Prometheus 상태 확인: http://192.0.20.140/  
- Elasticsearch 보안 연결: quickstart-es-http.elk.svc  

![grafana_dashboard](https://github.com/user-attachments/assets/964f0bb2-ea60-42b4-bcc8-514173eebdb8)

</details>

<details>
<summary>✅ Elasticsearch 연동 파이프라인</summary>

- Logstash 설정으로 Sale 정보 JSON 변환 후 Elasticsearch 저장  
- 마리아DB → Logstash JDBC → 변환 필터 → Elastic  
- 주기적 스케줄: `* * * * *`

</details>

---

## 📂 프로젝트 폴더 바로가기

- [📁 Backend Repository](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-BE)
- [📁 Infra/DevOps YAML](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-BE/tree/develop/devops)
- [📁 Frontend Repository](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-FE) 

❗ 미완성 정보는 확인 후 보완 바랍니다.
