console.log('member js 시작')

function onSignup(){
    let info = {
        memail : document.querySelector('.memail').value,
        mpassword : document.querySelector('.mpassword').value,
        mname : document.querySelector('.mname').value,
        mphone : document.querySelector('.mphone').value,
    }

    $.ajax({
        url : "/member/info",
        method : "post",
        contentType : "application/json",
        data : JSON.stringify(info),
        success : (r)=>{
            console.log(r);
            if(r==1){
                alert('회원가입 성공');
                location.href = "/";
            }else if(r==2){
                alert('회원가입 실패');
            }else if(r==3){
                alert('이미 가입된 아이디입니다.');
                location.href = href="/member/login"
            }
        }
    })
}
/*
시큐리티 사용으로 폼전송으로 로그인
function onLogin(){
    let info = {
        memail : document.querySelector('.memail').value,
        mpassword : document.querySelector('.mpassword').value,
    }

    $.ajax({
        url : "/member/login",
        method : "post",
        contentType : "application/json",
        data : JSON.stringify(info),
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('로그인 성공');
                location.href = "/";
            }
        }
    })
}
*/

getMember()
function getMember(){
    console.log('getMember 시작')
    $.ajax({
            url : "/member/info",
            method : "get",
            success : (r)=>{
                console.log(r)
                console.log(typeof(r))
                let html = '';
                if(!r){
                    html = `<a href="/member/signup"> 회원가입 </a>
                            <a href="/member/login"> 로그인 </a>
                            <a href="/member/find"> 아이디/비밀번호 찾기 </a>`
                }else{
                    html = `${r.mname}님 반갑습니다.
                        <a href="/member/logout">로그아웃</a>
                        <a href="/member/update">회원정보수정</a>
                        <a onclick="onDelete()">회원탈퇴</a>
                    `
                }
                document.querySelector('.infobox').innerHTML = html;
            }
        })
}

/*
function getLogout(){
    $.ajax({
        url : "/member/logout",
        method : "get",
        success : (r)=>{
            console.log(r);
        }
    })
}
*/

// 아이디 찾기
function findId(){
     let info = {
             mname : document.querySelector('.mname').value,
             mphone : document.querySelector('.mphone').value,
         }
         $.ajax({
             url : "/member/findId",
             method : "post",
             contentType : "application/json",
             data : JSON.stringify(info),
             success : (r)=>{
                 console.log(r);
                 if(r){
                     document.querySelector('.resultBox').innerHTML = '찾은 아이디 : ' + r;
                 }else{
                     document.querySelector('.resultBox').innerHTML = '입력된 정보가 잘못되었습니다.'
                 }
             }
         })

 }

 // 비밀번호 찾기
 function findPw(){
     let info = {
             memail : document.querySelector('.memail').value,
             mphone : document.querySelector('.mphone2').value,
         }
         $.ajax({
             url : "/member/findPw",
             method : "post",
             contentType : "application/json",
             data : JSON.stringify(info),
             success : (r)=>{
                 console.log(r);
                 if(r){
                     document.querySelector('.resultBox').innerHTML = '변경된 비밀번호 :'+ r;
                 }else{
                     document.querySelector('.resultBox').innerHTML = '입력된 정보가 잘못되었습니다.'
                 }
             }
         })

 }

// 회원탈퇴
function onDelete(){
    let mpassword = prompt('비밀번호를 입력해주세요');
    $.ajax({
        url : "member/info",
        method : "delete",
        data : {"mpassword" : mpassword},
        success : (r)=>{
            console.log(r);
            if(r==true){
                alert('회원탈퇴 성공');
                location.href = "/member/logout";
            }else{
                alert('회원탈퇴 실패');
            }
        }
    })
}