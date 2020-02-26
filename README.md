# Step3 

## step2 추가 학습 
- Controller에서 View로 모델로 데이터를 한 개만 넘기는 상황. 그 때 view에서 해당 model 데이터를 사용하려면 코드를 {{#user}} ... {{/user}}로 감싸야 한다. 기존에는 {{user.name}} 이렇게 접근함. 
- 개인정보 수정 시 user 객체에 update 메서드 추가한다. update에서 개인정보 수정하고 DB에 넣기. 훨씬 코드가 간결해짐. 

