import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css'

export default function Chatting(props) {

    let [id,setId] = useState(''); // 익명채팅방에서 사용할 id [난수 ]
    let [ msgContent, setMsgContent ] = useState([]); // 현재 채팅중인 메세지를 저장하는 변수
    let msfInput = useRef(null); // 채팅 입력창[input] DOM 객체 제어

    let clientSocket = useRef(null); // webSocket

    useEffect( ()=>{ // 재 렌더링 시 1번만 실행
        if(!clientSocket.current){ // 만약 클라이언트 소켓이 접속이 안되어 있을때
            clientSocket.current = new WebSocket('ws://localhost:8080/chat');
            console.log(clientSocket);
             clientSocket.current.onopen = ()=>{  // 서버에 접속했을때
                console.log('서버 접속했습니다');
                let randId = Math.floor(Math.random() * (9999-1) + 1); // 1~9999 난수
                setId("익명"+randId);
            }
            clientSocket.current.onclose = ()=>{ console.log('서버 나갔습니다'); }// 서버에서 나갔을때
            clientSocket.current.onerror = ()=>{ console.log('소켓 오류'); } // 에러 발생 시
            clientSocket.current.onmessage = (e)=>{ // 서버에서 메세지가 왔을때
                    console.log('서버소켓으로부터 메세지 수신');
                    //console.log(e.data)
                    let data =  JSON.parse(e.data)
                    //console.log(data)
                    //msgContent.push(data); // 배열에 내용 추가
                    setMsgContent( (msgContent) => [ ...msgContent, data ] ); // 재 렌더링
                    //console.log(msgContent);
            }
        }
    })

    // 4. 메세지 전송
    const onSend = ()=>{
        // msginput변수가 참조중인 dom 객체의 내용물을 호출
        let msgbox = {
            id : id, // 보낸사람
            msg : msfInput.current.value,
            time : new Date().toLocaleTimeString() // 현재 시간만
        };
        console.log(msgbox)
        clientSocket.current.send(JSON.stringify(msgbox)); // 클라이언트 메세지 전송 [ .send()]
        msfInput.current.value = '';
    }

    // 5. 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect ( ()=>{
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight;
    }, [msgContent])




    return ( <>
        <Container>
            <h6>익명 채팅방</h6>
            <div className="chatContentBox">
                {
                    msgContent.map( (m)=>{
                        return (<>
                            <div className="chatContent" style={ m.id == id ? {backgroundColor : 'gray' } : { backgroundColor : 'red'} }>
                                <span> {m.id} </span>
                                <span> {m.msg} </span>
                                <span> {m.time} </span>
                            </div>
                        </>)
                    })
                }
            </div>
            <div className="chatInputBox">
                <span> {id} </span>
                <input className="msgInput" ref={msfInput} type="text" />
                <button onClick={onSend} type="button"> 전송 </button>
            </div>
        </Container>


    </>)
}