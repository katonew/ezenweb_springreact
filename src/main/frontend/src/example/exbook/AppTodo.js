// 교재 App 컴포넌트 -> AppTodo 컴포넌트
import React, {useState} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {List, Paper, Container} from '@mui/material';

export default function AppTodo(props) {


    const [ items, setItems ] = useState( [ ] )

    // 2. items에 새 item을 등록하는 함수
    const addItem = (item) =>{
        item.id = "ID-" + setItems.length
        item.done = false;
        setItems([...items, item ] ); // 기존 상태 items에 item 추가
        // setItems([...기본배열, 추가할 데이터 ])
    }


    // 반복문 이용한 Todo 컴포넌트 생성
    //JSX의 style 속성 방법
    let TodoItems =
        <Paper style={{margin : 16}}>
            <List>
            {
                items.map( (i)=>
                        <Todo item = {i} />
                )
            }
            </List>
        </Paper>

    return (<>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
                {TodoItems}
            </Container>
        </div>
    </>);
}