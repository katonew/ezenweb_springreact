import React from 'react';
import axios from 'axios';
export default function Find( props ) {

    const findId = ()=>{
        let mname = document.querySelector('.mname').value;
        let mphone = document.querySelector('.mphone').value;
        axios.post("/member/findId" , {mname : mname , mphone : mphone}).then( r=>{
            console.log(r);
            document.querySelector('.resultBox').innerHTML = '찾은 아이디 : ' + r.data
            document.querySelector('.mname').value = ''
            document.querySelector('.mphone').value = ''
        })
    }

    const findPw = ()=>{
        let memail = document.querySelector('.memail').value;
        let mphone2 = document.querySelector('.mphone2').value;
        axios.post("/member/findPw" , {memail : memail , mphone : mphone2}).then( r=>{
        console.log(r)
            if(r.data==0){
                document.querySelector('.resultBox').innerHTML = '입력정보가 틀렸습니다.'
            }else{
                document.querySelector('.resultBox').innerHTML = '임시 비밀번호 : ' + r.data
                document.querySelector('.memail').value = ''
                document.querySelector('.mphone2').value = ''
            }

        })
    }

    /*
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
    */




    return ( <>
        <div>
            <a href="/"> 홈으로</a>
            <h3>아이디 찾기</h3>
            <form className="findIdForm">
              이름 : <input type="text" className="mname" />
              전화번호 : <input type="text" className="mphone" />
              <button onClick={findId} type="button">아이디 찾기</button>
            </form>
            <h3>비밀번호 찾기</h3>
            <form className="findPwForm">
                아이디[이메일] : <input type="text" className="memail" />
                전화번호 : <input type="text" className="mphone2" />
                <button onClick={findPw} type="button">비밀번호 찾기</button>
            </form>
            <div  className="resultBox"></div>
        </div>
     </> );
}