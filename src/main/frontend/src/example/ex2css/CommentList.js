// CommentList js

import React from 'react'
import Comment from './Comment'
export default function CommentList(props) {

    // ajax 이용한 서버로 부터 받은 데이터 [ JSON ] 예시
    let r = [
        { name : " 유재석", comment : "안녕하세요"},
        { name : " 강호동", comment : "반갑습니다"},
        { name : " 신동엽", comment : "반가워요"}
    ]
    console.log(r);
    // return 안에서 js 문 사용 시 {}
    // JSX 주석 => {/* 주석 내용 */}
    // return map[return O] VS forEach[return X]
    return (<>
        <div>
            {   /* jsx 시작 */
                r.map( (c)=>{
                return (<Comment name={c.name} comment={c.comment} />)
            })
            /* jsx 끝 */
            }
        </div>

    </>)
}