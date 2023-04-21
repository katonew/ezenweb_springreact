import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';

export default function Login(props) {

    // 2. 로그인
    //function onlogin(){
    const onLogin = ()=>{
        console.log('onLogin s')
        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData(loginForm);
        // axios Form전송
        axios.post("http://localhost:8080/member/login", loginFormData)
            .then( r=>{
                console.log(r)
                if(r.data==false){
                    alert("동일한 회원 정보가 없습니다.")
                }else{
                    alert("로그인 성공")
                    // JS 로컬 스토리지에 로그인 성공한 흔적 남기기
                    window.location.href="/"
                }
            })
    }

    return (<>
        <h3>로그인 페이지</h3>
        <form className="loginForm">
            아이디[이메일] : <input type="text" name="memail" /> <br/>
            비밀번호 : <input type="password" name="mpassword" /> <br/>
            <button onClick={onLogin} type="button">로그인</button>
            <a href="/oauth2/authorization/google">구글로그인</a>
            <a href="/oauth2/authorization/kakao">카카오로그인</a>
            <a href="/oauth2/authorization/naver">네이버로그인</a>
        </form>
    </>)
}