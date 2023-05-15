import React,{ useState , useEffect, useRef } from 'react';
import axios from 'axios';

export default function ProductWrite(props){

    const writeForm = useRef(null); //useRef() 객체 반환

    const onWriteHandler = () =>{
        let writeFormData = new FormData(writeForm.current)
        axios.post("/product", writeFormData).then( r =>{
            if(r.data==true){
                alert("등록 성공")
                props.handleTabsChange(null,3);
            }
            else{alert("등록 실패")}
        })
    }

    return(<>
        <form ref={writeForm}>
            제품명 <input type="text" name="pname" />
            제품가격 <input type="text" name="pprice" />
            제품 카테고리 <input type="text" name="pcategory" />
            제품설명 <input type="text" name="pcomment" />
            제품제조사 <input type="text" name="pmanufacturer" />
            제품초기상태 <input type="text" name="pstate" />
            제품재고 <input type="text" name="pstock" />
            제품이미지 <input
                            type="file" multiple
                            name="pimgs"
                            accept="image/gif,image/png,image/jpeg"
                            />
            <button type="button" onClick={onWriteHandler}>제품 등록</button>
        </form>
    </>)
}