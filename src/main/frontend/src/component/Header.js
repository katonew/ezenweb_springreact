import React, { useState , useEffect }  from 'react';
import axios from 'axios'
export default function Header(props) {
    // let [ login , setLogin] = useState( JSON.parse( localStorage.getItem("login_token") ) );
     // 로그인상태
    let [ login , setLogin] = useState( null );
    // 로그아웃
    const logOut = ()=>{
        // JS 세션 초기화
        sessionStorage.setItem("login_token" , null );
        axios.get("/member/logout").then( r=>{ console.log(r); });  // 백엔드의 인증세션 지우기
        //window.location.href="member/login";
        setLogin(null);
    }
    // 로그인 상태 호출
    useEffect(()=>{
        axios.get("/member/info")
            .then(r=>{
                console.log(r);
                if(r.data != ''){ // 로그인이 되어 있으면
                    // JS 로컬 스토리지에 저장
                    sessionStorage.setItem("login_token" , JSON.stringify(r.data));
                    // 상태변수에 로컬 스토리지 데이터 저장 [ 렌더링 하기 위해 ]
                    setLogin(JSON.parse(sessionStorage.getItem("login_token") ) );
                }
            })
    }, [])
    return (<>
        <div>
            <a href="/">Home</a>
            <a href="/board/list"> 게시판 </a>
            <a href="/AppTodo"> todo </a>
            <a href="/admin/dashboard"> 관리자 </a>
            {
                login == null
                ? ( <>
                        <a href="/member/login"> 로그인 </a>
                        <a href="/member/signup"> 회원가입 </a>
                        <a href="/member/find"> 아이디/비밀번호찾기 </a>
                    </> )
                : ( <>
                        <button onClick={ logOut }>로그아웃</button>
                    </> )
            }
        </div>
    </>)
}