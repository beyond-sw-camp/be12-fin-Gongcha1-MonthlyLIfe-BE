<img src="https://capsule-render.vercel.app/api?type=waving&height=250&color=gradient&text=%EC%9B%94%EC%A0%95%EC%95%A1%20%EC%9D%B8%EC%83%9D&fontSize=60&fontAlignY=30&animation=fadeIn&desc=Back-End%20System%20for%20%EB%A0%8C%ED%83%88%20%EA%B5%AC%EB%8F%85%20%EC%84%9C%EB%B9%84%EC%8A%A4&descSize=30" />


---

## 🧑‍💻 프로젝트 개요

> **[한화시스템 BEYOND SW캠프 12기] Final Project**  
> **공차1팀 - 월정액 인생 (Backend)**

**1인 가구를 위한 구독 기반 렌탈 플랫폼**의 핵심 백엔드 인프라를 구축했습니다.  
Spring Boot 기반 REST API 서버로, **구독, 결제, 장바구니, 상품 관리, 실시간 채팅, AI 챗봇 등 주요 도메인 기능**을 담당합니다.

---

## 👥 팀원 소개

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/celarim">
        <img src="https://mblogthumb-phinf.pstatic.net/MjAxNzA0MTNfMTQ2/MDAxNDkyMDg4OTU0NzU2.X-Ise8QGLx6BeA7f6y1lStSFaxdMRMNieJK_sB2sdokg.ll6BBI3GcX8hmiVP10LOy9b2rAZ2hHKnZFncXmzexsgg.JPEG.swhyun98/downloadfile.jpg?type=w420" width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>김경준</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Shin-JiHyun">
        <img src="https://i.namu.wiki/i/8wUNYOFiU0KQem2XbLBTkTmgGg4knQ1_xAxhTh2Yl6E0OUbwJKCNXuO32wS48LTPfXT1U3hzEmclYUhu0kOg3GBu7VFfhN-larrInwpPz2Bc6OIplUQSvQy2sMz4gMUmPxcxCsZZ_XFaOLpXsp363Q.webp" width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>신지현</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/leewoojin12">
        <img src="https://i.namu.wiki/i/OOrcrlumPF7y0fWMNwJGrUw29c5kJ9qtpPbLsKlKOV2OVBH3Y3j3hg9FWPNy3kCvTUMgHD68wTF2k3OscKuTtw.webp" width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>이우진</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/wkdlrn">
        <img src="https://i.namu.wiki/i/GlPkp9Dy4UIg4_LiRXKtZ2g5V-NsjY3LZi8k6WT6N3lQIHIKz8EaNESQLfZfV7lDi4E7k-VloLwSHDr21bQxVg.webp" width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
        <br /><sub><b>김재구</b></sub>
      </a>
    </td>
  </tr>
</table>

---

