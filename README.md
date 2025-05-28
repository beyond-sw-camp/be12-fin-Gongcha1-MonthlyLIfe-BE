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
        <img src="https://i.namu.wiki/i/kq2UHJ5flXHoMj2lDp1xJCUgfKj3dHRJdFJi5L5tCKFY8g4q7zquvfLMwksklZnm8qB_1mw19WiNas00FfoTllnlfeIn-9xLtQ41fZY_mBRK6stVbtpvsnSkA8kq9Cak9t7yzHdUkUqSuCiaK-8a4g.webp" width="120" height="150" style="object-fit: cover; border-radius: 8px;" />
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

---

## 🧠 주요 기능 상세
<details>
  <summary><strong>👤 회원가입</strong></summary>

  ### 회원가입
   - 요청
     ![회원가입 요청](https://github.com/user-attachments/assets/44aab1c2-6fec-4d7d-985e-c076436e8d5a)
      > 사용자가 이름, 이메일, 비밀번호 등의 정보를 입력하여 회원가입 요청을 보냅니다.
   - 응답
     ![회원가입 응답](https://github.com/user-attachments/assets/3acaaf7a-5321-443a-a864-17bd571e25a0)
       > 회원가입이 성공적으로 완료되었을 때 반환되는 응답입니다.
  ### 로그인
   - 요청
     ![로그인 요청](https://github.com/user-attachments/assets/057999c8-5952-4eff-9fe9-1ccd9ab69f83)
       > 아이디와 비밀번호를 기반으로 로그인 요청을 보냅니다.
   - 응답
     ![로그인 응답](https://github.com/user-attachments/assets/0f4cdea6-9138-4c7c-a9b6-5724d68d8fb8)
       > 로그인 성공 시 JWT 토큰과 사용자 정보가 반환됩니다.
</details>


<details>
  <summary><strong>🛒 상품 조회 기능</strong></summary>
  
  ### 전체 판매 상품 조회
   - 요청
     ![판매 상품 목록 조회 요청](https://github.com/user-attachments/assets/49201ca9-4902-41d9-82bb-5fbeaf700b61)
       > 전체 판매 상품을 조회하기 위한 GET 요청입니다.
   - 응답
     ![상품조회 응답](https://github.com/user-attachments/assets/010c8749-c1d7-4b28-a28a-f880e743d1a6)
       > 판매 상품들의 목록과 간략한 정보(이름, 이미지, 가격 등)를 포함한 응답입니다.

  ### 판매 상품 상세 조회
   - 요청
      ![상세조회 요청](https://github.com/user-attachments/assets/f61ff0e7-2eaf-4069-8cc4-9d44e40b6d31)
       > 선택한 판매 상품의 상세 정보를 조회하는 요청입니다.
   - 응답
      ![상세조회 응답](https://github.com/user-attachments/assets/5c7cd83d-a05c-498a-8a2c-90488b9bcb3c)
       > 해당 상품의 상세 정보(구성 상품, 기간별 가격, 설명 등)를 반환합니다.
  ### 판매 상품 전체 검색
   - 요청
      ![상품검색 요청](https://github.com/user-attachments/assets/34f7f6f7-c6b2-48c3-b076-bb2cb67bf5d1)
      > 키워드를 포함한 전체 판매 상품 검색 요청입니다.
   - 응답
     ![상품검색 응답](https://github.com/user-attachments/assets/4b6b9690-7d76-4424-a1a3-861be22fe1a9)
      > 검색어와 관련된 판매 상품 리스트를 반환합니다.
</details>

<details>
  <summary><strong>📦 구독/결제 기능</strong></summary>

  ### 상품 구독
   - 요청
     ![상품구독 요청](https://github.com/user-attachments/assets/6e6d6149-469c-4eb4-b509-4ec374435b21)
      > 사용자가 상품을 구독하기 위한 요청을 보냅니다.
   - 응답
     ![상품구독 응답](https://github.com/user-attachments/assets/9a413c52-ebaf-4579-a862-c5a7996161fe)
      > 구독 요청이 성공적으로 처리되었음을 나타내는 응답입니다.
  ### 장바구니
   - 요청
     ![장바구니 추가 요청](https://github.com/user-attachments/assets/8ca222e0-e464-486f-9128-d8d184dc15b2)
     > 선택한 상품을 장바구니에 추가하는 요청입니다.
   - 응답
     ![장바구니 추가 응답](https://github.com/user-attachments/assets/bcd2f45b-f41d-49a2-bbd7-6286b275ebe7)
      > 상품이 장바구니에 정상적으로 추가되었음을 알리는 응답입니다.
</details>

<details>
  <summary><strong>📞 고객지원 기능</strong></summary>

  ### 수리/분실 신청
  - 요청
    ![수리,분실 신청 요청](https://github.com/user-attachments/assets/af568129-acd7-4d20-8151-46103c62d2d3)
     > 제품 수리 또는 분실 신고 요청입니다.
  - 응답
    ![수리,분실 신청 응답](https://github.com/user-attachments/assets/0ce74e75-272d-46a9-a722-83f722ca3e25)
     > 요청이 정상적으로 접수되었음을 나타내는 응답입니다.
</details>

<details>
  <summary><strong>🤖 AI 챗봇 기능 (MCP 기반)</strong></summary>

### AI 챗봇 제품 검색
 - 요청
  ![MCP 제품 검색 요청](https://github.com/user-attachments/assets/9c586304-ac8e-4362-aef7-089cd0dbcc6c)
 > AI 챗봇에게 원하는 제품을 자연어로 검색 요청합니다.
 - 응답
  ![MCP 제품 검색 응답](https://github.com/user-attachments/assets/a5347670-af57-4558-999a-2980752e525a)
 > 사용자의 요청을 분석한 결과와 함께 추천 제품 목록을 반환합니다.

### AI 챗봇 구독흐름 자동 처리
 - 요청
   ![MCP 구독흐름자동처리 요청](https://github.com/user-attachments/assets/285e0c1d-596e-4394-860f-f303b590b37a)
    > 챗봇이 사용자의 선택을 기반으로 구독 과정을 자동화하여 처리합니다.
 - 응답
   ![MCP 구독흐름자동처리 응답](https://github.com/user-attachments/assets/a6a370f6-8faf-45a0-a5f6-dafd8cf37692)
    > 자동 처리된 구독 결과를 반환합니다.

</details>

<details>
  <summary><strong>👁‍🗨 관리자 기능</strong></summary>

### 상품 등록
- 요청
  ![상품등록 요청](https://github.com/user-attachments/assets/f868583c-2572-4240-9e2f-68038a7e55df)
   > 제품의 이름, 코드, 설명 등을 포함하여 새 상품을 등록합니다.
- 응답
  ![상품등록 응답](https://github.com/user-attachments/assets/39a6e4f0-51fe-4197-80c2-86ac19ded67e)
   > 상품이 성공적으로 등록되었음을 알리는 응답입니다.
  
### 판매 상품 등록
- 요청
   ![판매상품등록 요청](https://github.com/user-attachments/assets/c9ec7fbd-570a-4ca3-b3f1-6bf566b10562)
   > 기존 상품을 기반으로 판매할 상품을 등록합니다.
- 응답
   ![판매상품등록요청 응답](https://github.com/user-attachments/assets/df5776ab-3c44-477e-a0b5-5510eb1d9df7)
   > 판매 상품 등록이 완료되었음을 나타내는 응답입니다.

</details>


---

## 📑 API 명세서

- Swagger: [http://monthlylife-demo.kro.kr/docs/swagger-ui/index.html](http://monthlylife-demo.kro.kr/docs/swagger-ui/index.html)

---

## 🔍 핵심 로직 상세 설명
### [프로젝트 WIki](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-BE/wiki/%EC%A3%BC%EC%9A%94%EA%B8%B0%EB%8A%A5_%EB%B0%8F_%EC%84%A4%EA%B3%84%ED%9D%90%EB%A6%84)

---

## 📂 프로젝트 폴더 바로가기
- [📃 Frontend Repository](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-FE/blob/develop/README.md)
- [📃 Devops/Infra](https://github.com/beyond-sw-camp/be12-fin-Gongcha1-MonthlyLIfe-BE/blob/develop/devops/README.md)

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
