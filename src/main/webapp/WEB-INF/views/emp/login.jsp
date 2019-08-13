<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style type="text/css">

*        { margin:0; padding: 0; }

body{
         font-family: "맑은 고딕";
         font-size: 0.75em;
         color: #333;
}

#login-form{
         width:200px;
         margin:100px auto;
         border: 1px solid gray;
         border-radius: 5px;
         padding: 15px;
}

/* inline이였던 요소들을 block으로 바꿈 */

#login-form input, #login-form label{
         display:block;
}


#login-form label{
         margin-top: 10px;
}
#login-form input{
         margin-top: 5px;
}
/* 애트리뷰트 선택자 */
#login-form input[type='image']{
         margin: 10px auto;
}
</style>
<body>
         <form id="login-form" method="post">
         	<label class="legend">이름</label>
            <input name="name" type="text">

            <label class="legend">회원번호</label> 
            <input name="number" type="text"> 
            <input type="submit" value="로그인">
         </form>
</body>