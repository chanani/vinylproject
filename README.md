<img width="1169" alt="MainPagePC" src="https://github.com/chanani/vinylproject/assets/130114269/02ccd190-c92a-447b-aa79-0e2d9224ae6b">

## Endpoints


## 목차

- 개발 환경
- 사용 기술
    - 백엔드
    - 프론트엔드
    - 기타 라이브러리
- 핵심 키워드
- 아키텍처
- E-R 다이어그램
- 프로젝트 목적
- 핵심 기능
- 프로젝트를 통해 느낀점



## 개발환경

- IntelliJ
- GitHub
- Mysql WorkBench
- AWS RDS



## 사용 기술

- 백엔드
    - 주요 프레임워크 / 라이브러리
        - JAVA 11 Openjdk
        - SpringBoot 2.7.16
        - Mybatis
        - SpringBoot Security5
        - SpringBoot JWT
    - Build tool
        - Gradle
    - Database
        - Mysql
        - AWS RDS
    - Infra
        - AWS S3 
        - Docker
        - Nginx
        - GitHub Actions
- 프론트엔드
    - Html/Css/Java script
    - Bootstrap3
- 기타 라이브러리
    - Lombok
    - Gmail SMTP



## 핵심 키워드

- SpringBoot를 통해 홀로 기획과 개발 등 모든 과정에 대한 경험을 쌓았습니다.
- MVC2 모델을 기반으로 백엔드 서버를 구축하였습니다.
- Html, Css, Java script를 통해 모든 UI 직접 제작하였습니다.
- Security와 JWT를 이용한 로그인 기능 구현하였습니다.
- SMTP 라이브러리를 이용한 Email 발송 및 비밀번호 초기화 기능 구현하였습니다.
- Open API(카카오 로그인, 카카오 맵, 네이버 로그인) 적용하였습니다.
- AWS S3를 이용한 상품 이미지 업로드와 AWS RDS(MySQL)를 적용하였습니다.
- AWS EC2, Docker, Nginx를 통한 무중단 배포, GitHub Actions를 통한 CI/CD 구현하였습니다.


## 아키텍처




## ERD 다이어그램

<img width="770" alt="vinylErd" src="https://github.com/chanani/vinylproject/assets/130114269/23eb00a9-8818-467d-a83c-63ead9d101c9">



## 프로젝트 목적

- LP 쇼핑몰 프로젝트를 기획한 이유?
    
    네이버 스마트 스토어를 통해 직접 LP 판매업을 진행한 경험을 바탕으로 저의 쇼핑몰을 구현해보고자 기획하고 진행하게 되었습니다.
    



## 핵심 기능

- Securuty와 JWT을 이용하여 구현한 로그인 기능
    
    이전에 진행한 ITTAM에서 사용한 Security와 JWT를 이용하지 않고 Session을 통해 로그인 정보를 관리해볼 수 있는 다양한 경험을 쌓아 보고싶어 Session을 통해 로그인 기능을 구현했습니다. 
    
    카카오, 네이버 API를 통해 소셜 로그인까지 추가로 구현할 예정입니다.
  
    
- AWS S3를 이용한 상품 이미지 업로드 기능

    상품 등록 시 AWS S3 버킷을 통해 이미지를 등록할 수 있으며, 대표이미지와 상세이미지로 구분되어 다수의 이미지를 등록할 수 있도록 구현되어 있습니다.
    

- Open API를 이용한 로그인 기능
    
    카카오, 네이버 Open API를 이용하여 소셜 로그인 기능을 구현하였으며, 로그인 이외 카카오 맵 API를 통한 주소입력 기능까지 제작하였습니다.


- AWS EC2, Docker, Nginx를 통한 무중단 배포 및 GitHub Actions를 통한 CI/CD 구현

    AWS EC2 Ubuntu 환경에 Docker와 Nginx를 활용하여 중단되지 않는 서버를 설계하였으며, GitHub Actions를 통해 Push될 경우 자동으로 배포될 수 있도록 제작하였습니다.


    
## 프로젝트를 통해 느낀 점

모든 기획과 UI 제작, 홈페이지에서 필요한 기능까지 팀이 아닌 첫 개인 프로젝트였습니다. 
보완해야 할 부분도 많지만 추후 부족한 부분에 대해서는 학습과 함께 수정해 나아갈 예정입니다.
처음 접해보는 무중단 자동화 배포를 진행하면서 다양한 오류를 맞닥뜨렸지만 오류에 대한 해결 과정을 통해 지속적으로 성장할 수 있었습니다.
개발자라는 직업으로써 제가 생각만 하던 것들을 실체화 시킨다는 것에 많은 희열을 느낄 수 있는 좋은 경험이 되었고 보완점이 필요한 부분을 보완한 뒤 배포까지 진행해 볼 예정입니다.

