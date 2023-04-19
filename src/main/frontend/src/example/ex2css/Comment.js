// Comment js [ *카멜표기법 사용 ]
    // class -> className
import React from 'react'
import styles from './Comment.css' // css 가져오기
// img 파일 가져오기
import logo from '../../logo.svg'

export default function Comment(props) {

    return (<>
        <div class="wrapper">
            <div>
                <img class="logoImg" src= {logo} />
            </div>

            <div class="contentContainer">
                <div class="nameText"> {props.name} </div>
                <div class="commentText"> {props.comment} </div>
            </div>
        </div>
    </>)
}