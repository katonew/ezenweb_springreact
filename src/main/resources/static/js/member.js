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
            if(r==true){alert('회원가입 성공');}
        }
    })
}

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
            if(r==true){alert('로그인 성공');}
        }
    })
}