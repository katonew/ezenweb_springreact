import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // 1. import 이용해 App 컴포넌트[함수] 를 불러온다
import reportWebVitals from './reportWebVitals';
import Book from './example/ex1component/Book';
import Product from './example/ex1component/Product';
import ProductList from './example/ex1component/ProductList';
import Clock from './example/ex1component/Clock';
import Comment from './example/ex2css/Comment';
import CommentList from './example/ex2css/CommentList';
import AppTodo from './example/exbook/AppTodo';
import Hook1 from './example/ex3hook/Hook1'
import Hook2 from './example/ex3hook/Hook2'
import Index from './component/Index'
// 1. HTML에 존재하는 div 가져오기
// 2. ReactDOM.createRoot(div) : 해당 div를 react root로 사용하여 root 객체 생성
const root = ReactDOM.createRoot(document.getElementById('root'));

// 수업용 컴포넌트
root.render(
        <Index />
)




/*
// 7. 교재 Todo 적용
root.render(
    //<React.StrictMode>
        <AppTodo />
    //</React.StrictMode>
)
// 8. 훅 예제

root.render(
    //<React.StrictMode>
        <Hook2 />
    //</React.StrictMode>
)

// 6. 예제6
root.render(
    <React.StrictMode>
        <CommentList />
    </React.StrictMode>
)

// 5. 예제5
root.render(
    <React.StrictMode>
        <Comment />
    </React.StrictMode>
)


// 4. 예제4 렌더링 반복
// 1초마다 해당코드 실행 : setInterval( () => {}, 1000);
setInterval( () => {
    root.render(
        <React.StrictMode>
            <Clock />
        </React.StrictMode>
    )
}, 1000);



// 3. 예제3 컴포넌트에 컴포넌트 포함
root.render(
    <React.StrictMode>
        <ProductList />
    </React.StrictMode>
)

// 2. 예제 2 개발자 정의 컴포넌트 렌더링
root.render(
    <React.StrictMode>
        <Product />
    </React.StrictMode>
)

// 1. 예제 1 컴포넌트
root.render(
    <React.StrictMode>
        <Book />
    </React.StrictMode>
);*/







// 3. roo.reander() : 해당 root객체(가져온 div)의 컴포넌트 렌더링
/*
root.render(
  <React.StrictMode>
  //  2. <컴포넌트> 이용해 컴포넌트를 사용한다.
    <App /> // 4. App 컴포넌트에 render 함수에 포함 [ App 호출하는 방법 : 상단에 import ]
  </React.StrictMode>
);
*/
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
