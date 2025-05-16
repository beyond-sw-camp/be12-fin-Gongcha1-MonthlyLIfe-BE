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

## 🔐 인증 및 보안

1. 로그인 시 Access Token 발급
2. API 호출 시 Header에 JWT 포함
3. Spring Security 기반 Role 검증 후 접근 허용

---

## 🧠 주요 기능 상세
<details>
  <summary><strong>👤 회원가입</strong></summary>
  <ul>
    <li><strong>회원 가입</strong>:
      <br><img src="https://github.com/user-attachments/assets/d620d088-e259-4534-b806-4f80ceaf3676" width="400" />
    </li>
    
    <li><strong>로그인</strong>: 이메일과 비밀번호로 JWT 발급
      <br><img src="./gif/USER002.gif" width="400" />
    </li>
  </ul>
</details>


<details>
  <summary><strong>🛒 상품 조회 기능</strong></summary>
  <ul>
    <li><strong>상품 목록 조회</strong>: 전체 상품 최신순 조회
      <br><img src="./gif/SALE001.gif" width="400" />
    </li>
    <li><strong>상품 검색</strong>: 조건(이름, 가격 등)에 따른 상품 검색
      <br><img src="./gif/SALE004.gif" width="400" />
    </li>
    <li><strong>상품 상세 조회</strong>: 상품 사양 및 렌탈 조건 확인
      <br><img src="./gif/SALE005.gif" width="400" />
    </li>
  </ul>
</details>

<details>
  <summary><strong>📦 구독/결제 기능</strong></summary>
  <ul>
    <li><strong>상품 구독</strong>: 상품 및 기간 선택 후 결제
      <br><img src="./gif/SUBSCRIBE001.gif" width="400" />
    </li>
    <li><strong>장바구니 추가</strong>: 상품을 장바구니에 담기
      <br><img src="./gif/SUBSCRIBE002.gif" width="400" />
    </li>
    <li><strong>구독 정보 확인</strong>: 현재 구독 내역 확인
      <br><img src="./gif/SUBSCRIBE008.gif" width="400" />
    </li>
  </ul>
</details>

<details>
  <summary><strong>📞 고객지원 기능</strong></summary>
  <ul>
    <li><strong>1:1 채팅 상담</strong>: 유저가 메시지를 보내면 관리자에게 실시간 전달
      <br><img src="./gif/SUPPORT001.gif" width="400" />
    </li>
    <li><strong>수리신청/분실신고</strong>: 사유 입력 후 요청 등록
      <br><img src="./gif/SUPPORT003.gif" width="400" />
    </li>
  </ul>
</details>

<details>
  <summary><strong>🤖 AI 챗봇 기능 (MCP 기반)</strong></summary>
  <ul>
    <li><strong>GPT 챗봇 기반 구독 추천</strong>: 사용자 자연어 분석 → 조건 파악 → 상품 추천/자동 구독
      <br><img src="./gif/AI001.gif" width="400" />
    </li>
    <li><strong>사용자 조건 수집</strong>: 챗봇이 렌탈 기간, 제품 종류 등 누락된 정보를 순차 질문
      <br><img src="./gif/AI002.gif" width="400" />
    </li>
    <li><strong>AI 챗봇과 결제 연동</strong>: 추천 상품을 바로 구독 연결
      <br><img src="./gif/AI003.gif" width="400" />
    </li>
  </ul>
</details>

<details>
  <summary><strong> 관리자 기능</strong></summary>
  <ul>
    <li><strong> 판매 상품 등록 </strong>
      
    </li>
    <li><strong> 판매 상품 수정 및 삭제</strong>
    
    </li>
    
  </ul>
</details>


---

## 📑 API 명세서

- Swagger: [https://monthlylife.kro.kr/docs/swagger-ui/index.html](https://monthlylife.kro.kr/docs/swagger-ui/index.html)

---

## ⚙ 개발 환경 세팅

```bash
# 1. Git clone
git clone https://github.com/공차1팀-backend.git

# 2. 환경 변수 설정
# application-prod.yml 작성 (DB, S3, JWT 정보 포함)

# 3. Docker 빌드 및 실행
docker-compose up --build

# 4. Swagger 확인
http://localhost:8080/api/swagger-ui/index.html
