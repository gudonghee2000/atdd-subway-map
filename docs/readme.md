# 지하철 노선도 관리 1, 2단계

## 기능 요구 사항

### 1. 지하철 역 관리 API

- [x] 같은 이름 지하철역 생성 불가와 같은 기능을 추가
- [x] 지하철 역 삭제 기능
- [x] 지하철 역 입력 검증 (ex. XX역)

### 2. 지하철 노선 관리 API

- [x] 지하철 노선 생성
- [x] 노선 목록 조회 : 노선에 포함된 역 목록을 함께 응답하기
- [x] 노선 단일 조회 : 노선에 포함된 역 목록을 함께 응답하기
- [x] 노선 수정
- [x] 노선 삭제

# 지하철 노선도 관리 3단계

- [x] 지하철 노선 추가
    - [x] `upStationId`, `downStationId`, `distance` 데이터도 함께 노선에 추가
    - [x] 노선 생성 시 상행 종점/하행 종점을 입력 받는다.
       - [x] 상/하행 종점역이 같을 경우 예외처리
    - [x] 노선 생성 시 두 역의 거리를 입력 받는다.
       - [x] 거리는 0 이하일 경우 예외처리

- [x] 지하철 노선 조회 시 구간에 포함된 역 목록 응답

- [x] 지하철 구간 추가
    - [x] 구간 생성 시 상행역/하행역을 입력받는다.
        - [x] 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없음

        - [x] 구간 생성 시 두 역의 거리를 입력
            - [x] 역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없음

    - [x] 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음

- [x] 지하철 구간 삭제
    - [x] 구간이 하나인 노선에서 마지막 구간을 제거할 수 없음
    
- [x] 역이 노선에 등록되어 있는 경우 삭제할 수 없다.