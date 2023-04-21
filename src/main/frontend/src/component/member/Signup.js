import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Signup(props) {
    //function onSignup(){
    // 변수형 익명함수 변환
    const onSignup = () =>{
        console.log('onSignup open')
        let info = {
            memail : document.querySelector('.memail').value,
            mpassword : document.querySelector('.mpassword').value,
            mname : document.querySelector('.mname').value,
            mphone : document.querySelector('.mphone').value,
        }
        console.log(info)
        // ajax -> axios
        axios.post("http://localhost:8080/member/info" , info)
            .then( r =>{
                console.log(r);
                if(r.data==1){
                    alert('회원가입 성공');
                    window.location.href = "/login";
                }else if(r.data==2){
                    alert('회원가입 실패');
                }else if(r.data==3){
                    alert('이미 가입된 아이디입니다.');
                    window.location.href="/login"
                }
            })
            .catch(err=>{console.log(err)});
    }

    return (<>
            <h3>회원가입 페이지</h3>
            <div>
                아이디[이메일] : <input type="text" className="memail" /> <br/>
                비밀번호 : <input type="password" className="mpassword" /> <br/>
                이름 : <input type="text" className="mname" /> <br/>
                전화번호 : <input type="text" className="mphone" /> <br/>
                <button onClick={onSignup} type="button" >가입</button>
            </div>
    </>)
}

/*
    HTML -> JSX
        1. <> </>
        2. class => className
        3.style => style={{}}
        4. 카멜표기법 :
            onclick => onClick
            margin-top => marginTop

*/