### 목차
- [🛠 기술 스택](#-기술-스택)
- [📦 주요 도메인 기능](#-주요-도메인-기능)
- [🧩 시스템 아키텍처](#-시스템-아키텍처)
- [🏆 백엔드 프로젝트 목표](#-백엔드-프로젝트-목표)
- [📈 프로젝트 설계](#-프로젝트-설계)
- [🧠 주요 기능 상세](#-주요-기능-상세)
- [📑 API 명세서](#-API-명세서)
- [🔍 핵심 로직 상세 설명](#-핵심-로직-상세-설명)
- [📂 프로젝트 폴더 바로가기](#-프로젝트-폴더-바로가기)

---

## 🧰 기술 스택

### 🖥 Frontend
![Vue.js](https://img.shields.io/badge/vue.js-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)

### 🔧 Backend
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-%232F7D32.svg?style=for-the-badge&logo=json-web-tokens&logoColor=white)
<img src="https://img.shields.io/badge/Spring Batch-6DB33F?style=for-the-badge&logo=Spring&logoColor=white" />

### 🗄 Database & Search
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/elasticsearch-%230377CC.svg?style=for-the-badge&logo=elasticsearch&logoColor=white)
![Longhorn](https://img.shields.io/badge/Longhorn-FF6600?style=for-the-badge&logo=rancher&logoColor=white)

### 🚀 CI / CD
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)

### 📡 Communication & Monitoring
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white)

---

## 📦 주요 도메인 기능

| 기능         | 설명 |
|--------------|------|
| 회원 관리     | 로그인, 회원가입, JWT 인증, 마이페이지 조회 |
| 구독 상품     | 상품 등록/수정/삭제, 구독 기간 및 옵션 관리 |
| 장바구니      | 상품 추가/삭제, 다건 결제 처리 |
| 결제         | 포트원 연동, 결제 성공/실패 분기 처리 |
| 재고 처리     | 결제 시 재고 감소, 실패 시 롤백 처리 |
| 관리자 기능   | 상품 관리, 반납/수리/연체 관리, 대시보드 통계 |
| 실시간 채팅   | 1:1 WebSocket 기반 실시간 채팅 |
| AI 챗봇 (MCP) | GPT 연동 자연어 기반 추천 및 구독 요청 처리 |

---

## 🧩 시스템 아키텍처
<img src="https://github.com/user-attachments/assets/772e1bce-3339-4ea9-805b-a6b122d66347" width="617" />

<br>

---

## 🏆 백엔드 프로젝트 목표
- `비즈니스 로직의 일관성과 유지보수성 향상`
  - 복잡한 구독 및 결제 흐름을 퍼사드(Facade) 패턴으로 통합 관리하여, 로직의 응집도를 높이고 트랜잭션 단위를 명확히 하였습니다.
  - 이로 인해 장애 발생 시 복구가 용이하고, 각 서비스는 단일 책임에 집중할 수 있어 테스트와 유지보수가 쉬워졌습니다.
- `개발 생산성 향상을 위한 코드 자동화 체계 구축`
  - DTO ↔ Entity 변환 과정에 MapStruct를 도입하여 반복적인 변환 코드를 제거하고, 구조 변경 시 자동 반영이 가능하도록 설계하여 안정적인 개발 환경을 마련했습니다.
- `자연어 기반 챗봇 인터페이스 구현으로 사용자 접근성 강화`
  - GPT 기반 MCP 구조(Model-Context-Protocol)를 설계하여, 사용자의 자연어 요청(예: “삼성 청소기 3개월만 구독해줘”)을 파싱하고 구독 로직과 연동되도록 자동화했습니다.
  - 챗봇 인터페이스를 통해 구독 경험을 직관적이고 사용자 친화적으로 개선했습니다.
- `구독 결제의 자동화 및 안정적 운영`
  - Spring Batch와 Kubernetes CronJob을 연동하여 정기 결제를 자동화하고, 재고 복원 및 트랜잭션 롤백 처리로 데이터 무결성을 확보했습니다.
  - 일괄 처리 기반으로 대량 구독도 안정적으로 운영할 수 있도록 시스템을 설계했습니다.
- `사용자 친화적 경험 제공`
  - 사용자가 쉽고 빠르게 원하는 제품을 찾고 구독할 수 있는 경험 제공하기 위해 Elasticsearch를 도입하여 빠르고 정확한 상품 탐색과 커스텀 필터 구성으로 초성/유사어/오타 등의 검색을 지원합니다.
  - 또한 장바구니 기반 통합 신청 기능으로 개별 렌탈 신청 번거로움을 해소합니다.


[자세한 설명 보기](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-BE/wiki/%EC%84%A4%EA%B3%84-%EA%B2%B0%EC%A0%95-%EB%B0%8F-%EA%B8%B0%EC%88%A0-%EC%84%A0%ED%83%9D-%EC%9D%B4%EC%9C%A0)

---

## 📈 프로젝트 설계

### [1. 기획서 바로가기](https://docs.google.com/document/d/1S5pfITLqqDCIop5Io-dq3erEEs1rubz7M1q3DYilqFg/edit?tab=t.0)

### [2. 요구사항 정의서](https://docs.google.com/spreadsheets/d/1EtZBZOIuHVj2c4CbhtKNAfVmMSwQfEDYWAfI29FrQ7o/edit?gid=1790637635#gid=1790637635)

### [3. WBS](https://docs.google.com/spreadsheets/d/1EtZBZOIuHVj2c4CbhtKNAfVmMSwQfEDYWAfI29FrQ7o/edit?gid=1159274132#gid=1159274132)

### [4. 화면설계서 바로가기](https://www.figma.com/design/t1k8zoIFly0Hyxt0dJplNP/Gongcha1?node-id=71-86&p=f)

### [5. ERD ](https://media.discordapp.net/attachments/1354007770218893405/1356832191954550955/erd_2025_04_02.png?ex=6826b073&is=68255ef3&hm=2206536880e9b15ad3911b2f02249afad7022ca60014843505e543ad6b925788&=&format=webp&quality=lossless&width=953&height=704)

hello world!!
