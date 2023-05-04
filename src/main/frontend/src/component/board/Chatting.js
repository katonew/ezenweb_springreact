import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css'
import axios from 'axios';

export default function Chatting(props) {

    let [id,setId] = useState(''); // 익명채팅방에서 사용할 id [난수 ]
    let [ msgContent, setMsgContent ] = useState([]); // 현재 채팅중인 메세지를 저장하는 변수
    let msfInput = useRef(null); // 채팅 입력창[input] DOM 객체 제어
    let fileForm = useRef(null); //
    let fileInput = useRef(null); //
    let chatContentBox = useRef(null);

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
            time : new Date().toLocaleTimeString(), // 현재 시간만
            type : 'msg'
        };
        if(msgbox.msg != ''){ // 내용이 있으면 메시지 전송
            console.log(msgbox)
            clientSocket.current.send(JSON.stringify(msgbox)); // 클라이언트 메세지 전송 [ .send()]
            msfInput.current.value = '';
        }
        // 2. 첨부파일 전송 [ axios를 이용한 첨부파일 업로드 ]
        if(fileInput.current.value != ''){ // 첨부파일이 존재하면
            let formData = new FormData(fileForm.current);
            fileAxios(formData);
        }
    }

    // 5. 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect ( ()=>{
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight;
    }, [msgContent])

    // 6. 파일전송 axios
    const fileAxios = (formData)=>{
        axios.post("/chat/fileupload" , formData).then( r =>{
            console.log(r.data);
            // 다른 소켓들에게 업로드 결과 전달
            let msgbox = {
                id : id, // 보낸사람
                msg : msfInput.current.value,
                time : new Date().toLocaleTimeString(), // 현재 시간만
                type : 'file',
                fileInfo : r.data // 서버 업로드 후 응답받은 파일정보
            };
            clientSocket.current.send(JSON.stringify(msgbox));
            fileInput.current.value = '';
        })
    }


    return ( <>
        <Container>
            <h6>익명 채팅방</h6>
            <div
                ref={chatContentBox}
                className="chatContentBox"
                onDragEnter = { (e)=>{
                        e.preventDefault();  {/* 상위 이벤트 제거 */}
                 } }
                onDragOver = { (e)=>{
                        e.preventDefault();  {/* 상위 이벤트 제거 */}
                        e.target.style.backgroundColor = "#e8e8e8"
                } }
                onDragLeave = { (e)=>{
                        e.preventDefault();  {/* 상위 이벤트 제거 */}
                        e.target.style.backgroundColor = "#ffffff"
                } }
                onDrop = { (e)=>{
                        e.preventDefault();  {/* 상위 이벤트 제거 */}
                        {/* 드랍된 파일들을 호출  = e.dataTransfer.files */}
                        let files = e.dataTransfer.files;
                        for( let i=0; i<files.length; i++ ){
                            if( files[i] != null && !files[i] != undefined ){ {/* 파일이 존재하면 */}

                                let formData = new FormData(fileForm.current);
                                formData.set('attachFile', files[i]);
                                fileAxios(formData);
                            }
                        }

                } }
                >
                {
                    msgContent.map( (m)=>{
                        return (<>
                            <div className="chatContent" style={ m.id == id ? {backgroundColor : 'gray' } : { backgroundColor : 'red'} }>
                                <span> {m.id} </span>
                                <span> {
                                    m.type == 'msg' ?
                                    <span> {m.msg} </span> :
                                    (<>
                                        <span>
                                            <span> {m.fileInfo.originalFilename} </span>
                                            <span> {m.fileInfo.sizeKb} </span>
                                            <span> <a href={"/chat/filedownload?uuidFile="+m.fileInfo.uuidFile}> 저장 </a> </span>
                                        </span>
                                    </>)
                                } </span>
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
                <form ref={fileForm}>
                    <input ref={fileInput} type="file" name="attachFile" />
                </form>
            </div>
        </Container>


    </>)
}