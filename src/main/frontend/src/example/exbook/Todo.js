// p.183 컴포넌트 만들기
import React, {useState} from 'react';
// 외부 컴포넌트 호출
import {ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton} from '@mui/material';
// 삭제 아이콘
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';
import axios from 'axios'; // npm install axios [ install -> i ]

export default function Todo(props) {

    // 1. Hook 상태관리 useState
    const [ item, setItem ] = useState(props.item);

    // 2. props 전달된 삭제함수 변수로 이동
    const deleteItem = props.deleteItem;

    // 3. 삭제함수 이벤트처리 핸들러
    const deleteEventHandler = ()=>{
        deleteItem(item);
    }


    // 4. readOnly = true 초기화가 된 필드/변수와 해당 필드를 수정할 수 있는 함수 estReadOnly [배열]
    const [readOnly , setReadOnly ] = useState(true);

    // 5. 읽기 모드 해제
    const turnOffReadOnly = ()=>{ //console.log('turnOffReadOnly')
        setReadOnly(false); // readOnly = true 수정 불가능
    }

    // 6. 엔터키 눌렀을때 - 수정 완료
    const turnOnReadOnly = (e)=>{ //console.log('turnOnReadOnly')
        if(e.key==="Enter"){
            setReadOnly(true);
            axios.put("/todo",{ id : item.id, done : item.done ,title : item.title }).then(r=>{console.log(r)});
        }
    }

    // 7. 입력받은 값을 변경
    let editItem = props.editItem
    const editEventHandler = (e)=>{ //console.log('editEventHandler')
    // inputBase의 값이 변경될때마다 item.title이 변경
        item.title = e.target.value;
        editItem();
    }

    // 8. 체크박스 업데이트
    const checkboxEventHandler = (e)=>{
        item.done = e.target.checked; //checked : 체크일 경우 true
        editItem(); // 상위 컴포넌트 렌더링
        axios.put("/todo",{ id : item.id, done : item.done ,title : item.title }).then(r=>{console.log(r)});
    }

    return (<>
        <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler}  />
            <ListItemText>
                <InputBase
                    inputProps={{readOnly : readOnly}}
                    onClick = {turnOffReadOnly}
                    onKeyDown = {turnOnReadOnly}
                    onChange = {editEventHandler}
                    type="text"
                    id={`${item.id}`}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </>);
}