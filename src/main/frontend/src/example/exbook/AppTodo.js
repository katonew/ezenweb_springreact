// 교재 App 컴포넌트 -> AppTodo 컴포넌트
import React, {useState, useEffect} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {List, Paper, Container} from '@mui/material';
import axios from 'axios'; // npm install axios [ install -> i ]

export default function AppTodo(props) {


    const [ items, setItems ] = useState( [ ] )

    // 2. items에 새 item을 등록하는 함수
    const addItem = (item) =>{
        setItems([...items, item ] ); // 기존 상태 items에 item 추가
        // setItems([...기본배열, 추가할 데이터 ])
        axios.post("http://192.168.17.54:8080/todo", { title : item.title }).then(r=>{console.log(r)});
    }

    // 컴포넌트가 실행 될 때 한번 이벤트 발생
    useEffect( ()=>{
        // ajax : jquery 설치가 필요
        // fetch : 리액트 전송 비동기 통신 함수 [ 리액트 내장 / 설치 X ]
        // axios : 리액트 외부 라이브러리 [ 설치 필요 ] ==> JSON 통신 기본값.
        axios.get("http://192.168.17.54:8080/todo")
                .then(r=>{
                        console.log(r)
                        setItems(r.data);
                    }
                );
        console.log(setItems);
         // 해당 주소에 매핑되는 컨트롤/메소드에 @CrossOrigin(origins = "http://localhost:3000") 추가
    }, [])

    // 3. items에서 item 삭제
    const deleteItem = (item)=>{
    // 만약에 items에 있는 item 중 id와 삭제할 id와 다른 경우 해당 item 반환
        const newItems = items.filter( i => i.id !== item.id);
        console.log(item.id);
        axios.delete("http://192.168.17.54:8080/todo", {params : { id : item.id }}).then(r=>{ console.log(r);});

            // 삭제할 id를 제외한 newItems배열 선언
        setItems([...newItems]);
    }
    // JS 반복문 함수 제공
                // 배열/리스트.forEach((o)=>{}) : 반복문만 가능 , 리턴 X
                // 배열/리스트.map((o)=>{실행문}) : + return 값들을 새로운 배열에 저장
                // 배열/리스트.filter((o)=>{조건}) : + 조건이 충족 할 경우 객체를 반환

    // 4. 수정함수
    const editItem = ()=>{
        setItems([...items]); // 재렌더링
    }



    // 반복문 이용한 Todo 컴포넌트 생성
    //JSX의 style 속성 방법
    let TodoItems =
        <Paper style={{margin : 16}}>
            <List>
                {
                    items.map( (i)=>
                        <Todo
                            item ={ i }
                            key = { i.id }
                            deleteItem = { deleteItem }
                            editItem = { editItem } />
                    )
                }
            </List>
        </Paper>

    return (<>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem}  />
                {TodoItems}
            </Container>
        </div>
    </>);
}