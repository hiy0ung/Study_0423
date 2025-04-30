## API 엔드포인트
- 'GET /api/todos' : 모든 할 일 목록 조회
- 'GET /api/todos/{id}' : 특정 할 일 조회
- 'POST /api/todos' : 새로운 할 일 생성
- 'PUT /api/todos/{id}' : 기존 할 일 수정
- 'DELETE /api/todos/{id}': 할 일 삭제

- 계층적 아키텍처
- DTO 패턴
- 예외처리
- 시간이 있다면 테스트 코드 간단하게 구성
- 내 스타일대로 짜기

- model
```text
id,
title,
description,
boolean completed,
createdAt,
updatedAt
```