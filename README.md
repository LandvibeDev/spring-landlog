# spring-landlog


## 과제 제출 방법

1. 자신의 레포로 fork
2. 과제 구현
3. 각자 자신의 branch로 PR
   - 권용현: yonghyun
   - 김동하: dongha
   - 김수환: suhwan
   - 박준영: junyoung
   - 양재승: jaeseung
   - 오형석: hyungseok
   - 이세원: sewon
   - 이승철: seungcheol
   - 임주민: jumin

### 기능 구현
issue 1. 로그인 구현

회원가입 된 회원 정보로 로그인 구현

1. 회원가입 필드 추가
   1. 이메일 / 패스워드 추가 
      1. members/createMemberForm.html 수정 -> email, password 추가
      2. memberForm 객체 수정 -> email, password 추가
      3. member 객체 수정 -> email, password 추가, 생성자 수정(id를 제외하고 객체 생성), set 함수 삭제
      4. memberController.create 함수 수정 -> 객체 생성 수정
      5. memberService.join 의 validate 로직 수정 -> validateDuplicateMember에 email 중복 확인 코드 추가
      6. members/memberList.html 수정 -> email, password 추가
      
2. 홈 화면
   1. Path: /
   2. 로그인 버튼 추가
   3. 클릭 -> /members/login 으로 이동
      => home.html 수정 -> 로그인 버튼 추가(/members/login 으로 보내기)
   
3. 로그인 페이지
   1. Path: /members/login
      1. LoginController 생성 -> @GetMapping("/members/login") 함수 만들어서 loginForm.html과 바인딩
      2. loginForm.html 생성
   

   2. 성공 -> 블로그 리스트 페이지(/blogs?creatorId=1)
      실패 -> 홈(/)
      1. memberService에 login 함수 만들기 
      2. memberService에 validateLogin 함수 만들기
         1. findByEmail해서 null X
         2. password 가 일치
      3. LoginController에 post 방식의 login하는 함수 , 데이터 받아올 form 생성 
         1. memberService.login 활용
         2. 성공시 redirect:/blogs
         3. 실패시 redirect:/
   3. loginForm.html


4. 블로그 페이지
   1. Path: /blogs
      Title: ${name}의 블로그
      1. blogController 추가 (MemberRepository 의존)
         1. blogForm : parameter로 creatorId를 받아옴. name을 찾고, model에 주입해 blogs/blogList.html과 바인딩
      
5. unit test 추가
   1. LoginServiceTest : 추가(정상수행, 이메일 존재 X, 비밀번호 불일치 예외)
   2. MemberServiceTest : "중복_이메일_예외" 추가
   3. MemoryMemberRepositoryTest : 객체 생성 방법 수정(memberForm을 생성자에 주입), findById 추가