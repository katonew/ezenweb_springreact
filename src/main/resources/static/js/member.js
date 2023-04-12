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
            if(r==true){
                alert('회원가입 성공');
                location.href = "/";
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
                            <a href="#"> 아이디 찾기 </a>
                            <a href="#"> 비밀번호 찾기 </a>`
                }else{
                    html = `${r.mname}님 반갑습니다.
                        <button type="button">로그아웃</button>
                        <button type="button">회원정보수정</button>
                        <button type="button">회원탈퇴</button>
                    `
                }
                document.querySelector('.infobox').innerHTML = html;
            }
        })
}

/*
function getLogout(){
    $.ajax({
        url : "member/logout",
        method : "get",
        success : (r)=>{
            location.href = "/";
        }
    })
}
*/